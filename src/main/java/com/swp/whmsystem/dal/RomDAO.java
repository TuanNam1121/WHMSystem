package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Rom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RomDAO {
    public List<Rom> getAllRom() {
        List<Rom> list = new ArrayList<>();

        String sql = "SELECT id, size, isactive FROM roms ORDER BY id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rom rom = new Rom();
                rom.setId(rs.getInt("id"));
                rom.setSize(rs.getString("size"));
                rom.setActive(rs.getBoolean("isactive"));
                list.add(rom);
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
                Rom rom = new Rom();
                rom.setId(rs.getInt("id"));
                rom.setSize(rs.getString("size"));
                rom.setActive(rs.getBoolean("isactive"));
                return rom;
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

    public List<Rom> getRomsByFilter(String status) {
        List<Rom> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT id, size, isactive FROM roms");
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
                Rom rom = new Rom();
                rom.setId(rs.getInt("id"));
                rom.setSize(rs.getString("size"));
                rom.setActive(rs.getBoolean("isactive"));
                list.add(rom);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
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

    public boolean insertRom(String size) {
        String sql = "Insert into roms (size, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, size);
            ps.setBoolean(2, true);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
    }
}