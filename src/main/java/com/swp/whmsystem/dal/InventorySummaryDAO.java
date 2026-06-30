package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.InventorySummary;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InventorySummaryDAO {

    private static final String INCREASED = "INCREASED";
    private static final String DECREASED = "DECREASED";

    private int getQtyByType(int productId, String type, LocalDate fromDate, LocalDate toDate) {
        StringBuilder sql = new StringBuilder(
                "SELECT COALESCE(SUM(CASE WHEN sm.type = ? THEN sm.quantity ELSE 0 END), 0) AS qty " +
                        "FROM stock_movement sm WHERE sm.productid = ?");
        if (fromDate != null) {
            sql.append(" AND sm.createdat >= ?");
        }
        sql.append(" AND sm.createdat <= ?");

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setString(idx++, type);
            ps.setInt(idx++, productId);
            if (fromDate != null) {
                ps.setTimestamp(idx++, Timestamp.valueOf(fromDate.atStartOfDay()));
            }
            ps.setTimestamp(idx, Timestamp.valueOf(toDate.atTime(LocalTime.MAX)));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("qty");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getStockAtDate(int productId, LocalDate date) {
        String sql = "SELECT COALESCE(" +
                "SUM(CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE 0 END) - " +
                "SUM(CASE WHEN sm.type = 'DECREASED' THEN sm.quantity ELSE 0 END), 0) AS stock_at_date " +
                "FROM stock_movement sm WHERE sm.productid = ? AND sm.createdat <= ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setTimestamp(2, Timestamp.valueOf(date.atTime(LocalTime.MAX)));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock_at_date");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getImportQtyInRange(int productId, LocalDate openingDate, LocalDate closingDate) {
        return getQtyByType(productId, INCREASED, openingDate, closingDate);
    }

    public int getExportQtyInRange(int productId, LocalDate openingDate, LocalDate closingDate) {
        return getQtyByType(productId, DECREASED, openingDate, closingDate);
    }

    public int getAllImportQty(int productId) {
        return getQtyByType(productId, INCREASED, null, LocalDate.now());
    }

    public int getAllExportQty(int productId) {
        return getQtyByType(productId, DECREASED, null, LocalDate.now());
    }

    public InventorySummary toInventorySummary(int productId, String openDate, String closeDate) {
        InventorySummary is = new InventorySummary();
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductFromId(productId);

        is.setProductId(productId);
        is.setSku(product.getSku());
        is.setProductName(product.getName());
        is.setCategory(product.getCategory().getName());
        is.setUnit(product.getUnit().getName());

        if (openDate != null && !openDate.isEmpty()
                && closeDate != null && !closeDate.isEmpty()) {
            LocalDate openingDate = DateUtils.parseDate(openDate);
            LocalDate closingDate = DateUtils.parseDate(closeDate);

            is.setOpeningStock(getStockAtDate(productId, openingDate));
            is.setImportStock(getImportQtyInRange(productId, openingDate, closingDate));
            is.setExportStock(getExportQtyInRange(productId, openingDate, closingDate));
            is.setClosingStock(getStockAtDate(productId, closingDate));

        } else {
            is.setOpeningStock(0);
            is.setImportStock(getAllImportQty(productId));
            is.setExportStock(getAllExportQty(productId));
            is.setClosingStock(getStockAtDate(productId, LocalDate.now()));
        }

        return is;
    }

    public List<InventorySummary> showAll(String openDate, String closeDate, String keyword, int page, int pageSize) {
        List<InventorySummary> list = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.searchProduct(keyword, -1, -1, -1, null, pageSize, page);

        for (Product product : productList) {
            list.add(toInventorySummary(product.getProductId(), openDate, closeDate));
        }
        return list;
    }

    public int countAll(String keyword) {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.countProducts(keyword, -1, -1, -1);
    }

    public int[] getGrandTotals(String openDate, String closeDate, String keyword) {
        int[] totals = new int[4];
        boolean hasDate = openDate != null && !openDate.trim().isEmpty() && closeDate != null && !closeDate.trim().isEmpty();
        
        Timestamp tOpenStart = null;
        Timestamp tOpenMax = null;
        Timestamp tCloseMax = null;
        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        if (hasDate) {
            LocalDate openingDate = DateUtils.parseDate(openDate);
            LocalDate closingDate = DateUtils.parseDate(closeDate);
            tOpenStart = Timestamp.valueOf(openingDate.atStartOfDay());
            tOpenMax = Timestamp.valueOf(openingDate.atTime(LocalTime.MAX));
            tCloseMax = Timestamp.valueOf(closingDate.atTime(LocalTime.MAX));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (hasDate) {
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS opening, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat >= ? AND sm.createdat <= ? AND sm.type='INCREASED' THEN sm.quantity ELSE 0 END), 0) AS import_qty, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat >= ? AND sm.createdat <= ? AND sm.type='DECREASED' THEN sm.quantity ELSE 0 END), 0) AS export_qty, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS closing ");
        } else {
            sql.append("0 AS opening, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? AND sm.type='INCREASED' THEN sm.quantity ELSE 0 END), 0) AS import_qty, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? AND sm.type='DECREASED' THEN sm.quantity ELSE 0 END), 0) AS export_qty, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS closing ");
        }
        
        sql.append("FROM products p ");
        sql.append("LEFT JOIN stock_movement sm ON p.productid = sm.productid ");
        sql.append("LEFT JOIN categories c ON p.categoryid = c.categoryid ");
        sql.append("LEFT JOIN brands b ON p.brandid = b.brandid ");
        sql.append("WHERE 1=1 ");

        String kw = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            kw = "%" + keyword.trim() + "%";
            sql.append("AND (p.name LIKE ? OR c.name LIKE ? OR b.name LIKE ? OR p.sku LIKE ?) ");
        }

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
             
            int idx = 1;
            if (hasDate) {
                ps.setTimestamp(idx++, tOpenMax);
                ps.setTimestamp(idx++, tOpenStart);
                ps.setTimestamp(idx++, tCloseMax);
                ps.setTimestamp(idx++, tOpenStart);
                ps.setTimestamp(idx++, tCloseMax);
                ps.setTimestamp(idx++, tCloseMax);
            } else {
                ps.setTimestamp(idx++, tNowMax);
                ps.setTimestamp(idx++, tNowMax);
                ps.setTimestamp(idx++, tNowMax);
            }
            
            if (kw != null) {
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totals[0] = rs.getInt("opening");
                    totals[1] = rs.getInt("import_qty");
                    totals[2] = rs.getInt("export_qty");
                    totals[3] = rs.getInt("closing");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totals;
    }
}
