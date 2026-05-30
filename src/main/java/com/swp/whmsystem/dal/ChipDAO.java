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

    public List<Chip> getChipsByFilter(String status) {
        List<Chip> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM chips");
        boolean hasWhere = false;

        if ("active".equalsIgnoreCase(status)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" isactive = 1");
            hasWhere = true;
        } else if ("inactive".equalsIgnoreCase(status)) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" isactive = 0");
            hasWhere = true;
        }

        sql.append(" ORDER BY id");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapChip(rs));
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

}
