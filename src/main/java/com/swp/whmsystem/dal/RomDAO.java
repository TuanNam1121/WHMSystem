package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Rom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RomDAO {

    private Rom mapRom(ResultSet rs) throws SQLException {
        Rom rom = new Rom();
        rom.setId(rs.getInt("id"));
        rom.setSize(rs.getString("size"));
        rom.setActive(rs.getBoolean("isactive"));
        return rom;
    }

    public List<Rom> getAllRom() {
        List<Rom> list = new ArrayList<>();

        String sql = "SELECT id, size, isactive FROM roms ORDER BY id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRom(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<Rom> getAllRomToAssign() {
        List<Rom> list = new ArrayList<>();

        String sql = "SELECT id, size, isactive FROM roms where isactive = 1 ORDER BY id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRom(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Rom getRomById(int id) {
        String sql = "SELECT id, size, isactive FROM roms WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRom(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean insertRom(String size, Boolean active) {
        String sql = "Insert into roms (size, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, size);
            ps.setBoolean(2, active);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Rom getRomBySize(String size) {
        String sql = "SELECT id, size, isactive FROM roms WHERE size = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, size);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRom(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Rom> getRomsByFilter(String status, String keyword, int offset, int limit) {
        List<Rom> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT id, size, isactive FROM roms WHERE 1=1");

        if ("active".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 1");
        } else if ("inactive".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 0");
        }
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND size LIKE ?");
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
                list.add(mapRom(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    
    public int countRomsByFilter(String status, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM roms WHERE 1=1");

        if ("active".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 1");
        } else if ("inactive".equalsIgnoreCase(status)) {
            sql.append(" AND isactive = 0");
        }
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND size LIKE ?");
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
        String sql = "SELECT COUNT(*) FROM roms";
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

    public boolean updateRomStatus(int id, boolean active) {
        String sql = "UPDATE roms SET isactive = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
    }
}