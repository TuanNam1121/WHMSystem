package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Chip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChipDAO {
    
    public List<Chip> getAllChip(){
        List<Chip> list = new ArrayList<>();

        String sql = "SELECT * FROM chips ORDER BY id ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chip chip = new Chip();
                chip.setId(rs.getInt("id"));
                chip.setName(rs.getString("name"));
                chip.setActive(rs.getBoolean("isactive"));
                list.add(chip);
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
                Chip chip = new Chip();
                chip.setId(rs.getInt("id"));
                chip.setName(rs.getString("name"));
                chip.setActive(rs.getBoolean("isactive"));
                return chip;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Chip> getChipsByFilter(String keyword, String status) {
        List<Chip> list = new ArrayList<>();

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

        StringBuilder sql = new StringBuilder("SELECT * FROM chips");
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
                Chip chip = new Chip();
                chip.setId(rs.getInt("id"));
                chip.setName(rs.getString("name"));
                chip.setActive(rs.getBoolean("isactive"));
                list.add(chip);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
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

    public boolean insertChip(String name)
    {
        String sql ="Insert into chips (name, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, name);
            ps.setBoolean(2, true);
            ResultSet rs = ps.executeQuery();
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
