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
        brand.setCreatedAt(rs.getTimestamp("createdat"));
        brand.setUpdatedAt(rs.getTimestamp("updatedat"));
        model.setBrand(brand);

        return model;
    }

    public List<Model> getModelByBrandId(int brandId) {
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

    public Model getModelById(int modelId) {

        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid where m.modelid = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, modelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapModel(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Model> getAll() {
        List<Model> list = new ArrayList<>();

        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid " +
                "ORDER BY m.modelid";

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

    public boolean insertModel(String name, int brandId, Boolean active) {
        String sql = "Insert into models (name, brandid, isactive) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, brandId);
            ps.setBoolean(3, active);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Model getModelByName(String name) {
        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid WHERE m.name = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapModel(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Model> getModelsByFilter(Integer brandId, String status) {
        List<Model> list = new ArrayList<>();


        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.modelid, m.name, m.isactive, ")
                .append("b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat ")
                .append("FROM models m JOIN brands b ON m.brandid = b.brandid");
        boolean hasWhere = false;

        if (brandId != null) {
            sql.append(" WHERE m.brandid = ?");
            hasWhere = true;
        }

        if ("active".equalsIgnoreCase(status)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" m.isactive = 1");
        } else if ("inactive".equalsIgnoreCase(status)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" m.isactive = 0");
        }

        sql.append(" ORDER BY m.modelid");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (brandId != null) {
                ps.setInt(1, brandId);
            }

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
        for(Model i : dao.getAll()){
            System.out.println(i.toString());
        }
    }
}
