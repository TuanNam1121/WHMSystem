package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.ImportHistoryDTO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import java.awt.BorderLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodReceiptDAO {

    public List<GoodReceipt> getAllGoodReceipt() {
        String sql = "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "ORDER BY gr.created_at DESC";
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
        String sql = "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "WHERE gr.processedby = ? ORDER BY gr.created_at DESC";
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

    public List<GoodReceipt> getNewAndImcompletedGoodReceipt() {
        String sql = "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "WHERE gr.status IN ('NEW', 'IMCOMPLETED') ORDER BY gr.created_at DESC";
        List<GoodReceipt> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

    public List<GoodReceipt> getGoodReceiptByPurchaseRequestId(int purchaseRequestId) {
        String sql = "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "WHERE gr.purchaserequestid = ?";
        List<GoodReceipt> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseRequestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToGoodReceipt(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public GoodReceipt getGoodReceiptByGoodReceipId(int goodReceiptId) {
        String sql = "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "WHERE gr.id = ? LIMIT 1";
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
        String sql = "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "WHERE gr.id = ?";
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
        c.setInvoiceNumber(rs.getString("invoice_number"));
        c.setNote(rs.getString("note"));
        return c;
    }

    // luôn trả về ID để insert good_receipts_items ngay sau đó
    public int insertGoodReceiptAndGetId(GoodReceipt receipt) {
        String sql = "insert into good_receipts (purchaserequestid, processedby, status, note, invoice_number) values (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, receipt.getPurchaseRequestId());
            preparedStatement.setInt(2, receipt.getProcessedBy());
            preparedStatement.setString(3, receipt.getStatus());
            preparedStatement.setString(4, receipt.getNote());
            preparedStatement.setString(5, receipt.getInvoiceNumber());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public boolean existInvoiceNumber(String invoiceNumber){
        String sql = "select * from good_receipts where invoice_number = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, invoiceNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<GoodReceipt> searchProduct(int receiptId, int purchaseid, int supplierId, int processedby, String sortBy) {
        List<GoodReceipt> goodReceipts = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT gr.* "
                + "FROM good_receipts gr "
                + "LEFT JOIN purchase_requests pr ON gr.purchaserequestid = pr.id "
                + "LEFT JOIN suppliers s ON pr.supplierid = s.supplierid "
                + "WHERE 1 = 1"
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

        if (supplierId != -1) {
            sql.append(" and (s.supplierid = ?)");
            parameter.add(String.valueOf(supplierId));
        }

        if (processedby != -1) {
            sql.append(" and processedby = ? ");
            parameter.add(String.valueOf(processedby));
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "date_latest" ->
                    sql.append(" order by created_at asc");
                case "date_earliest" ->
                    sql.append(" order by created_at desc");
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
        System.out.println(gr.existInvoiceNumber(""));
    }
}
