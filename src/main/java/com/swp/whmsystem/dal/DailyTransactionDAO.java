/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.DailyTransaction;
import com.swp.whmsystem.model.GoodReceipt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DailyTransactionDAO {

    public List<DailyTransaction> searchDailyTransaction(String date, String keyword, String sortBy, String sortDir, int page, int pageSize) {
        String sql = """
                     select p.productid, p.name, p.sku, u.name as unit, date(?) as date , sum(case when sm.type = 'INCREASED' then sm.quantity else 0 end) as 'total_import', sum(case when sm.type = 'DECREASED' then sm.quantity else 0 end) as 'total_export'
                     from products p
                     left join stock_movement sm on sm.productid = p.productid and DATE(sm.createdat) = ?
                     join units u on p.unitid = u.id where 1 = 1 """;

        if (keyword != null && !keyword.isEmpty()) {
            sql += " and (";
            sql += " p.name like '%" + keyword.trim() + "%'";
            sql += " or p.sku like '%" + keyword.trim() + "%'";
            sql += ")";
        }

        sql += " group by p.productid ";
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "sku":
                    sql += " order by p.sku ";
                    break;
                case "name":
                    sql += " order by p.name ";
                    break;
                case "importQuantity":
                    sql += " order by total_import ";
                    break;
                default:
                    sql += " order by total_export ";
                    break;
            }
        } else {
            sql += " order by total_import desc, total_export ";
        }

        if (sortDir != null && !sortDir.isEmpty()) {
            if (sortDir.equals("asc")) {
                sql += sortDir;
            } else if (sortDir.equals("desc")) {
                sql += sortDir;
            }
        }
        else{
            sql += "desc";
        }
        int offset = (page - 1) * pageSize;
        sql += " limit ? offset ?";
        System.out.println(sql);
        // group by p.productid  order by total_import  limit 10 offset 0;
        List<DailyTransaction> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, pageSize);
            preparedStatement.setInt(4, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DailyTransaction p = mapResultSetToDailyTransaction(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countDailyTransaction(String keyword) {
        ProductDAO dao = new ProductDAO();
        return dao.countProducts(keyword, -1, -1, -1);
    }

    private DailyTransaction mapResultSetToDailyTransaction(ResultSet rs) throws SQLException {
        DailyTransaction i = new DailyTransaction();
        i.setProductId(rs.getInt("productid"));
        i.setProductName(rs.getString("name"));
        i.setSku(rs.getString("sku"));
        i.setUnit(rs.getString("unit"));
        i.setTotalImport(rs.getLong("total_import"));
        i.setTotalExport(rs.getLong("total_export"));
        i.setDate(rs.getDate("date"));
        return i;
    }

    public Long getTotalImportQty(String date, String keyword) {
        return calculateTotalQuantity(date, keyword, "INCREASED", false);
    }

    public Long getTotalExportQty(String date, String keyword) {
        return calculateTotalQuantity(date, keyword, "DECREASED", false);
    }

    public Long getTotalAdjustQty(String date, String keyword) {
        return calculateTotalQuantity(date, keyword, null, true);
    }

    private Long calculateTotalQuantity(String date, String keyword, String type, boolean isAdjust) {
        String sql = "SELECT SUM(sm.quantity) FROM stock_movement sm JOIN products p ON sm.productid = p.productid WHERE DATE(sm.createdat) = ?";
        if (isAdjust) {
            sql += " AND sm.reference_type = 'INVENTORY_AUDIT'";
        } else {
            sql += " AND sm.type = ? AND (sm.reference_type IS NULL OR sm.reference_type != 'INVENTORY_AUDIT')";
        }
        if (keyword != null && !keyword.isBlank()) {
            sql += " AND (p.name LIKE '%" + keyword + "%' OR p.sku LIKE '%" + keyword + "%')";
        }
        try (Connection connection = DBContext.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, date);
            if (!isAdjust) {
                preparedStatement.setString(2, type);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public Long getTotalImportValue(String date, String keyword) {
        GoodReceiptDAO dao = new GoodReceiptDAO();
        return dao.getCompletedImportTotalPriceByDay(date, keyword);
    }

    public Long getTotalExportValue(String date, String keyword) {
        ExportReceiptDAO dao = new ExportReceiptDAO();
        return dao.getCompletedExportTotalPriceByDay(date, keyword);
    }

    public static void main(String[] args) {
        DailyTransactionDAO di = new DailyTransactionDAO();
        String date = LocalDate.now().toString();
        //date=2026-07-13&keyword=&sortBy=&sortDir=desc&pageSize=10
        for (DailyTransaction i : di.searchDailyTransaction("2026-07-13", null, null, null, 1, 10)) {
            System.out.println(i);
        }
        System.out.println(di.countDailyTransaction(null));
    }
}
