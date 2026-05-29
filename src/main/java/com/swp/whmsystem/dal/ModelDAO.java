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

    public Model getModelById(int id) {
        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid " +
                "WHERE m.modelid = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapModel(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Model> getModelsByFilter(String keyword, Integer brandId, String status) {
        List<Model> list = new ArrayList<>();

        String keywordTrimmed = keyword == null ? null : keyword.trim();
        if (keywordTrimmed != null && keywordTrimmed.isEmpty()) {
            keywordTrimmed = null;
        }

        Integer keywordId = null;
        if (keywordTrimmed != null) {
            try {
                keywordId = Integer.valueOf(keywordTrimmed);
            } catch (NumberFormatException ignored) {
            }
        }

        Integer normalizedBrandId = (brandId != null && brandId > 0) ? brandId : null;

        String statusTrimmed = status == null ? null : status.trim();
        if (statusTrimmed != null && statusTrimmed.isEmpty()) {
            statusTrimmed = null;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.modelid, m.name, m.isactive, ")
                .append("b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat ")
                .append("FROM models m JOIN brands b ON m.brandid = b.brandid");

        boolean hasWhere = false;
        if (keywordTrimmed != null) {
            if (keywordId != null) {
                sql.append(" WHERE (m.modelid = ? OR m.name LIKE ?)");
            } else {
                sql.append(" WHERE m.name LIKE ?");
            }
            hasWhere = true;
        }

        if (normalizedBrandId != null) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" m.brandid = ?");
            hasWhere = true;
        }

        if ("active".equalsIgnoreCase(statusTrimmed)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" m.isactive = 1");
            hasWhere = true;
        } else if ("inactive".equalsIgnoreCase(statusTrimmed)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" m.isactive = 0");
            hasWhere = true;
        }

        sql.append(" ORDER BY m.modelid");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (keywordTrimmed != null) {
                if (keywordId != null) {
                    ps.setInt(paramIndex++, keywordId);
                    ps.setString(paramIndex++, "%" + keywordTrimmed + "%");
                } else {
                    ps.setString(paramIndex++, "%" + keywordTrimmed + "%");
                }
            }

            if (normalizedBrandId != null) {
                ps.setInt(paramIndex++, normalizedBrandId);
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
}
