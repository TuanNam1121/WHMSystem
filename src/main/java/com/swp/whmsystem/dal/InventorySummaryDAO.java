package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.InventorySummary;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.utils.DateUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InventorySummaryDAO {

    public List<InventorySummary> forReport(String openDate, String closeDate, String keyword, int page, int pageSize, String sortColumn, String sortOrder) {
        List<InventorySummary> list = new ArrayList<>();
        boolean hasOpenDate = openDate != null && !openDate.trim().isEmpty();
        boolean hasCloseDate = closeDate != null && !closeDate.trim().isEmpty();
        boolean hasDate = hasOpenDate || hasCloseDate;

        LocalDate fromDate = hasDate ? (hasOpenDate ? DateUtils.parseDate(openDate) : LocalDate.of(2000, 1, 1)) : null;
        LocalDate toDate   = hasDate ? (hasCloseDate ? DateUtils.parseDate(closeDate) : LocalDate.now()) : null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.productid, p.sku, p.name AS product_name, c.name AS category_name, u.name AS unit_name, ");

        if (hasDate) {
            sql.append("COALESCE(dsb_a.closing_balance,    0)                                                          AS opening_stock, ");
            sql.append("COALESCE(dsb_b.cumulative_import  - COALESCE(dsb_a.cumulative_import, 0), 0)                   AS import_stock, ");
            sql.append("COALESCE(dsb_b.cumulative_export  - COALESCE(dsb_a.cumulative_export, 0), 0)                   AS export_stock, ");
            sql.append("COALESCE(dsb_b.closing_balance, COALESCE(dsb_a.closing_balance, 0))                            AS closing_stock ");
        } else {
            sql.append("0                                     AS opening_stock, ");
            sql.append("COALESCE(dsb.cumulative_import, 0)    AS import_stock, ");
            sql.append("COALESCE(dsb.cumulative_export, 0)    AS export_stock, ");
            sql.append("COALESCE(dsb.closing_balance,   0)    AS closing_stock ");
        }

        sql.append("FROM products p ");
        sql.append("LEFT JOIN categories c ON p.categoryid = c.categoryid ");
        sql.append("LEFT JOIN units u ON p.unitid = u.id ");

        if (hasDate) {
            sql.append("LEFT JOIN daily_stock_balance dsb_b ");
            sql.append("       ON dsb_b.product_id = p.productid AND dsb_b.date = ");
            sql.append("          (SELECT MAX(date) FROM daily_stock_balance ");
            sql.append("            WHERE product_id = p.productid AND date >= ? AND date <= ?) ");
            sql.append("LEFT JOIN daily_stock_balance dsb_a ");
            sql.append("       ON dsb_a.product_id = p.productid AND dsb_a.date = ");
            sql.append("          (SELECT MAX(date) FROM daily_stock_balance ");
            sql.append("            WHERE product_id = p.productid AND date < ?) ");
        } else {
            sql.append("LEFT JOIN daily_stock_balance dsb ");
            sql.append("       ON dsb.product_id = p.productid AND dsb.date = ");
            sql.append("          (SELECT MAX(date) FROM daily_stock_balance WHERE product_id = p.productid) ");
        }

        sql.append("WHERE 1=1 ");

        String kw = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            kw = "%" + keyword.trim() + "%";
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }

        if (sortColumn != null && !sortColumn.trim().isEmpty()) {
            String order = "desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";
            switch (sortColumn) {
                case "openingStock": sql.append("ORDER BY opening_stock ").append(order).append(", p.productid DESC "); break;
                case "importStock":  sql.append("ORDER BY import_stock ").append(order).append(", p.productid DESC "); break;
                case "exportStock":  sql.append("ORDER BY export_stock ").append(order).append(", p.productid DESC "); break;
                case "closingStock": sql.append("ORDER BY closing_stock ").append(order).append(", p.productid DESC "); break;
                default:             sql.append("ORDER BY p.productid DESC "); break;
            }
        } else {
            sql.append("ORDER BY p.productid DESC ");
        }

        sql.append("LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (hasDate) {
                ps.setDate(idx++, Date.valueOf(fromDate));
                ps.setDate(idx++, Date.valueOf(toDate));
                ps.setDate(idx++, Date.valueOf(fromDate));
            }
            if (kw != null) {
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
            }
            ps.setInt(idx++, pageSize);
            ps.setInt(idx++, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventorySummary is = new InventorySummary();
                    is.setProductId(rs.getInt("productid"));
                    is.setSku(rs.getString("sku"));
                    is.setProductName(rs.getString("product_name"));
                    is.setCategory(rs.getString("category_name"));
                    is.setUnit(rs.getString("unit_name"));
                    is.setOpeningStock(rs.getInt("opening_stock"));
                    is.setImportStock(rs.getInt("import_stock"));
                    is.setExportStock(rs.getInt("export_stock"));
                    is.setClosingStock(rs.getInt("closing_stock"));
                    list.add(is);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countAll(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products p WHERE 1=1 ");
        String kw = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            kw = "%" + keyword.trim() + "%";
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (kw != null) {
                ps.setString(1, kw);
                ps.setString(2, kw);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int[] getGrandTotals(String openDate, String closeDate, String keyword) {
        int[] totals = new int[4];
        boolean hasOpenDate = openDate != null && !openDate.trim().isEmpty();
        boolean hasCloseDate = closeDate != null && !closeDate.trim().isEmpty();
        boolean hasDate = hasOpenDate || hasCloseDate;

        LocalDate fromDate = hasDate ? (hasOpenDate ? DateUtils.parseDate(openDate) : LocalDate.of(2000, 1, 1)) : null;
        LocalDate toDate   = hasDate ? (hasCloseDate ? DateUtils.parseDate(closeDate) : LocalDate.now()) : null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (hasDate) {
            sql.append("COALESCE(SUM(COALESCE(dsb_a.closing_balance, 0)), 0) AS opening, ");
            sql.append("COALESCE(SUM(COALESCE(dsb_b.cumulative_import  - COALESCE(dsb_a.cumulative_import, 0), 0)), 0) AS import_qty, ");
            sql.append("COALESCE(SUM(COALESCE(dsb_b.cumulative_export  - COALESCE(dsb_a.cumulative_export, 0), 0)), 0) AS export_qty, ");
            sql.append("COALESCE(SUM(COALESCE(dsb_b.closing_balance, COALESCE(dsb_a.closing_balance, 0))), 0) AS closing ");
        } else {
            sql.append("0 AS opening, ");
            sql.append("COALESCE(SUM(COALESCE(dsb.cumulative_import, 0)), 0) AS import_qty, ");
            sql.append("COALESCE(SUM(COALESCE(dsb.cumulative_export, 0)), 0) AS export_qty, ");
            sql.append("COALESCE(SUM(COALESCE(dsb.closing_balance,   0)), 0) AS closing ");
        }

        sql.append("FROM products p ");

        if (hasDate) {
            sql.append("LEFT JOIN daily_stock_balance dsb_b ");
            sql.append("       ON dsb_b.product_id = p.productid AND dsb_b.date = ");
            sql.append("          (SELECT MAX(date) FROM daily_stock_balance ");
            sql.append("            WHERE product_id = p.productid AND date >= ? AND date <= ?) ");
            sql.append("LEFT JOIN daily_stock_balance dsb_a ");
            sql.append("       ON dsb_a.product_id = p.productid AND dsb_a.date = ");
            sql.append("          (SELECT MAX(date) FROM daily_stock_balance ");
            sql.append("            WHERE product_id = p.productid AND date < ?) ");
        } else {
            sql.append("LEFT JOIN daily_stock_balance dsb ");
            sql.append("       ON dsb.product_id = p.productid AND dsb.date = ");
            sql.append("          (SELECT MAX(date) FROM daily_stock_balance WHERE product_id = p.productid) ");
        }

        sql.append("WHERE 1=1 ");

        String kw = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            kw = "%" + keyword.trim() + "%";
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (hasDate) {
                ps.setDate(idx++, Date.valueOf(fromDate));
                ps.setDate(idx++, Date.valueOf(toDate));
                ps.setDate(idx++, Date.valueOf(fromDate));
            }
            if (kw != null) {
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
        boolean hasOpenDate = openDate != null && !openDate.trim().isEmpty();
        boolean hasCloseDate = closeDate != null && !closeDate.trim().isEmpty();
        boolean hasDate = hasOpenDate || hasCloseDate;

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
                LocalDate from = hasOpenDate ? DateUtils.parseDate(openDate) : LocalDate.of(2000, 1, 1);
                LocalDate to = hasCloseDate ? DateUtils.parseDate(closeDate) : LocalDate.now();
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
        return getTop5ByType(openDate, closeDate, "INCREASED");
    }

    public List<InventorySummary> getTop5Export(String openDate, String closeDate) {
        return getTop5ByType(openDate, closeDate, "DECREASED");
    }

    private List<InventorySummary> getTop5ByType(String openDate, String closeDate, String type) {
        List<InventorySummary> list = new ArrayList<>();
        boolean hasOpenDate = openDate != null && !openDate.trim().isEmpty();
        boolean hasCloseDate = closeDate != null && !closeDate.trim().isEmpty();
        boolean hasDate = hasOpenDate || hasCloseDate;

        Timestamp tStart = null;
        Timestamp tEnd = null;
        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        if (hasDate) {
            LocalDate from = hasOpenDate ? DateUtils.parseDate(openDate) : LocalDate.of(2000, 1, 1);
            LocalDate to = hasCloseDate ? DateUtils.parseDate(closeDate) : LocalDate.now();
            tStart = Timestamp.valueOf(from.atStartOfDay());
            tEnd = Timestamp.valueOf(to.atTime(LocalTime.MAX));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.productid, p.sku, p.name AS product_name, u.name AS unit_name, ");
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

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
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
                    if ("INCREASED".equals(type)) {
                        item.setImportStock(qty);
                    } else {
                        item.setExportStock(qty);
                    }
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
