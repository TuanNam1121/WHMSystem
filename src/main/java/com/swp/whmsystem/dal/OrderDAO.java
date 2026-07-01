/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.OrderItemDetailDTO;
import com.swp.whmsystem.model.Order;

import java.math.BigDecimal;
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

    public BigDecimal getCompletedSaleOrderTotalPrice() {
        String sql = "select coalesce(sum(total_price), 0) from orders where status = 'COMPLETED'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                BigDecimal totalPrice = rs.getBigDecimal(1);
                return totalPrice == null ? BigDecimal.ZERO : totalPrice;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getNewSaleOrderTotalPrice() {
        String sql = "select coalesce(sum(total_price), 0) from orders where status = 'NEW'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                BigDecimal totalPrice = rs.getBigDecimal(1);
                return totalPrice == null ? BigDecimal.ZERO : totalPrice;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return BigDecimal.ZERO;
    }

    public int countNewSaleOrders() {
        String sql = "select count(*) from orders where status = 'NEW'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
    }

    public List<BigDecimal> getMonthlySaleTotals(int year) {
        String sql = "select month(coalesce(completedat, orderdate, createdat)) as monthNumber, "
                + "coalesce(sum(total_price), 0) as totalPrice "
                + "from orders "
                + "where status = 'COMPLETED' "
                + "and year(coalesce(completedat, orderdate, createdat)) = ? "
                + "group by monthNumber";
        List<BigDecimal> monthlyTotals = createEmptyMonthlyTotals();

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("monthNumber");
                    if (month >= 1 && month <= 12) {
                        BigDecimal totalPrice = rs.getBigDecimal("totalPrice");
                        monthlyTotals.set(month - 1, totalPrice == null ? BigDecimal.ZERO : totalPrice);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return monthlyTotals;
    }

    private List<BigDecimal> createEmptyMonthlyTotals() {
        List<BigDecimal> monthlyTotals = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            monthlyTotals.add(BigDecimal.ZERO);
        }
        return monthlyTotals;
    }

    public List<Order> getAllOrder() {
        String sql = "select * from orders";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                Order o = mapResultSetToOrder(rs);
                UserDAO ud = new UserDAO();
                CustomerDAO cd = new CustomerDAO();
                OrderItemDAO oid = new OrderItemDAO();
                o.setCustomer(cd.getCustomerNameById(o.getCustomerId()));
                o.setCreater(ud.getUserNameById(o.getCreatedBy()));
                o.setProcessor(ud.getUserNameById(o.getProcessedBy()));
                o.setTotalQuantity(oid.totalQuantityByOrderId(o.getId()));
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Order> getOrderByCustomer(String name) {

        try (Connection conn = DBContext.getConnection()) {
            String sql = "select o.* from orders o join customers c on o.customer_id = c.id where c.name LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                Order o = mapResultSetToOrder(rs);
                UserDAO ud = new UserDAO();
                CustomerDAO cd = new CustomerDAO();
                o.setCustomer(cd.getCustomerNameById(o.getCustomerId()));
                o.setCreater(ud.getUserNameById(o.getCreatedBy()));
                o.setProcessor(ud.getUserNameById(o.getProcessedBy()));
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Order> getOrderByCustomerId(int id) {

        try (Connection conn = DBContext.getConnection()) {
            String sql = "select o.* from orders o join customers c on o.customer_id = c.id where c.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                Order o = mapResultSetToOrder(rs);
                UserDAO ud = new UserDAO();
                CustomerDAO cd = new CustomerDAO();
                o.setCustomer(cd.getCustomerNameById(o.getCustomerId()));
                o.setCreater(ud.getUserNameById(o.getCreatedBy()));
                o.setProcessor(ud.getUserNameById(o.getProcessedBy()));
                OrderItemDAO oid = new OrderItemDAO();
                o.setTotalQuantity(oid.totalQuantityByOrderId(o.getId()));
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Order> searchOrdersToExport(String keyword, String date, String status,
            String sortBy, int pageSize, int page) {
        List<Order> orderList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select o.id, coalesce(er.status, o.status) as status, o.total_price, o.note, "
                + "o.orderdate, o.createdat, o.updatedat, o.completedat, "
                + "o.createdby, o.processedby, o.customer_id "
                + "from orders o "
                + "join customers c on o.customer_id = c.id "
                + "left join export_receipts er on er.order_id = o.id and er.status = 'DRAFT' "
                + "where o.status = 'NEW'"
        );
        List<Object> parameter = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (c.name like ? or o.id like ?)");
            parameter.add("%" + keyword.trim() + "%");
            parameter.add("%" + keyword.trim() + "%");
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" and date_format(o.orderdate, '%d-%m-%Y') = ?");
            parameter.add(date.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and coalesce(er.status, o.status) = ?");
            parameter.add(status);
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "dateNewest":
                    sql.append(" order by o.orderdate desc");
                    break;
                case "dateOldest":
                    sql.append(" order by o.orderdate asc");
                    break;
                case "totalLow":
                    sql.append(" order by o.total_price asc");
                    break;
                case "totalHigh":
                    sql.append(" order by o.total_price desc");
                    break;
            }
        } else {
            sql.append(" order by o.id desc");
        }

        int offset = (page - 1) * pageSize;
        sql.append(" limit ? offset ?");
        parameter.add(pageSize);
        parameter.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    CustomerDAO customerDAO = new CustomerDAO();
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    order.setCustomer(customerDAO.getCustomerNameById(order.getCustomerId()));
                    order.setTotalQuantity(orderItemDAO.totalQuantityByOrderId(order.getId()));
                    orderList.add(order);
                }
            }
            return orderList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return orderList;
    }

    public int countOrdersToExport(String keyword, String date, String status) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from orders o "
                + "join customers c on o.customer_id = c.id "
                + "left join export_receipts er on er.order_id = o.id and er.status = 'DRAFT' "
                + "where o.status = 'NEW'"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchValue = "%" + keyword.trim() + "%";
            sql.append(" and (c.name like ? or o.id like ?)");
            parameters.add(searchValue);
            parameters.add(searchValue);
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" and date_format(o.orderdate, '%d-%m-%Y') = ?");
            parameters.add(date.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and coalesce(er.status, o.status) = ?");
            parameters.add(status);
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
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
                UserDAO ud = new UserDAO();
                CustomerDAO cd = new CustomerDAO();
                OrderItemDAO oid = new OrderItemDAO();
                o.setCustomer(cd.getCustomerNameById(o.getCustomerId()));
                o.setCreater(ud.getUserNameById(o.getCreatedBy()));
                o.setProcessor(ud.getUserNameById(o.getProcessedBy()));
                o.setTotalQuantity(oid.totalQuantityByOrderId(o.getId()));

                return o;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public List<Order> searchOrder(String customerName, String status, int pageSize, int page) {

        try (Connection conn = DBContext.getConnection()) {
            String sql = "select o.* from orders o join customers c on o.customer_id = c.id ";
            if (customerName != null && !customerName.isBlank()) {

                sql += "where c.name LIKE ? ";
                if (!status.equals("ALL")) {
                    sql += "AND ";
                    sql += "o.status = ?";
                }
            } else if (customerName == null || customerName.isBlank()) {
                if (!status.equals("ALL")) {
                    sql += "where o.status = ?";
                }
            }

            sql += " ORDER BY o.id ";
            sql += " limit " + pageSize;
            sql += " offset " + (pageSize * (page - 1)) + " ";

            PreparedStatement ps = conn.prepareStatement(sql);
            if (customerName != null && !customerName.isBlank()) {

                ps.setString(1, "%" + customerName + "%");
                if (!status.equals("ALL")) {
                    ps.setString(2, status);
                }
            } else if (customerName == null || customerName.isBlank()) {
                if (!status.equals("ALL")) {
                    ps.setString(1, status);
                }
            }

            ResultSet rs = ps.executeQuery();
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                Order o = mapResultSetToOrder(rs);
                UserDAO ud = new UserDAO();
                CustomerDAO cd = new CustomerDAO();
                o.setCustomer(cd.getCustomerNameById(o.getCustomerId()));
                o.setCreater(ud.getUserNameById(o.getCreatedBy()));
                o.setProcessor(ud.getUserNameById(o.getProcessedBy()));
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public int countSearchOrder(String customerName, String status) {

        try (Connection conn = DBContext.getConnection()) {
            String sql = "select COUNT(*) from orders o join customers c on o.customer_id = c.id ";
            if (customerName != null && !customerName.isBlank()) {

                sql += "where c.name LIKE ? ";
                if (!status.equals("ALL")) {
                    sql += "AND ";
                    sql += "o.status = ?";
                }
            } else if (customerName == null || customerName.isBlank()) {
                if (!status.equals("ALL")) {
                    sql += "where o.status = ?";
                }
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            if (customerName != null && !customerName.isBlank()) {

                ps.setString(1, "%" + customerName + "%");
                if (!status.equals("ALL")) {
                    ps.setString(2, status);
                }
            } else if (customerName == null || customerName.isBlank()) {
                if (!status.equals("ALL")) {
                    ps.setString(1, status);
                }
            }

            ResultSet rs = ps.executeQuery();
            int result = 0;
            if (rs.next()) {
                result = rs.getInt("COUNT(*)");
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
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

    public void updateOrderPrice(Order o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE orders SET total_price = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setDouble(1, o.getTotalPrice());
            st.setInt(2, o.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateOrderNote(Order o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE orders SET note = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, o.getNote());
            st.setInt(2, o.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateOrderStatus(Order o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE orders SET status = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, o.getStatus());
            st.setInt(2, o.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateOrder(Order o) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE orders SET status = ?, total_price = ?, note = ?, updatedat = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, o.getStatus());
            st.setDouble(2, o.getTotalPrice());
            st.setString(3, o.getNote());
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
        o.setProcessedBy(rs.getInt("processedby"));
        o.setCustomerId(rs.getInt("customer_id"));

        return o;
    }

    public List<OrderItemDetailDTO> getOrderItemsByOrderId(int orderId) {
        List<OrderItemDetailDTO> list = new ArrayList<>();

        String sql = "SELECT p.name, p.img_url, p.sku, oi.quantity, oi.price "
                + "FROM order_items oi "
                + "JOIN products p ON oi.productid = p.productid "
                + "WHERE oi.orderid = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String imgUrl = rs.getString("img_url");
                    String sku = rs.getString("sku");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");

                    list.add(new OrderItemDetailDTO(name, imgUrl, sku, quantity, price));
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm của Order ID: " + orderId);
            e.printStackTrace();
        }

        return list;
    }

    public List<Order> getExportHistory() {
        String sql = "select o.id, er.status, o.total_price, o.note, "
                + "coalesce(er.exported_at, er.created_at) as orderdate, er.created_at as createdat, "
                + "er.updated_at as updatedat, er.exported_at as completedat, "
                + "o.createdby, er.exported_by as processedby, o.customer_id "
                + "from export_receipts er "
                + "join orders o on er.order_id = o.id "
                + "order by er.id desc";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Order> result = new ArrayList<>();
            while (rs.next()) {
                Order o = mapResultSetToOrder(rs);
                UserDAO ud = new UserDAO();
                CustomerDAO cd = new CustomerDAO();
                OrderItemDAO oid = new OrderItemDAO();
                o.setCustomer(cd.getCustomerNameById(o.getCustomerId()));
                o.setCreater(ud.getUserNameById(o.getCreatedBy()));
                o.setProcessor(ud.getUserNameById(o.getProcessedBy()));
                o.setTotalQuantity(oid.totalQuantityByOrderId(o.getId()));
                result.add(o);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Order> searchExportHistory(String keyword, String date, String status,
            String sortBy, int pageSize, int page) {
        List<Order> orderList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select o.id, er.status, o.total_price, o.note, "
                + "coalesce(er.exported_at, er.created_at) as orderdate, er.created_at as createdat, "
                + "er.updated_at as updatedat, er.exported_at as completedat, "
                + "o.createdby, er.exported_by as processedby, o.customer_id "
                + "from export_receipts er "
                + "join orders o on er.order_id = o.id "
                + "join customers c on o.customer_id = c.id "
                + "where 1=1"
        );
        List<Object> parameter = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchValue = "%" + keyword.trim() + "%";
            sql.append(" and (c.name like ? or o.id like ? or exists ("
                    + "select 1 from export_receipt_details erd "
                    + "join export_receipt_serials ers on erd.id = ers.export_receipt_detail_id "
                    + "join product_items pi on ers.product_item_id = pi.id "
                    + "where erd.export_receipt_id = er.id and pi.serial like ?"
                    + "))");
            parameter.add(searchValue);
            parameter.add(searchValue);
            parameter.add(searchValue);
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" and date_format(coalesce(er.exported_at, er.created_at), '%d-%m-%Y') = ?");
            parameter.add(date.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and er.status = ?");
            parameter.add(status);
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "dateNewest":
                    sql.append(" order by coalesce(er.exported_at, er.created_at) desc");
                    break;
                case "dateOldest":
                    sql.append(" order by coalesce(er.exported_at, er.created_at) asc");
                    break;
                case "totalLow":
                    sql.append(" order by o.total_price asc");
                    break;
                case "totalHigh":
                    sql.append(" order by o.total_price desc");
                    break;
            }
        } else {
            sql.append(" order by o.id desc");
        }

        int offset = (page - 1) * pageSize;
        sql.append(" limit ? offset ?");
        parameter.add(pageSize);
        parameter.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    CustomerDAO customerDAO = new CustomerDAO();
                    UserDAO userDAO = new UserDAO();
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    order.setCustomer(customerDAO.getCustomerNameById(order.getCustomerId()));
                    order.setProcessor(userDAO.getUserNameById(order.getProcessedBy()));
                    order.setTotalQuantity(orderItemDAO.totalQuantityByOrderId(order.getId()));
                    orderList.add(order);
                }
            }
            return orderList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return orderList;
    }

    public int countExportHistory(String keyword, String date, String status) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from orders o "
                + "join export_receipts er on er.order_id = o.id "
                + "join customers c on o.customer_id = c.id "
                + "where 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchValue = "%" + keyword.trim() + "%";
            sql.append(" and (c.name like ? or o.id like ? or exists ("
                    + "select 1 from export_receipt_details erd "
                    + "join export_receipt_serials ers on erd.id = ers.export_receipt_detail_id "
                    + "join product_items pi on ers.product_item_id = pi.id "
                    + "where erd.export_receipt_id = er.id and pi.serial like ?"
                    + "))");
            parameters.add(searchValue);
            parameters.add(searchValue);
            parameters.add(searchValue);
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" and date_format(coalesce(er.exported_at, er.created_at), '%d-%m-%Y') = ?");
            parameters.add(date.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and er.status = ?");
            parameters.add(status);
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        List<OrderItemDetailDTO> orderList = orderDAO.getOrderItemsByOrderId(3);
        for (OrderItemDetailDTO o : orderList) {
            System.out.println(o);
        }
    }

}
