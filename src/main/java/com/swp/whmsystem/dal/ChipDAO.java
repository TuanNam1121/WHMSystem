package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Chip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChipDAO {

    public List<Chip> getAllChip() {
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

    public List<Chip> getChipsByPage(int pageNo, int pageSize) {

        List<Chip> list = new ArrayList<>();

        String sql = "SELECT * FROM chips ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (pageNo - 1) * pageSize;
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
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

    public Chip getChipById(int chipid) {
        String sql = "select * from chips where id = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, chipid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Chip chip = new Chip();
                    chip.setId(rs.getInt("id"));
                    chip.setName(rs.getString("name"));
                    chip.setActive(rs.getBoolean("isactive"));
                    return chip;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean insertChip(String name) {
        String sql = "Insert into chips (name, isactive) VALUES (?, ?)";
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
