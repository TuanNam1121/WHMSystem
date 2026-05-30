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
        String sql = "select * from brands";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Brand> result = new ArrayList<>();
            while (rs.next()) {
                Brand p = mapResultSetToBrand(rs);
                result.add(p);
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

                Brand p = mapResultSetToBrand(rs);

                return p;
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
                Brand p = mapResultSetToBrand(rs);

                return p;
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
            String sql = "insert into brands (name,description,createdat, updatedat) values (?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, b.getName());
            st.setString(2, b.getDescription());
            st.setDate(3, new Date(System.currentTimeMillis()));
            st.setDate(4, new Date(System.currentTimeMillis()));
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateBrand(Brand b) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE brands SET name = ?, description = ?, createdat = ?, updatedat = ? WHERE brandid = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, b.getName());
            st.setString(2, b.getDescription());
            st.setTimestamp(3, b.getCreatedAt());
            st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            st.setInt(5, b.getId());
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
        b.setDescription(rs.getString("description"));
        b.setCreatedAt(rs.getTimestamp("createdat"));
        b.setUpdatedAt(rs.getTimestamp("updatedat"));

        return b;
    }

    public static void main(String[] args) {

        BrandDAO dao = new BrandDAO();

        List<Brand> list = dao.getAllBrand();

        Brand b = new Brand();

        b.setId(1);
        b.setName("Dell");
        b.setDescription("Laptop brand");
        b.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        dao.updateBrand(b);

        for (Brand i : dao.getAllBrand()) {
            System.out.println(i.getId() + " " + i.getName() + " " + i.getDescription());
        }
    }
}
