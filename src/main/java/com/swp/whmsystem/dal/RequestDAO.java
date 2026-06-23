/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RequestDAO {

    public List<Request> getAllRequest() {
        String sql = "select * from password_resets order by createdat desc";


        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Request> result = new ArrayList<>();
            while (rs.next()) {
                Request i = mapResultSetToRequest(rs);
                result.add(i);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Request> getRequestsByPage(int offset, int limit) {
        String sql = "select * from password_resets order by createdat desc limit ? offset ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    Request i = mapResultSetToRequest(rs);
                    result.add(i);
                }
                return result;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return new ArrayList<>();
    }

    public int countTotalRequests() {
        String sql = "select count(*) from password_resets";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
    }

    public Request getLatestRequestByUserId(int userId) {
        String sql = "select * from password_resets where userid = ? order by createdat desc limit 1";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRequest(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public boolean updateRequestStatus(int requestId, String newStatus) {
    String sql = "update password_resets set status = ?, completedat = CURRENT_TIMESTAMP WHERE requestid = ?";

    try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newStatus);
        ps.setInt(2, requestId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; 
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return false;
}

    
    private Request mapResultSetToRequest(ResultSet rs) throws SQLException {
        Request i = new Request();
        i.setRequestId(rs.getInt("requestid"));
        i.setUserId(rs.getInt("userid"));
        i.setStatus(rs.getString("status"));
        Timestamp createdAt = rs.getTimestamp("createdat");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        i.setCreatedAt(createdAt.toLocalDateTime().format(format));
        return i;
    }

    public boolean addNewRequest(Request request) {
        String sql = "Insert into password_resets(userid, status)"
                + " values (?,?)";
        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, request.getUserId());
            ps.setString(2, request.getStatus());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        RequestDAO dao = new RequestDAO();
        List<Request> list = dao.getAllRequest();
        for (Request i : list) {
            System.out.println(i.toString());
        }
    }
}
