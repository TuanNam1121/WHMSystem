

package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class OrderItemDAO {

    PreparedStatement st;
    ResultSet rs;

    public OrderItemDAO() {
    }

    public List<OrderItem> getAllOrder() {
        String sql = "select * from order_items";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<OrderItem> result = new ArrayList<>();
            while (rs.next()) {
                OrderItem o = mapResultSetToOrder(rs);
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<OrderItem> getOrderItemByOrderId(int id) {
        try {
            List<OrderItem> result = new ArrayList<>();
            Connection conn = DBContext.getConnection();
            String sql = "select * from order_items where orderid=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery(); //only select
            while (rs.next()) {

                OrderItem o = mapResultSetToOrder(rs);

                result.add(o);
            }
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public OrderItem insertOrderItem(OrderItem o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "insert into orders"
                    + " (orderid,productid,quantity,price)"
                    + " values (?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, o.getOrderId());
            st.setInt(2, o.getProductId());
            st.setInt(3, o.getQuantity());
            st.setDouble(4, o.getPrice());
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

    public void updateOrderItem(OrderItem o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE orders SET quantity = ?, price = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, o.getQuantity());
            st.setDouble(2, o.getPrice());
            st.setInt(3, o.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private OrderItem mapResultSetToOrder(ResultSet rs) throws SQLException {
        OrderItem o = new OrderItem();

        o.setId(rs.getInt("id"));
        o.setOrderId(rs.getInt("orderid"));
        o.setProductId(rs.getInt("productid"));
        o.setQuantity(rs.getInt("quantity"));
        o.setPrice(rs.getDouble("price"));

        return o;
    }



}
