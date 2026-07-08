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

    /**
     * Get stock at the start of day (0:00) — all movements BEFORE the given date.
     * This is used for Opening Stock calculation.
     */
    public int getStockAtStartOfDay(int productId, LocalDate date) {
        String sql = "SELECT COALESCE(" +
                "SUM(CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE 0 END) - " +
                "SUM(CASE WHEN sm.type = 'DECREASED' THEN sm.quantity ELSE 0 END), 0) AS stock_at_date " +
                "FROM stock_movement sm WHERE sm.productid = ? AND sm.createdat < ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));
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

            is.setOpeningStock(getStockAtStartOfDay(productId, openingDate));
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
        Timestamp tCloseMax = null;
        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        if (hasDate) {
            LocalDate openingDate = DateUtils.parseDate(openDate);
            LocalDate closingDate = DateUtils.parseDate(closeDate);
            tOpenStart = Timestamp.valueOf(openingDate.atStartOfDay());
            tCloseMax = Timestamp.valueOf(closingDate.atTime(LocalTime.MAX));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (hasDate) {
            // Opening stock = all movements BEFORE the start of fromDate (0:00)
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat < ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS opening, ");
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
                ps.setTimestamp(idx++, tOpenStart);  // opening: < fromDate 0:00
                ps.setTimestamp(idx++, tOpenStart);  // import_qty: >= fromDate 0:00
                ps.setTimestamp(idx++, tCloseMax);   // import_qty: <= toDate 23:59
                ps.setTimestamp(idx++, tOpenStart);  // export_qty: >= fromDate 0:00
                ps.setTimestamp(idx++, tCloseMax);   // export_qty: <= toDate 23:59
                ps.setTimestamp(idx++, tCloseMax);   // closing: <= toDate 23:59
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

    public long getTotalAdjustQty(String openDate, String closeDate) {
        boolean hasDate = openDate != null && !openDate.trim().isEmpty()
                       && closeDate != null && !closeDate.trim().isEmpty();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COALESCE(SUM(sm.quantity), 0) AS adjust_qty ");
        sql.append("FROM stock_movement sm ");
        sql.append("WHERE sm.reference_type = 'INVENTORY_AUDIT' ");

        if (hasDate) {
            sql.append("AND sm.createdat >= ? AND sm.createdat <= ? ");
        } else {
            sql.append("AND sm.createdat <= ? ");
        }

        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (hasDate) {
                LocalDate from = DateUtils.parseDate(openDate);
                LocalDate to = DateUtils.parseDate(closeDate);
                ps.setTimestamp(idx++, Timestamp.valueOf(from.atStartOfDay()));
                ps.setTimestamp(idx++, Timestamp.valueOf(to.atTime(LocalTime.MAX)));
            } else {
                ps.setTimestamp(idx++, tNowMax);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("adjust_qty");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public List<InventorySummary> getTop5Import(String openDate, String closeDate) {
        return getTop5ByType(openDate, closeDate, INCREASED);
    }

    public List<InventorySummary> getTop5Export(String openDate, String closeDate) {
        return getTop5ByType(openDate, closeDate, DECREASED);
    }

    private List<InventorySummary> getTop5ByType(String openDate, String closeDate, String type) {
        List<InventorySummary> list = new ArrayList<>();
        boolean hasDate = openDate != null && !openDate.trim().isEmpty()
                       && closeDate != null && !closeDate.trim().isEmpty();

        Timestamp tStart = null;
        Timestamp tEnd = null;
        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        if (hasDate) {
            LocalDate from = DateUtils.parseDate(openDate);
            LocalDate to = DateUtils.parseDate(closeDate);
            tStart = Timestamp.valueOf(from.atStartOfDay());
            tEnd = Timestamp.valueOf(to.atTime(LocalTime.MAX));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.productid, p.sku, p.name AS product_name, ");
        sql.append("u.name AS unit_name, ");
        sql.append("COALESCE(SUM(sm.quantity), 0) AS total_qty ");
        sql.append("FROM stock_movement sm ");
        sql.append("JOIN products p ON sm.productid = p.productid ");
        sql.append("LEFT JOIN units u ON p.unitid = u.id ");
        sql.append("WHERE sm.type = ? ");

        if (hasDate) {
            sql.append("AND sm.createdat >= ? AND sm.createdat <= ? ");
        } else {
            sql.append("AND sm.createdat <= ? ");
        }

        sql.append("GROUP BY p.productid, p.sku, p.name, u.name ");
        sql.append("ORDER BY total_qty DESC ");
        sql.append("LIMIT 5 ");

        String finalSql = sql.toString();

        System.out.println("[DEBUG] getTop5ByType SQL: " + finalSql);
        System.out.println("[DEBUG] type=" + type + ", hasDate=" + hasDate);

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(finalSql)) {
            int idx = 1;
            ps.setString(idx++, type);
            if (hasDate) {
                ps.setTimestamp(idx++, tStart);
                ps.setTimestamp(idx++, tEnd);
            } else {
                ps.setTimestamp(idx++, tNowMax);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventorySummary item = new InventorySummary();
                    item.setProductId(rs.getInt("productid"));
                    item.setSku(rs.getString("sku"));
                    item.setProductName(rs.getString("product_name"));
                    item.setUnit(rs.getString("unit_name"));
                    int qty = rs.getInt("total_qty");
                    if (INCREASED.equals(type)) {
                        item.setImportStock(qty);
                    } else {
                        item.setExportStock(qty);
                    }
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("[DEBUG] getTop5ByType SQL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("[DEBUG] getTop5ByType result size: " + list.size());
        return list;
    }
}
