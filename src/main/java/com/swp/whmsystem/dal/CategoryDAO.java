package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public List<Category> getAllCategory() {
        String sql = "select * from categories";
        List<Category> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category c = mapResultSetToCategory(resultSet);
                list.add(c);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean deactiveCategory(int cateid){
        String sql = "update categories set isactive = 0 where categoryid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, cateid);
            return ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Category getCategoryById(int cateid) {
        String sql = "select * from categories where categoryid = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, cateid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Category getCategoryByName(String cate) {
        String sql = "select * from categories where name = ? limit 1";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, cate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category c = mapResultSetToCategory(rs);
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId(rs.getInt("categoryid"));
        c.setName(rs.getString("name"));
        c.setDescription(rs.getString("description"));
        c.setIsActive(rs.getBoolean("isactive"));
        return c;
    }

    public boolean addNewCategory(Category category) {
        String sql = "insert into Categories(name, description) values (?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1 ,category.getName());
            ps.setString(2 ,category.getDescription());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateCategory(Category category) {
        String sql = "update Categories set name = ?, description = ? where categoryid = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId()); // giả sử Category có trường categoryId
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> a = categoryDAO.getAllCategory();
        for(Category i : a){
            System.out.println(i.toString());
        }
    }
}
