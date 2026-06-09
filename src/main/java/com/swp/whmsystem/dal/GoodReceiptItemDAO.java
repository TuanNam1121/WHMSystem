package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.GoodReceiptItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodReceiptItemDAO {
    public List<GoodReceiptItem> getAllGoodReceiptItems() {
        String sql = "select * from good_receipts_items";
        List<GoodReceiptItem> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GoodReceiptItem p = mapResultSetToGoodReceiptItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GoodReceiptItem> getItemsByGoodReceiptId(int goodReceiptId) {
        String sql = "select * from good_receipts_items where goodreceiptid = ?";
        List<GoodReceiptItem> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, goodReceiptId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    GoodReceiptItem p = mapResultSetToGoodReceiptItem(resultSet);
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public GoodReceiptItem mapResultSetToGoodReceiptItem(ResultSet rs) throws SQLException {
        GoodReceiptItem item = new GoodReceiptItem();
        item.setId(rs.getInt("id"));
        item.setGoodReceiptId(rs.getInt("goodreceiptid"));
        item.setProductId(rs.getInt("product_id"));
        item.setActualQuantity(rs.getInt("actual_quantity"));
        return item;
    }

    public boolean insertGoodReceiptItem(GoodReceiptItem item) {
        String sql = "insert into good_receipts_items (goodreceiptid, product_id, actual_quantity) values (?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, item.getGoodReceiptId());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getActualQuantity());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateGoodReceiptItem(GoodReceiptItem item) {
        String sql = "update good_receipts_items set goodreceiptid = ?, product_id = ?, actual_quantity = ? where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, item.getGoodReceiptId());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getActualQuantity());
            preparedStatement.setInt(4, item.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteGoodReceiptItem(int id) {
        String sql = "delete from good_receipts_items where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        GoodReceiptItemDAO dao = new GoodReceiptItemDAO();
        List<GoodReceiptItem> a = dao.getAllGoodReceiptItems();
        for(GoodReceiptItem i : a){
            System.out.println(i);
        }
    }
}
