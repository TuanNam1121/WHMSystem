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

    public List<InventorySummary> forMovementReport(String movementType, String referenceType,
            String fromDate, String toDate, String keyword, int page, int pageSize,
            String sortOrder) {
        List<InventorySummary> list = new ArrayList<>();
        Timestamp from = toStartTimestamp(fromDate);
        Timestamp to = toEndTimestamp(toDate);
        String keywordPattern = toKeywordPattern(keyword);
        String order = "asc".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.productid, p.sku, p.name AS product_name, ")
                .append("c.name AS category_name, u.name AS unit_name, ")
                .append("COALESCE(SUM(sm.quantity), 0) AS report_quantity ")
                .append("FROM products p ")
                .append("LEFT JOIN categories c ON p.categoryid = c.categoryid ")
                .append("LEFT JOIN units u ON p.unitid = u.id ")
                .append("JOIN stock_movement sm ON p.productid = sm.productid ")
                .append("AND sm.type = ? AND sm.reference_type = ? ")
                .append("AND sm.createdat >= ? AND sm.createdat <= ? ")
                .append("WHERE 1=1 ");
        if (keywordPattern != null) {
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }
        sql.append("GROUP BY p.productid, p.sku, p.name, c.name, u.name ")
                .append("ORDER BY report_quantity ").append(order)
                .append(", p.productid DESC LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setString(index++, movementType);
            ps.setString(index++, referenceType);
            ps.setTimestamp(index++, from);
            ps.setTimestamp(index++, to);
            if (keywordPattern != null) {
                ps.setString(index++, keywordPattern);
                ps.setString(index++, keywordPattern);
            }
            ps.setInt(index++, pageSize);
            ps.setInt(index, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventorySummary item = mapSpecializedReportItem(rs);
                    int quantity = rs.getInt("report_quantity");
                    if ("INCREASED".equals(movementType)) {
                        item.setImportStock(quantity);
                    } else {
                        item.setExportStock(quantity);
                    }
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countMovementReport(String movementType, String referenceType,
            String fromDate, String toDate, String keyword) {
        Timestamp from = toStartTimestamp(fromDate);
        Timestamp to = toEndTimestamp(toDate);
        String keywordPattern = toKeywordPattern(keyword);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT p.productid) ")
                .append("FROM products p ")
                .append("JOIN stock_movement sm ON p.productid = sm.productid ")
                .append("AND sm.type = ? AND sm.reference_type = ? ")
                .append("AND sm.createdat >= ? AND sm.createdat <= ? ")
                .append("WHERE 1=1 ");
        if (keywordPattern != null) {
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }
        return executeSpecializedCount(sql.toString(), movementType, referenceType,
                from, to, keywordPattern);
    }

    public long getMovementReportTotal(String movementType, String referenceType,
            String fromDate, String toDate, String keyword) {
        Timestamp from = toStartTimestamp(fromDate);
        Timestamp to = toEndTimestamp(toDate);
        String keywordPattern = toKeywordPattern(keyword);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COALESCE(SUM(sm.quantity), 0) ")
                .append("FROM products p ")
                .append("JOIN stock_movement sm ON p.productid = sm.productid ")
                .append("AND sm.type = ? AND sm.reference_type = ? ")
                .append("AND sm.createdat >= ? AND sm.createdat <= ? ")
                .append("WHERE 1=1 ");
        if (keywordPattern != null) {
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }
        return executeSpecializedTotal(sql.toString(), movementType, referenceType,
                from, to, keywordPattern);
    }

    public List<InventorySummary> forStockReport(String fromDate, String toDate, String keyword,
            int page, int pageSize, String sortColumn, String sortOrder) {
        List<InventorySummary> list = new ArrayList<>();
        Timestamp from = toStartTimestamp(fromDate);
        Timestamp to = toEndTimestamp(toDate);
        String keywordPattern = toKeywordPattern(keyword);
        String order = "asc".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.productid, p.sku, p.name AS product_name, ")
                .append("c.name AS category_name, u.name AS unit_name, ")
                .append("COALESCE(SUM(CASE WHEN sm.createdat < ? ")
                .append("THEN CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE -sm.quantity END ")
                .append("ELSE 0 END), 0) AS opening_stock, ")
                .append("COALESCE(SUM(CASE WHEN sm.createdat <= ? ")
                .append("THEN CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE -sm.quantity END ")
                .append("ELSE 0 END), 0) AS closing_stock ")
                .append("FROM products p ")
                .append("LEFT JOIN categories c ON p.categoryid = c.categoryid ")
                .append("LEFT JOIN units u ON p.unitid = u.id ")
                .append("LEFT JOIN stock_movement sm ON p.productid = sm.productid ")
                .append("WHERE 1=1 ");
        if (keywordPattern != null) {
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }
        sql.append("GROUP BY p.productid, p.sku, p.name, c.name, u.name ");
        String sortExpression = "openingStock".equals(sortColumn) ? "opening_stock" : "closing_stock";
        sql.append("ORDER BY ").append(sortExpression).append(" ").append(order)
                .append(", p.productid DESC LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setTimestamp(index++, from);
            ps.setTimestamp(index++, to);
            if (keywordPattern != null) {
                ps.setString(index++, keywordPattern);
                ps.setString(index++, keywordPattern);
            }
            ps.setInt(index++, pageSize);
            ps.setInt(index, (page - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventorySummary item = mapSpecializedReportItem(rs);
                    item.setOpeningStock(rs.getInt("opening_stock"));
                    item.setClosingStock(rs.getInt("closing_stock"));
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countStockReport(String keyword) {
        String keywordPattern = toKeywordPattern(keyword);
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products p WHERE 1=1 ");
        if (keywordPattern != null) {
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (keywordPattern != null) {
                ps.setString(1, keywordPattern);
                ps.setString(2, keywordPattern);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long[] getStockReportTotals(String fromDate, String toDate, String keyword) {
        long[] totals = new long[2];
        Timestamp from = toStartTimestamp(fromDate);
        Timestamp to = toEndTimestamp(toDate);
        String keywordPattern = toKeywordPattern(keyword);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("COALESCE(SUM(CASE WHEN sm.createdat < ? ")
                .append("THEN CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE -sm.quantity END ")
                .append("ELSE 0 END), 0) AS total_opening, ")
                .append("COALESCE(SUM(CASE WHEN sm.createdat <= ? ")
                .append("THEN CASE WHEN sm.type = 'INCREASED' THEN sm.quantity ELSE -sm.quantity END ")
                .append("ELSE 0 END), 0) AS total_closing ")
                .append("FROM products p LEFT JOIN stock_movement sm ON p.productid = sm.productid ")
                .append("WHERE 1=1 ");
        if (keywordPattern != null) {
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setTimestamp(index++, from);
            ps.setTimestamp(index++, to);
            if (keywordPattern != null) {
                ps.setString(index++, keywordPattern);
                ps.setString(index, keywordPattern);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totals[0] = rs.getLong("total_opening");
                    totals[1] = rs.getLong("total_closing");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totals;
    }

    private int executeSpecializedCount(String sql, String movementType, String referenceType,
            Timestamp from, Timestamp to, String keywordPattern) {
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setMovementParameters(ps, movementType, referenceType, from, to, keywordPattern);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private long executeSpecializedTotal(String sql, String movementType, String referenceType,
            Timestamp from, Timestamp to, String keywordPattern) {
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setMovementParameters(ps, movementType, referenceType, from, to, keywordPattern);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private void setMovementParameters(PreparedStatement ps, String movementType,
            String referenceType, Timestamp from, Timestamp to, String keywordPattern)
            throws SQLException {
        int index = 1;
        ps.setString(index++, movementType);
        ps.setString(index++, referenceType);
        ps.setTimestamp(index++, from);
        ps.setTimestamp(index++, to);
        if (keywordPattern != null) {
            ps.setString(index++, keywordPattern);
            ps.setString(index, keywordPattern);
        }
    }

    private InventorySummary mapSpecializedReportItem(ResultSet rs) throws SQLException {
        InventorySummary item = new InventorySummary();
        item.setProductId(rs.getInt("productid"));
        item.setSku(rs.getString("sku"));
        item.setProductName(rs.getString("product_name"));
        item.setCategory(rs.getString("category_name"));
        item.setUnit(rs.getString("unit_name"));
        return item;
    }

    private Timestamp toStartTimestamp(String date) {
        LocalDate parsed = DateUtils.parseDate(date);
        LocalDate value = parsed == null ? LocalDate.of(2000, 1, 1) : parsed;
        return Timestamp.valueOf(value.atStartOfDay());
    }

    private Timestamp toEndTimestamp(String date) {
        LocalDate parsed = DateUtils.parseDate(date);
        LocalDate value = parsed == null ? LocalDate.now() : parsed;
        return Timestamp.valueOf(value.atTime(LocalTime.MAX));
    }

    private String toKeywordPattern(String keyword) {
        return keyword == null || keyword.trim().isEmpty() ? null : "%" + keyword.trim() + "%";
    }

    public List<InventorySummary> forReport(String openDate, String closeDate, String keyword, int page, int pageSize, String sortColumn, String sortOrder) {
        List<InventorySummary> list = new ArrayList<>();
        boolean hasOpenDate = openDate != null && !openDate.trim().isEmpty();
        boolean hasCloseDate = closeDate != null && !closeDate.trim().isEmpty();
        boolean hasDate = hasOpenDate || hasCloseDate;

        Timestamp tOpenStart = null;
        Timestamp tCloseMax = null;
        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        if (hasDate) {
            LocalDate openingDate = hasOpenDate ? DateUtils.parseDate(openDate) : LocalDate.of(2000, 1, 1);
            LocalDate closingDate = hasCloseDate ? DateUtils.parseDate(closeDate) : LocalDate.now();
            tOpenStart = Timestamp.valueOf(openingDate.atStartOfDay());
            tCloseMax = Timestamp.valueOf(closingDate.atTime(LocalTime.MAX));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.productid, p.sku, p.name AS product_name, c.name AS category_name, u.name AS unit_name, ");
        
        if (hasDate) {
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat < ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS opening_stock, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat >= ? AND sm.createdat <= ? AND sm.type='INCREASED' THEN sm.quantity ELSE 0 END), 0) AS import_stock, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat >= ? AND sm.createdat <= ? AND sm.type='DECREASED' THEN sm.quantity ELSE 0 END), 0) AS export_stock, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS closing_stock ");
        } else {
            sql.append("0 AS opening_stock, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? AND sm.type='INCREASED' THEN sm.quantity ELSE 0 END), 0) AS import_stock, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? AND sm.type='DECREASED' THEN sm.quantity ELSE 0 END), 0) AS export_stock, ");
            sql.append("COALESCE(SUM(CASE WHEN sm.createdat <= ? THEN (CASE WHEN sm.type='INCREASED' THEN sm.quantity ELSE -sm.quantity END) ELSE 0 END), 0) AS closing_stock ");
        }

        sql.append("FROM products p ");
        sql.append("LEFT JOIN categories c ON p.categoryid = c.categoryid ");
        sql.append("LEFT JOIN units u ON p.unitid = u.id ");
        sql.append("LEFT JOIN stock_movement sm ON p.productid = sm.productid ");
        sql.append("WHERE 1=1 ");

        String kw = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            kw = "%" + keyword.trim() + "%";
            sql.append("AND (p.name LIKE ? OR p.sku LIKE ?) ");
        }

        sql.append("GROUP BY p.productid, p.sku, p.name, c.name, u.name ");

        if (sortColumn != null && !sortColumn.trim().isEmpty()) {
            String order = "desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";
            switch (sortColumn) {
                case "openingStock": sql.append("ORDER BY opening_stock ").append(order).append(", p.productid DESC "); break;
                case "importStock": sql.append("ORDER BY import_stock ").append(order).append(", p.productid DESC "); break;
                case "exportStock": sql.append("ORDER BY export_stock ").append(order).append(", p.productid DESC "); break;
                case "closingStock": sql.append("ORDER BY closing_stock ").append(order).append(", p.productid DESC "); break;
                default: sql.append("ORDER BY p.productid DESC "); break;
            }
        } else {
            sql.append("ORDER BY p.productid DESC ");
        }

        sql.append("LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (hasDate) {
                ps.setTimestamp(idx++, tOpenStart);
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

        Timestamp tOpenStart = null;
        Timestamp tCloseMax = null;
        Timestamp tNowMax = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX));

        if (hasDate) {
            LocalDate openingDate = hasOpenDate ? DateUtils.parseDate(openDate) : LocalDate.of(2000, 1, 1);
            LocalDate closingDate = hasCloseDate ? DateUtils.parseDate(closeDate) : LocalDate.now();
            tOpenStart = Timestamp.valueOf(openingDate.atStartOfDay());
            tCloseMax = Timestamp.valueOf(closingDate.atTime(LocalTime.MAX));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (hasDate) {
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
                ps.setTimestamp(idx++, tOpenStart); // opening: < fromDate 0:00
                ps.setTimestamp(idx++, tOpenStart); // import_qty: >= fromDate 0:00
                ps.setTimestamp(idx++, tCloseMax);  // import_qty: <= toDate 23:59
                ps.setTimestamp(idx++, tOpenStart); // export_qty: >= fromDate 0:00
                ps.setTimestamp(idx++, tCloseMax);  // export_qty: <= toDate 23:59
                ps.setTimestamp(idx++, tCloseMax);  // closing: <= toDate 23:59
            } else {
                ps.setTimestamp(idx++, tNowMax);
                ps.setTimestamp(idx++, tNowMax);
                ps.setTimestamp(idx++, tNowMax);
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
                    if ("INCREASED".equals(type)) {
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
