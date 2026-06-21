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
    
    public List<Unit> getAllUnitToAssign() {
        List<Unit> list = new ArrayList<>();

        String sql = "SELECT * FROM units where isactive = 1 ORDER BY id";

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

    public List<Unit> getUnitsByFilter(String status, String keyword, int offset, int limit) {
        List<Unit> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM units WHERE 1=1");

        if ("active".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 1");
        } else if ("inactive".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 0");
        }
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }

        sql.append(" ORDER BY id LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }
            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUnit(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    
    public int countUnitsByFilter(String status, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM units WHERE 1=1");

        if ("active".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 1");
        } else if ("inactive".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 0");
        }
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
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

    public boolean insertUnit(String name, Boolean active) {
        String sql = "Insert into units (name, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBoolean(2, active);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Unit getUnitByName(String name) {
        String sql = "SELECT * FROM units WHERE name = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUnit(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean updateUnitStatus(int id, boolean active) {
        String sql = "UPDATE units SET isactive = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Unit mapUnit(ResultSet rs) throws SQLException {
        Unit unit = new Unit();
        unit.setId(rs.getInt("id"));
        unit.setName(rs.getString("name"));
        unit.setActive(rs.getBoolean("isactive"));
        return unit;
    }

    public boolean isUnitUsed(int unitId) {
        String sql = "SELECT COUNT(*) FROM products WHERE unitid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, unitId);
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
        UnitDAO dao = new UnitDAO();
        for(Unit i : dao.getAllUnitToAssign()){
            System.out.println(i.toString());
        }
    }
}