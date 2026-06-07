package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.GoodReceipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodReceiptDAO {
    public List<GoodReceipt> getAllGoodReceipt() {
        String sql = "select * from good_receipts order by created_at desc";
        List<GoodReceipt> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GoodReceipt p = mapResultSetToGoodReceipt(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GoodReceipt> getAllGoodReceiptForProcessor(int processorId) {
        String sql = "select * from good_receipts where processedby = ? order by created_at desc";
        List<GoodReceipt> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, processorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    GoodReceipt p = mapResultSetToGoodReceipt(resultSet);
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public GoodReceipt mapResultSetToGoodReceipt(ResultSet rs) throws SQLException {
        GoodReceipt c = new GoodReceipt();
        c.setId(rs.getInt("id"));
        c.setPurchaseRequestId(rs.getInt("purchaserequestid"));
        c.setProcessedBy(rs.getInt("processedby"));
        c.setStatus(rs.getString("status"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        return c;
    }

    public boolean insertGoodReceipt(GoodReceipt receipt) {
        String sql = "insert into good_receipts (purchaserequestid, processedby, status) values (?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receipt.getPurchaseRequestId());
            preparedStatement.setInt(2, receipt.getProcessedBy());
            preparedStatement.setString(3, receipt.getStatus() != null ? receipt.getStatus() : "DRAFT");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateGoodReceipt(GoodReceipt receipt) {
        String sql = "update good_receipts set purchaserequestid = ?, processedby = ?, status = ? where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receipt.getPurchaseRequestId());
            preparedStatement.setInt(2, receipt.getProcessedBy());
            preparedStatement.setString(3, receipt.getStatus() != null ? receipt.getStatus() : "DRAFT");
            preparedStatement.setInt(4, receipt.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteGoodReceipt(int id) {
        String sql = "delete from good_receipts where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
