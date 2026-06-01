package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public List<Category> getAllCategory() {
        String sql = "select * from categories order by isActive desc";
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

    public List<Category> getAllCategoryToAssign() {
        String sql = "select * from categories where isactive = 1 order by isActive desc";
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

//    public boolean deactiveCategory(int cateid){
//        String sql = "update products set isactive = 0 where categoryid = ?";
//        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
//            ps.setInt(1, cateid);
//            return ps.executeUpdate() >= 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean reactiveCategory(int cateid){
        String sql = "update products set isactive = 1 where categoryid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, cateid);
            return ps.executeUpdate() >= 1;
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
                    Category c = mapResultSetToCategory(rs);
                    return c;
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
        String sql = "update Categories set name = ?, description = ?, isActive = ? where categoryid = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isIsActive());
            ps.setInt(4, category.getCategoryId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteCategoryById(int categoryId) {
        String sql = "delete from Categories where categoryid = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Category> searchCategory(int categoryId, int active, String sortBy) {
        List<Category> categoryList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select * from categories c " +
                        "where 1=1"
        );
        List<String> parameter = new ArrayList<>();


        if (categoryId != -1) {
            sql.append(" and categoryid = ?");
            parameter.add(String.valueOf(categoryId));

        }

        if (active != -1) {
            sql.append(" and isactive = ?");
            parameter.add(String.valueOf(active));
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "nameAZ":
                    sql.append(" order by name asc");
                    break;
                case "nameZA":
                    sql.append(" order by name desc");
                    break;
                case "active":
                    sql.append(" order by isactive desc");
                    break;
                case "inactive":
                    sql.append(" order by isactive asc");

                    break;
            }
        }


        try (Connection conn = DBContext.getConnection()
        ) {
            System.out.println(sql.toString());
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(mapResultSetToCategory(rs));
            }
            return categoryList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {

        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = new Category();

        category = categoryDAO.getCategoryById(1);
        System.out.println(category);
    }
}
