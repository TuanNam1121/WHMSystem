package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.model.User;

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

    public Category getCategoryByName(String categoryName) {
        String sql = "select * from categories where name = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
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

    public boolean deactiveCategory(int categoryid) {
        String sql = "update Products set isactive = 0 where categoryid = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryid);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
//        CategoryDAO categoryDAO = new CategoryDAO();
//        List<Category> list = CategoryDAO.getAllCategory();
//        for (Category i : list) {
//            System.out.println(i.toString());
//        }

        // 2 Nguyen Thi Manager 5 Male 0900000002 manager@gmail.com
        Category a = new Category(1, "Laptop Gaming", "High graphic");
        System.out.println(a.getCategoryName());
        System.out.println(a.getDescription());
        System.out.println(a.isActive());
    }
}
