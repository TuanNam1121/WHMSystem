package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;

public class BrandDAO {

    PreparedStatement st;
    ResultSet rs;

    public BrandDAO() {
    }

    public List<Brand> getAllBrand() {
        String sql = "select * from brands order by name";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Brand> result = new ArrayList<>();
            while (rs.next()) {
                Brand b = mapResultSetToBrand(rs);
                result.add(b);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public Brand getBrandById(int brand_id) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "select * from brands where brandid=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setInt(1, brand_id);
            rs = st.executeQuery(); //only select
            if (rs.next()) {

                Brand b = mapResultSetToBrand(rs);

                return b;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Brand getBrandByName(String brand_name) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "select * from brands where name=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setString(1, brand_name);
            rs = st.executeQuery(); //only select
            if (rs.next()) {
                Brand b = mapResultSetToBrand(rs);

                return b;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void insertBrand(Brand b) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "insert into brands (name,img_url,description,createdat, updatedat) values (?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, b.getName());
            st.setString(2, b.getImg());
            st.setString(3, b.getDescription());
            st.setDate(4, new Date(System.currentTimeMillis()));
            st.setDate(5, new Date(System.currentTimeMillis()));
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateBrand(Brand b) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE brands SET name = ?, img_url = ?, description = ?, createdat = ?, updatedat = ? WHERE brandid = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, b.getName());
            st.setString(2, b.getImg());
            st.setString(3, b.getDescription());
            st.setTimestamp(4, b.getCreatedAt());
            st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            st.setInt(6, b.getId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteBrand(Brand b) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "DELETE FROM brands WHERE brandid = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, b.getId());
            st.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Brand mapResultSetToBrand(ResultSet rs) throws SQLException {
        Brand b = new Brand();

        b.setId(rs.getInt("brandid"));
        b.setName(rs.getString("name"));
        b.setImg(rs.getString("img_url"));
        b.setDescription(rs.getString("description"));
        b.setCreatedAt(rs.getTimestamp("createdat"));
        b.setUpdatedAt(rs.getTimestamp("updatedat"));

        return b;
    }

    public List<Brand> searchBrand(String keyword, String sortBy, int pageSize, int page) {
        List<Brand> brandList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select b.* from brands b "
                        + "where 1=1"
        );
        List<Object> parameter = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (b.name like ? or b.description like ?)");
            parameter.add("%" + keyword.trim() + "%");
            parameter.add("%" + keyword.trim() + "%");
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "nameAZ":
                    sql.append(" order by b.name asc");
                    break;
                case "nameZA":
                    sql.append(" order by b.name desc");
                    break;
                case "descriptionAZ":
                    sql.append(" order by b.description asc");
                    break;
                case "descriptionZA":
                    sql.append(" order by b.description desc");
                    break;
            }
        } else {
            sql.append(" order by b.brandid asc");
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
                brandList.add(mapResultSetToBrand(rs));
            }
            return brandList;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return brandList;
    }

    public int countBrands(String keyword) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from brands b "
                        + "where 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchValue = "%" + keyword.trim() + "%";
            sql.append(" and (b.name like ? or b.description like ?)");
            parameters.add(searchValue);
            parameters.add(searchValue);
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

    public static void main(String[] args) {

        BrandDAO dao = new BrandDAO();

        List<Brand> list = dao.getAllBrand();
        List<Brand> search = dao.searchBrand(null, null, 10, 1);
        Brand b = new Brand();

        b.setId(1);
        b.setName("Dell");
        b.setDescription("Laptop brand");
        b.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        dao.updateBrand(b);

        for (Brand i : dao.getAllBrand()) {
            System.out.println(i.getId() + " " + i.getName() + " " + i.getDescription());
        }
        System.out.println(dao.getBrandByName("Dell").toString());
        System.out.println("====================");
        for (Brand asd : search) {
            System.out.println(asd);
        }
    }
}
