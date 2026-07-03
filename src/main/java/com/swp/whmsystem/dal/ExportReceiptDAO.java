/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ExportReceiptDAO {
    public Long getCompletedExportTotalPriceByDay(String date, String keyword) {
        String sql = """
                     select sum(quantity * unit_price) 
                     from export_receipts er
                     join export_receipt_details erd on er.id = erd.export_receipt_id
                     join products p on p.productid = erd.product_id
                     where date(er.created_at) = ? """;
                
        if(keyword != null && !keyword.isEmpty()){
            sql += " and ( ";
            sql += " p.name like '%" + keyword + "%' ";
            sql += " or p.sku like '%" + keyword + "%' ";
            sql += " )";
        }
        try (Connection connection = DBContext.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long totalPrice = resultSet.getLong(1);
                return totalPrice;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }
}
