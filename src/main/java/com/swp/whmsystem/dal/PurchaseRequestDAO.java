package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PurchaseRequestDAO {
    public List<PurchaseRequest> getAllPurchaseRequest() {
        String sql = "SELECT pr.*, u.username AS createdByUsername, s.suppliername FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid " +
                "where pr.isDeleted = 0 ORDER BY pr.id DESC";
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
    
    public List<PurchaseRequest> getApprovedAndIncompletedPurchaseRequest() {
        String sql = "SELECT pr.*, u.username AS createdByUsername, s.suppliername FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid " +
                "where pr.isDeleted = 0 and pr.status in ('APPROVED','INCOMPLETED') ORDER BY pr.id DESC";
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
        String sql = "SELECT pr.*, u.username AS createdByUsername, s.suppliername FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid " +
                "WHERE pr.createdby = ? and pr.isDeleted = 0 ORDER BY pr.id DESC";
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
        String sql = "SELECT pr.*, u.username AS createdByUsername, s.suppliername FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid WHERE pr.id = ?";
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
        String sql = "SELECT pr.*, u.username AS createdByUsername, s.suppliername FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid WHERE pr.createdby = ? ORDER BY pr.createdat DESC LIMIT 1";
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
        c.setSupplierId(rs.getInt("supplierid"));
        c.setDeleted(rs.getBoolean("isDeleted"));
        Timestamp ts = rs.getTimestamp("createdat");
        c.setCreatedAt(ts);
        Timestamp updatedTs = rs.getTimestamp("updatedat");
        c.setUpdatedAt(updatedTs);
        try {
            c.setCreatedByUsername(rs.getString("createdByUsername"));
            c.setSupplierName(rs.getString("suppliername"));
        } catch (SQLException e) {
            // Column might not exist in some queries if not updated
        }
        return c;
    }

    public boolean insertPurchaseRequest(PurchaseRequest request) {
        String sql = "insert into purchase_requests (createdby, note, supplierid) values (?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, request.getCreatedBy());
            preparedStatement.setString(2, request.getNote());
            preparedStatement.setInt(3, request.getSupplierId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePurchaseRequest(PurchaseRequest request) {
        String sql = "UPDATE purchase_requests SET createdby = ?, approvedby = ?, status = ?, note = ?, supplierid = ? WHERE id = ?";
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
            preparedStatement.setInt(5, request.getSupplierId());
            preparedStatement.setInt(6, request.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean softDeletePurchaseRequest(int id) {
        String sql = "UPDATE purchase_requests SET isDeleted = ? WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id);
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

    public List<PurchaseRequest> searchPurchaseItem(int salemanId, int id, String status, String dateStr, String sort, int pageSize, int page) {
        StringBuilder sql = new StringBuilder("SELECT pr.*, u.username AS createdByUsername, s.suppliername FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid " +
                "where pr.isDeleted = 0") ;
        List<Object> parameter = new ArrayList<>();
        if (salemanId != 0) {
            sql.append(" and pr.createdBy = ?");
            parameter.add(salemanId);
        }
        if (id != 0) {
            sql.append(" and pr.id = ?");
            parameter.add(id);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" and pr.status = ?");
            parameter.add(status.toUpperCase());
        }
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                String format = "dd-MM-yyyy";
                java.util.Date parsedDate = new SimpleDateFormat(format).parse(dateStr);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                sql.append(" and DATE(pr.createdat) = ?");
                parameter.add(sqlDate);
            } catch (Exception e) {
                System.out.println("Date parsing error: " + e.getMessage());
            }
        }
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "id_asc": sql.append(" order by pr.id asc"); break;
                case "status_desc": sql.append(" order by pr.status desc"); break;
                case "status_asc": sql.append(" order by pr.status asc"); break;
                case "date_desc": sql.append(" order by pr.createdat desc"); break;
                case "date_asc": sql.append(" order by pr.createdat asc"); break;
                case "id_desc": sql.append(" order by pr.id desc"); break;
                default: sql.append(" order by pr.id desc");
            }
        } else {
            sql.append(" order by pr.id desc");
        }

        int offset = (page - 1) * pageSize;
        sql.append(" limit ? offset ?");
        parameter.add(pageSize);
        parameter.add(offset);

        List<PurchaseRequest> list = new ArrayList<>();

        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                preparedStatement.setObject(i + 1, parameter.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToPurchaseRequest(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countPurchaseItem(int salemanId, int id, String status, String dateStr) {
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM purchase_requests pr " +
                "LEFT JOIN users u ON pr.createdby = u.userid " +
                "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid " +
                "where pr.isDeleted = 0") ;
        List<Object> parameter = new ArrayList<>();
        if (salemanId != 0) {
            sql.append(" and pr.createdBy = ?");
            parameter.add(salemanId);
        }
        if (id != 0) {
            sql.append(" and pr.id = ?");
            parameter.add(id);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" and pr.status = ?");
            parameter.add(status.toUpperCase());
        }
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                String format = "dd-MM-yyyy";
                java.util.Date parsedDate = new SimpleDateFormat(format).parse(dateStr);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                sql.append(" and DATE(pr.createdat) = ?");
                parameter.add(sqlDate);
            } catch (Exception e) {
                System.out.println("Date parsing error: " + e.getMessage());
            }
        }

        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                preparedStatement.setObject(i + 1, parameter.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
