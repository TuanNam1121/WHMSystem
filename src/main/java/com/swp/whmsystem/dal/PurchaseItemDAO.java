package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.PurchaseItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseItemDAO {
    public List<PurchaseItem> getAllPurchaseItems() {
        String sql = "select * from purchase_request_items";
        List<PurchaseItem> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PurchaseItem p = mapResultSetToPurchaseItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PurchaseItem> getItemsByPurchaseRequestId(int purchaseRequestId) {
        String sql = "select * from purchase_request_items where purchaserequestid = ?";
        List<PurchaseItem> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseRequestId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PurchaseItem p = mapResultSetToPurchaseItem(resultSet);
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public PurchaseItem mapResultSetToPurchaseItem(ResultSet rs) throws SQLException {
        PurchaseItem item = new PurchaseItem();
        item.setId(rs.getInt("id"));
        item.setPurchaseRequestId(rs.getInt("purchaserequestid"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }

    public boolean insertPurchaseItem(PurchaseItem item) {
        String sql = "insert into purchase_request_items (purchaserequestid, product_id, quantity) values (?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, item.getPurchaseRequestId());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getQuantity());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePurchaseItem(PurchaseItem item) {
        String sql = "update purchase_request_items set purchaserequestid = ?, product_id = ?, quantity = ? where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, item.getPurchaseRequestId());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setInt(4, item.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletePurchaseItem(int id) {
        String sql = "delete from purchase_request_items where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
