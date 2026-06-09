/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO {

    PreparedStatement st;
    ResultSet rs;

    public OrderDAO() {
    }

    public List<Order> getAllOrder() {
        String sql = "select * from orders";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                Order o = mapResultSetToOrder(rs);
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public Order getOrderById(int id) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "select * from orders where id=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery(); //only select
            if (rs.next()) {

                Order o = mapResultSetToOrder(rs);

                return o;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Order insertOrder(Order o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "insert into orders"
                    + " (status,note,orderdate,createdat, updatedat,createdby,customer_id)"
                    + " values (?,?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, o.getStatus());
            st.setString(2, o.getNote());
            st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            st.setInt(6, o.getCreatedBy());
            st.setInt(7, o.getCustomerId());
            st.executeUpdate();
            
            sql = "select * from orders order by id desc limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order od = mapResultSetToOrder(rs);
                return od;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void updateOrder(Order o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE orders SET status = ?, total_price = ?, note = ?, updatedat = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, o.getStatus());
            st.setDouble(2, o.getTotalPrice());
            st.setString(3,o.getNote());
            st.setTimestamp(4, o.getUpdatedAt());
            st.setInt(5, o.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order o = new Order();

        o.setId(rs.getInt("id"));
        o.setStatus(rs.getString("status"));
        o.setTotalPrice(rs.getDouble("total_price"));
        o.setNote(rs.getString("note"));
        o.setOrderDate(rs.getTimestamp("orderdate"));
        o.setCreatedAt(rs.getTimestamp("createdat"));
        o.setUpdatedAt(rs.getTimestamp("updatedat"));
        o.setCompletedAt(rs.getTimestamp("completedat"));
        o.setCreatedBy(rs.getInt("createdby"));
        o.setProcessdBy(rs.getInt("processedby"));
        o.setCustomerId(rs.getInt("customer_id"));

        return o;
    }


//    public static void main(String[] args) {
//
//        BrandDAO dao = new BrandDAO();
//
//        List<Brand> list = dao.getAllBrand();
//        List<Brand> search = dao.searchBrand(null, null, null);
//        Brand b = new Brand();
//
//        b.setId(1);
//        b.setName("Dell");
//        b.setDescription("Laptop brand");
//        b.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//
//        dao.updateBrand(b);
//
//        for (Brand i : dao.getAllBrand()) {
//            System.out.println(i.getId() + " " + i.getName() + " " + i.getDescription());
//        }
//        System.out.println(dao.getBrandByName("Dell").toString());
//        System.out.println("====================");
//        for (Brand asd : search) {
//            System.out.println(asd);
//        }
//    }


}
