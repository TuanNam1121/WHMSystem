package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.InventorySummary;
import com.swp.whmsystem.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class InventorySummaryDAO {

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

    public int getImportQtyInRange(int productId, LocalDate fromDate, LocalDate toDate) {
        String sql = "SELECT COALESCE(" +
                "SUM(CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE 0 END), 0) AS import_qty " +
                "FROM stock_movement sm WHERE sm.productid = ? AND sm.createdat >= ? AND sm.createdat <= ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setTimestamp(2, Timestamp.valueOf(fromDate.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(toDate.atTime(LocalTime.MAX)));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("import_qty");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getExportQtyInRange(int productId, LocalDate fromDate, LocalDate toDate) {
        String sql = "SELECT COALESCE(" +
                "SUM(CASE WHEN sm.type = 'DECREASED' THEN sm.quantity ELSE 0 END), 0) AS export_qty " +
                "FROM stock_movement sm WHERE sm.productid = ? AND sm.createdat >= ? AND sm.createdat <= ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setTimestamp(2, Timestamp.valueOf(fromDate.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(toDate.atTime(LocalTime.MAX)));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("export_qty");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public InventorySummary toInventorySummary(int productId, int openingStock, int importStock, int exportStock,
            int closingStock, LocalDate openingDate, LocalDate closingDate) {
        InventorySummary is = new InventorySummary();

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductFromId(productId);

        is.setSku(product.getSku());
        is.setProductName(product.getName());
        is.setUnit(product.getUnit().getName());

        is.setOpeningStock(getStockAtDate(productId, openingDate));
        is.setImportStock(getImportQtyInRange(productId, openingDate, closingDate));
        is.setExportStock(getExportQtyInRange(productId, openingDate, closingDate));
        is.setClosingStock(getStockAtDate(productId, closingDate));

        return is;
    }
}
