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

    public List<Rom> getRomsByPage(int pageNo, int pageSize) {

        List<Rom> list = new ArrayList<>();

        String sql = "SELECT id, size, isactive FROM roms ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (pageNo - 1) * pageSize;
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
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

    public Rom getRomById(int romid) {
        String sql = "select * from roms where id = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, romid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rom rom = new Rom();
                    rom.setId(rs.getInt("id"));
                    rom.setSize(rs.getString("size"));
                    rom.setActive(rs.getBoolean("isactive"));
                    return rom;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}