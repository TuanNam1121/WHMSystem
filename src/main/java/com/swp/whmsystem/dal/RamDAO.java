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

    public Ram getRamById(int id) {
        String sql = "SELECT id, size, isactive FROM rams WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ram ram = new Ram();
                ram.setId(rs.getInt("id"));
                ram.setSize(rs.getString("size"));
                ram.setActive(rs.getBoolean("isactive"));
                return ram;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean insertRam(String size, Boolean active) {
        String sql = "Insert into rams (size, isactive) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, size);
            ps.setBoolean(2, active);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Ram> getRamsByFilter(String status) {
        List<Ram> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT id, size, isactive FROM rams");
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