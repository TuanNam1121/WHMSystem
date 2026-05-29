package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitDAO {

    public List<Unit> getAllUnit() {
        List<Unit> list = new ArrayList<>();

        String sql = "SELECT * FROM units ORDER BY id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUnit(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Unit getUnitById(int id) {
        String sql = "SELECT * FROM units WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUnit(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Unit> getUnitsByFilter(String keyword, String status) {
        List<Unit> list = new ArrayList<>();

        String keywordTrimmed = keyword == null ? null : keyword.trim();
        if (keywordTrimmed != null && keywordTrimmed.isEmpty()) {
            keywordTrimmed = null;
        }

        String statusTrimmed = status == null ? null : status.trim();
        if (statusTrimmed != null && statusTrimmed.isEmpty()) {
            statusTrimmed = null;
        }

        Integer keywordId = null;
        if (keywordTrimmed != null) {
            try {
                keywordId = Integer.valueOf(keywordTrimmed);
            } catch (NumberFormatException ignored) {
            }
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM units");
        boolean hasWhere = false;

        if (keywordTrimmed != null) {
            if (keywordId != null) {
                sql.append(" WHERE (id = ? OR name LIKE ?)");
            } else {
                sql.append(" WHERE name LIKE ?");
            }
            hasWhere = true;
        }

        if ("active".equalsIgnoreCase(statusTrimmed)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" isactive = 1");
            hasWhere = true;
        } else if ("inactive".equalsIgnoreCase(statusTrimmed)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" isactive = 0");
            hasWhere = true;
        }

        sql.append(" ORDER BY id");

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

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUnit(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM units";
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

    public boolean insertUnit(String name) {
        String sql = "Insert into units (name, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBoolean(2, true);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Unit mapUnit(ResultSet rs) throws SQLException {
        Unit unit = new Unit();
        unit.setId(rs.getInt("id"));
        unit.setName(rs.getString("name"));
        unit.setActive(rs.getBoolean("isactive"));
        return unit;
    }
}