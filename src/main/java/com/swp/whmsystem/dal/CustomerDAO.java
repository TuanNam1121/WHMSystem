/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.swp.whmsystem.dal;


import com.swp.whmsystem.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {
    PreparedStatement st;
    ResultSet rs;

    public CustomerDAO() {
    }

    public List<Customer> getAllCustomer() {
        String sql = "select * from customers";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Customer> result = new ArrayList<>();
            while (rs.next()) {
                Customer c = mapResultSetToCustomer(rs);
                result.add(c);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public String getCustomerNameById(int id) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "select name from customers where id=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery(); //only select
            if (rs.next()) {
                String name = rs.getString("name");

                return name;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public Customer getCustomerById(int id) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "select * from customers where id = ?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery(); //only select
            if (rs.next()) {

                return mapResultSetToCustomer(rs);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
            return null;
    }

    public Customer getCustomerByPhone(String phone) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "select * from customers where phone = ?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setString(1, phone);
            rs = st.executeQuery(); //only select
            if (rs.next()) {
                Customer c = mapResultSetToCustomer(rs);

                return c;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void insertCustomer(Customer c) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "insert into customers (name,phone) values (?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setString(2, c.getPhone());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void updateCustomer(Customer c) {
        try (Connection conn = DBContext.getConnection()){
            
            String sql = "UPDATE customers SET name = ?, phone = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setString(2, c.getPhone());
            st.setInt(3, c.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public List<Customer> SearchCustomer(String search) {

        try (Connection conn = DBContext.getConnection()) {
        String sql = "select * from customers where name like ? or phone like ?";
             PreparedStatement ps = conn.prepareStatement(sql);
             ps.setString(1, "%" + search + "%");
             ps.setString(2, "%" + search + "%");
             ResultSet rs = ps.executeQuery();
            List<Customer> result = new ArrayList<>();
            while (rs.next()) {
                Customer c = mapResultSetToCustomer(rs);
                result.add(c);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer c = new Customer();

        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setPhone(rs.getString("phone"));

        return c;
    }


    public static void main(String[] args) {

        CustomerDAO dao = new CustomerDAO();

        List<Customer> list = dao.getAllCustomer();
        if(list != null && !list.isEmpty()){
            for(Customer c:list){
                System.out.println(c.toString());
            }
        }else{
            System.out.println("null or empty");
        }

    }
}
