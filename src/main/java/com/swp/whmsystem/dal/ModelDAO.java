package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDAO {
    private Model mapModel(ResultSet rs) throws SQLException {
        Model model = new Model();
        model.setId(rs.getInt("modelid"));
        model.setName(rs.getString("name"));
        model.setActive(rs.getBoolean("isactive"));

        Brand brand = new Brand();
        brand.setId(rs.getInt("brandid"));
        brand.setName(rs.getString("brand_name"));
        brand.setDescription(rs.getString("description"));
        brand.setCreatedAt(rs.getDate("createdat"));
        brand.setUpdatedAt(rs.getDate("updatedat"));
        model.setBrand(brand);

        return model;
    }
    
    public List<Model> getModelById(int brandId){
        List<Model> list = new ArrayList<>();

        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid where b.brandid = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, brandId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapModel(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Model> getAll() {
        List<Model> list = new ArrayList<>();

        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapModel(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public List<Model> getModelsByPage(int pageNo, int pageSize) {

        List<Model> list = new ArrayList<>();

        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid " +
                "ORDER BY m.modelid LIMIT ? OFFSET ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (pageNo - 1) * pageSize;
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapModel(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM models";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        ModelDAO dao = new ModelDAO();
        System.out.println(dao.getModelsByPage(1,10));
    }
}
