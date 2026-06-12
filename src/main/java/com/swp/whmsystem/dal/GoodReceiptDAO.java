package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.ImportHistoryDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;

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

    public GoodReceipt getGoodReceiptByPurchaseRequestId(int purchaseRequestId) {
        String sql = "SELECT * FROM good_receipts WHERE purchaserequestid = ? LIMIT 1";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseRequestId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToGoodReceipt(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public GoodReceipt getGoodReceiptByGoodReceipId(int goodReceiptId) {
        String sql = "SELECT * FROM good_receipts WHERE id = ? LIMIT 1";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, goodReceiptId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToGoodReceipt(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public GoodReceipt getGoodReceiptById(int id) {
        String sql = "SELECT * FROM good_receipts WHERE id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToGoodReceipt(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public GoodReceipt mapResultSetToGoodReceipt(ResultSet rs) throws SQLException {
        GoodReceipt c = new GoodReceipt();
        c.setId(rs.getInt("id"));
        c.setPurchaseRequestId(rs.getInt("purchaserequestid"));
        c.setProcessedBy(rs.getInt("processedby"));
        c.setStatus(rs.getString("status"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        c.setNote(rs.getString("note"));
        c.setSupplierName(rs.getString("supplier_name"));
        return c;
    }

    public boolean insertGoodReceipt(GoodReceipt receipt) {
        String sql = "insert into good_receipts (purchaserequestid, processedby, status, note) values (?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receipt.getPurchaseRequestId());
            preparedStatement.setInt(2, receipt.getProcessedBy());
            preparedStatement.setString(3, receipt.getStatus());
            preparedStatement.setString(4, receipt.getNote());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateGoodReceipt(GoodReceipt receipt) {
        String sql = "update good_receipts set purchaserequestid = ?, processedby = ?, status = ?, note = ? where id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receipt.getPurchaseRequestId());
            preparedStatement.setInt(2, receipt.getProcessedBy());
            preparedStatement.setString(3, receipt.getStatus() != null ? receipt.getStatus() : "DRAFT");
            preparedStatement.setString(4, receipt.getNote());
            preparedStatement.setInt(5, receipt.getId());
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
    
    public List<GoodReceipt> searchProduct(int receiptId,int purchaseid, String supplier, int processedby, String sortBy) {
        List<GoodReceipt> goodReceipts = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select * from good_receipts where 1 = 1"
        );
        List<String> parameter = new ArrayList<>();
                
        if (receiptId != -1) {
            sql.append(" and id = ? ");
            parameter.add(String.valueOf(receiptId));
        }
        
        if (purchaseid != -1) {
            sql.append(" and purchaserequestid = ? ");
            parameter.add(String.valueOf(purchaseid));
        }

        if (supplier != null && !supplier.trim().isEmpty()) {
            sql.append(" and supplier_name like ?");
            parameter.add("%" + supplier + "%");
        }

        if (processedby != -1) {
            sql.append(" and processedby = ? ");
            parameter.add(String.valueOf(processedby));
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "date_latest" -> sql.append(" order by created_at asc");
                case "date_earliest" -> sql.append(" order by created_at desc");
            }
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString());) {
            System.out.println(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                goodReceipts.add(mapResultSetToGoodReceipt(rs));
            }
            return goodReceipts;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return goodReceipts;
    }

    public static void main(String[] args) {
        GoodReceiptDAO gr = new GoodReceiptDAO();
        GoodReceiptItemDAO gri = new GoodReceiptItemDAO();
        ProductItemDAO pi = new ProductItemDAO();
    }
}
