package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestDAO {
    public List<PurchaseRequest> getAllPurchaseRequest() {
        String sql = "SELECT pr.*, u.username AS createdByUsername FROM purchase_requests pr LEFT JOIN users u ON pr.createdby = u.userid ORDER BY pr.createdat DESC";
        List<PurchaseRequest> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PurchaseRequest p = mapResultSetToPurchaseRequest(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PurchaseRequest> getAllPurchaseRequestForSaleman(int salemanId) {
        String sql = "SELECT pr.*, u.username AS createdByUsername FROM purchase_requests pr LEFT JOIN users u ON pr.createdby = u.userid WHERE pr.createdby = ? ORDER BY pr.createdat DESC";
        List<PurchaseRequest> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salemanId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    PurchaseRequest p = mapResultSetToPurchaseRequest(resultSet);
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public PurchaseRequest getPurchaseRequestById(int id) {
        String sql = "SELECT pr.*, u.username AS createdByUsername FROM purchase_requests pr LEFT JOIN users u ON pr.createdby = u.userid WHERE pr.id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPurchaseRequest(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public PurchaseRequest getLatestPurchaseRequestBySalemanId(int salemanId) {
        String sql = "SELECT pr.*, u.username AS createdByUsername FROM purchase_requests pr LEFT JOIN users u ON pr.createdby = u.userid WHERE pr.createdby = ? ORDER BY pr.createdat DESC LIMIT 1";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salemanId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPurchaseRequest(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public PurchaseRequest mapResultSetToPurchaseRequest(ResultSet rs) throws SQLException {
        PurchaseRequest c = new PurchaseRequest();
        c.setId(rs.getInt("id"));
        c.setCreatedBy(rs.getInt("createdby"));
        c.setApprovedBy(rs.getInt("approvedby"));
        c.setStatus(rs.getString("status"));
        c.setNote(rs.getString("note"));
        Timestamp ts = rs.getTimestamp("createdat");
        c.setCreatedAt(ts);
        try {
            c.setCreatedByUsername(rs.getString("createdByUsername"));
        } catch (SQLException e) {
            // Column might not exist in some queries if not updated
        }
        return c;
    }

    public boolean insertPurchaseRequest(PurchaseRequest request) {
        String sql = "insert into purchase_requests (createdby, note) values (?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, request.getCreatedBy());
            preparedStatement.setString(2, request.getNote());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePurchaseRequest(PurchaseRequest request) {
        String sql = "UPDATE purchase_requests SET createdby = ?, approvedby = ?, status = ?, note = ? WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, request.getCreatedBy());
            if (request.getApprovedBy() != 0) {
                preparedStatement.setInt(2, request.getApprovedBy());
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(3, request.getStatus() != null ? request.getStatus() : "NEW");
            preparedStatement.setString(4, request.getNote());
            preparedStatement.setInt(5, request.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletePurchaseRequest(int id) {
        String sql = "DELETE FROM purchase_requests WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
