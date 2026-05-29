package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Ram;
import com.swp.whmsystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RamDAO {

    public List<Ram> getAllRam() {
        List<Ram> list = new ArrayList<>();

        String sql = "SELECT id, size, isactive FROM rams ORDER BY id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ram ram = new Ram();
                ram.setId(rs.getInt("id"));
                ram.setSize(rs.getString("size"));
                ram.setActive(rs.getBoolean("isactive"));
                list.add(ram);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Ram> getRamsByPage(int pageNo, int pageSize) {

        List<Ram> list = new ArrayList<>();

        String sql = "SELECT id, size, isactive FROM rams ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (pageNo - 1) * pageSize;
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ram ram = new Ram();
                ram.setId(rs.getInt("id"));
                ram.setSize(rs.getString("size"));
                ram.setActive(rs.getBoolean("isactive"));
                list.add(ram);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Ram getRamById(int ramid) {
        String sql = "select * from rams where id = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, ramid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ram ram = new Ram();
                    ram.setId(rs.getInt("id"));
                    ram.setSize(rs.getString("size"));
                    ram.setActive(rs.getBoolean("isactive"));
                    return ram;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM rams";
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

    public boolean insertRam(String size) {
        String sql = "Insert into rams (size, isactive) VALUES (?, ?)";
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