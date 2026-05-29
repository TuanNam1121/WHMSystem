package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Category getCategoryById(int cateid) {
        String sql = "select * from categories where id = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, cateid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("id"));
                    category.setCategoryName(rs.getString("name"));
                    category.setDescription(rs.getString("description"));
                    category.setIsactive(rs.getBoolean("isactive"));
                    return category;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category getCategoryById(int categoryId) {
        String sql = "select * from categories where categoryid = ? limit 1";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if(resultSet.next()) {
                    Category c = mapResultSetToCategory(resultSet);
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId(rs.getInt("categoryid"));
        c.setCategoryName(rs.getString("name"));
        c.setDescription(rs.getString("description"));
        return c;
    }

    public boolean addNewCategory(Category category) {
        String sql = "insert into Categories(name, description) values (?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1 ,category.getCategoryName());
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
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId()); // giả sử Category có trường categoryId
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
