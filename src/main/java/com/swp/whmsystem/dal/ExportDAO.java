package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportDAO {

    public List<Order> getOrderList() {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from orders";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return orderList;
    }

}
