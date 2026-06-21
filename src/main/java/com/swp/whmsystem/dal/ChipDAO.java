package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Chip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChipDAO {

    private Chip mapChip(ResultSet rs) throws SQLException {
        Chip chip = new Chip();
        chip.setId(rs.getInt("id"));
        chip.setName(rs.getString("name"));
        chip.setActive(rs.getBoolean("isactive"));
        return chip;
    }

    public List<Chip> getAllChip() {
        List<Chip> list = new ArrayList<>();

        String sql = "SELECT * FROM chips ORDER BY id ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapChip(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<Chip> getAllChipToAssign() {
        List<Chip> list = new ArrayList<>();

        String sql = "SELECT * FROM chips where isactive = 1 ORDER BY id ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapChip(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Chip getChipById(int id) {
        String sql = "SELECT * FROM chips WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapChip(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Chip> getChipsByFilter(String status, String keyword, int offset, int limit) {
        List<Chip> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM chips WHERE 1=1");

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
                list.add(mapChip(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    
    public int countChipsByFilter(String status, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM chips WHERE 1=1");

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
        String sql = "SELECT COUNT(*) FROM chips";
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

    public boolean insertChip(String name,Boolean active)
    {
        String sql ="Insert into chips (name, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, name);
            ps.setBoolean(2, active);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateChipStatus(int id, boolean active) {
        String sql = "UPDATE chips SET isactive = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Chip getChipByName(String name) {
        String sql = "SELECT * FROM chips WHERE name = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapChip(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean isChipUsed(int chipId) {
        String sql = "SELECT COUNT(*) FROM products WHERE chipid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chipId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
