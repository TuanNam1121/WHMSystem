package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.StockMovement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO {
    public List<StockMovement> getAllStockMovement() {
        String sql = "SELECT * FROM stock_movement ORDER BY id DESC";
        List<StockMovement> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToStockMovement(resultSet));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StockMovement getStockMovementById(int id) {
        String sql = "SELECT * FROM stock_movement WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToStockMovement(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<StockMovement> getStockMovementByProductId(int productId) {
        String sql = "SELECT * FROM stock_movement WHERE productid = ? ORDER BY id DESC";
        List<StockMovement> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToStockMovement(resultSet));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StockMovement> getStockMovementByProductIdAndDateRange(int productId, String fromDateStr, String toDateStr, String typeFilter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM stock_movement WHERE productid = ?");
        java.time.LocalDate fromDate = com.swp.whmsystem.utils.DateUtils.parseDate(fromDateStr);
        java.time.LocalDate toDate = com.swp.whmsystem.utils.DateUtils.parseDate(toDateStr);

        if (fromDate != null) {
            sql.append(" AND createdat >= ?");
        }
        if (toDate != null) {
            sql.append(" AND createdat <= ?");
        }
        if (typeFilter != null && !typeFilter.trim().isEmpty() && !typeFilter.equalsIgnoreCase("ALL")) {
            sql.append(" AND type = ?");
        }
        sql.append(" ORDER BY id DESC");

        List<StockMovement> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            int idx = 1;
            preparedStatement.setInt(idx++, productId);
            if (fromDate != null) {
                preparedStatement.setTimestamp(idx++, java.sql.Timestamp.valueOf(fromDate.atStartOfDay()));
            }
            if (toDate != null) {
                preparedStatement.setTimestamp(idx++, java.sql.Timestamp.valueOf(toDate.atTime(java.time.LocalTime.MAX)));
            }
            if (typeFilter != null && !typeFilter.trim().isEmpty() && !typeFilter.equalsIgnoreCase("ALL")) {
                preparedStatement.setString(idx++, typeFilter.toUpperCase());
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToStockMovement(resultSet));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertStockMovement(StockMovement stockMovement) {
        String sql = "INSERT INTO stock_movement (productid, quantity, type, reference_type, reference_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stockMovement.getProductId());
            preparedStatement.setInt(2, stockMovement.getQuantity());
            preparedStatement.setString(3, stockMovement.getType());
            preparedStatement.setString(4, stockMovement.getReference_type());
            preparedStatement.setInt(5, stockMovement.getReference_id());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStockMovement(StockMovement stockMovement) {
        String sql = "UPDATE stock_movement SET productid = ?, quantity = ?, type = ?, reference_type = ?, reference_id = ? WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stockMovement.getProductId());
            preparedStatement.setInt(2, stockMovement.getQuantity());
            preparedStatement.setString(3, stockMovement.getType());
            preparedStatement.setString(4, stockMovement.getReference_type());
            preparedStatement.setInt(5, stockMovement.getReference_id());
            preparedStatement.setInt(6, stockMovement.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteStockMovement(int id) {
        String sql = "DELETE FROM stock_movement WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private StockMovement mapResultSetToStockMovement(ResultSet rs) throws SQLException {
        StockMovement s = new StockMovement();
        s.setId(rs.getInt("id"));
        s.setProductId(rs.getInt("productid"));
        s.setQuantity(rs.getInt("quantity"));
        s.setType(rs.getString("type"));
        s.setReference_type(rs.getString("reference_type"));
        s.setCreatedAt(rs.getTimestamp("createdat"));
        try {
            s.setReference_id(rs.getInt("reference_id"));
        } catch (Exception e) {
        }
        return s;
    }

    public boolean goodReceiptExists(int id) {
        String sql = "SELECT id FROM good_receipts WHERE id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAssociatedGoodReceiptId(int productId, java.sql.Timestamp createdAt) {
        String sql = "SELECT gr.id FROM good_receipts gr " +
                     "JOIN good_receipts_items gri ON gr.id = gri.goodreceiptid " +
                     "WHERE gri.product_id = ? " +
                     "ORDER BY ABS(TIMESTAMPDIFF(SECOND, gr.created_at, ?)) ASC LIMIT 1";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setTimestamp(2, createdAt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlAnyGr = "SELECT id FROM good_receipts WHERE status = 'COMPLETED' ORDER BY id DESC LIMIT 1";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlAnyGr)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public int getAssociatedOrderIdForExport(int referenceId, int productId, java.sql.Timestamp createdAt) {
        if (referenceId > 0) {
            String sql1 = "SELECT order_id FROM export_receipts WHERE id = ?";
            try (Connection connection = DBContext.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql1)) {
                ps.setInt(1, referenceId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql2 = "SELECT id FROM orders WHERE id = ?";
            try (Connection connection = DBContext.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql2)) {
                ps.setInt(1, referenceId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String sqlFallback = "SELECT er.order_id FROM export_receipts er " +
                             "JOIN export_receipt_details erd ON er.id = erd.export_receipt_id " +
                             "WHERE erd.product_id = ? " +
                             "ORDER BY ABS(TIMESTAMPDIFF(SECOND, er.exported_at, ?)) ASC LIMIT 1";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlFallback)) {
            ps.setInt(1, productId);
            ps.setTimestamp(2, createdAt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlOrderFallback = "SELECT oi.orderid FROM order_items oi WHERE oi.productid = ? LIMIT 1";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlOrderFallback)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlAnyOrder = "SELECT id FROM orders WHERE status = 'COMPLETED' ORDER BY id DESC LIMIT 1";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlAnyOrder)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return referenceId > 0 ? referenceId : 1;
    }
}
