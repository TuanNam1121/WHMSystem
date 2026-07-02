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
        String sql = "select * from categories where isactive = 1";
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
        String sql = "update products set isactive = 0 where categoryid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, cateid);
            return ps.executeUpdate() >= 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

//    public boolean deleteCategoryById(int categoryId) {
//        String sql = "delete from Categories where categoryid = ?";
//        try (Connection connection = DBContext.getConnection()) {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setInt(1, categoryId);
//            return ps.executeUpdate() == 1;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }

    public List<Category> searchCategory(String keyword, int isActive, String sortBy,
                                         int pageSize, int page) {
        List<Category> categoryList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select c.* from categories c "
                        + "where 1=1"
        );
        List<Object> parameter = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (c.name like ? or c.description like ?)");
            parameter.add("%" + keyword.trim() + "%");
            parameter.add("%" + keyword.trim() + "%");
        }

        if (isActive != -1) {
            sql.append(" and c.isactive = ?");
            parameter.add(isActive);
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "nameAZ":
                    sql.append(" order by c.name asc");
                    break;
                case "nameZA":
                    sql.append(" order by c.name desc");
                    break;
                case "active":
                    sql.append(" order by c.isactive desc");
                    break;
                case "inactive":
                    sql.append(" order by c.isactive asc");
                    break;
            }
        } else {
            sql.append(" order by c.categoryid asc");
        }

        int offset = (page - 1) * pageSize;
        sql.append(" limit ? offset ?");
        parameter.add(pageSize);
        parameter.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString());) {
            System.out.println(sql.toString());
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
        return categoryList;
    }

    public int countCategories(String keyword, int isActive) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from categories c "
                        + "where 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchValue = "%" + keyword.trim() + "%";
            sql.append(" and (c.name like ? or c.description like ?)");
            parameters.add(searchValue);
            parameters.add(searchValue);
        }
        if (isActive != -1) {
            sql.append(" and c.isactive = ?");
            parameters.add(isActive);
        }

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
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

    public boolean isCategoryUsed(int categoryId) {
        String sql = "SELECT COUNT(*) FROM products WHERE categoryid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void main(String[] args) {

        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = new Category();

        category = categoryDAO.getCategoryById(1);
        System.out.println(category);
    }
}
