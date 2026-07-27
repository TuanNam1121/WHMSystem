package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.ExportDetailItemDTO;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.dto.ExportReceiptInfoDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExportItemDAO {
    public int countCompletedExportReceipts() {
        String sql = "SELECT COUNT(*) FROM export_receipts WHERE status = 'COMPLETED'";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public ExportItemDTO getItemBySerial(String serial, int orderId) {
        ExportItemDTO dto = null;

        String sql = "SELECT p.sku, p.name, p.img_url, oi.price, pi.serial, "
                + "(SELECT COUNT(*) FROM product_items pi2 "
                + "WHERE pi2.product_id = p.productid AND pi2.status = 'AVAILABLE') AS available_quantity "
                + "FROM product_items pi "
                + "JOIN products p ON pi.product_id = p.productid "
                + "JOIN order_items oi ON p.productid = oi.productid "
                + "WHERE pi.serial = ? AND oi.orderid = ? "
                + "AND pi.status = 'AVAILABLE' AND p.isactive = 1";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, serial);
            ps.setInt(2, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = mapResultSetToExportItemDTO(rs);
                    dto.setSerial(rs.getString("serial"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    private ExportItemDTO mapResultSetToExportItemDTO(ResultSet rs) throws SQLException {
        ExportItemDTO dto = new ExportItemDTO();
        dto.setSku(rs.getString("sku"));
        dto.setName(rs.getString("name"));
        dto.setImgUrl(rs.getString("img_url"));
        dto.setStock(rs.getInt("available_quantity"));
        dto.setPrice(rs.getDouble("price"));
        dto.setSerial("");

        return dto;
    }

    public String processExportTransaction(int orderId, int userId, List<ExportItemDTO> exportList) {
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            try {
                String updateOrderSql =
                        "UPDATE orders SET status = 'COMPLETED', processedby = ?, "
                        + "updatedat = CURRENT_TIMESTAMP, completedat = CURRENT_TIMESTAMP "
                        + "WHERE id = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateOrderSql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, orderId);
                    ps.executeUpdate();
                }

                String createReceiptSql =
                        "INSERT INTO export_receipts"
                        + "(order_id, status, created_by, exported_by, exported_at) "
                        + "VALUES (?, 'COMPLETED', ?, ?, CURRENT_TIMESTAMP)";
                int exportReceiptId;
                try (PreparedStatement ps = conn.prepareStatement(
                        createReceiptSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, userId);
                    ps.setInt(3, userId);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Cannot create export receipt.");
                        }
                        exportReceiptId = rs.getInt(1);
                    }
                }

                String updateReceiptCodeSql =
                        "UPDATE export_receipts SET code = ? WHERE id = ?";
                try (PreparedStatement ps =
                        conn.prepareStatement(updateReceiptCodeSql)) {
                    ps.setString(1, "ER-" + exportReceiptId);
                    ps.setInt(2, exportReceiptId);
                    ps.executeUpdate();
                }

                List<Integer> productItemIds = new ArrayList<>();
                List<Integer> itemOrderItemIds = new ArrayList<>();
                List<Integer> detailOrderItemIds = new ArrayList<>();
                List<Integer> detailProductIds = new ArrayList<>();
                List<Integer> detailQuantities = new ArrayList<>();
                List<Double> detailPrices = new ArrayList<>();
                List<Integer> productIds = new ArrayList<>();
                List<Integer> productQuantities = new ArrayList<>();

                String findItemSql =
                        "SELECT pi.id, pi.product_id, pi.status, "
                        + "oi.id AS order_item_id, oi.price "
                        + "FROM product_items pi "
                        + "JOIN products p ON pi.product_id = p.productid "
                        + "JOIN order_items oi ON pi.product_id = oi.productid "
                        + "WHERE pi.serial = ? AND p.sku = ? AND oi.orderid = ?";
                String markItemSoldSql =
                        "UPDATE product_items SET status = 'SOLD', export_price = ? "
                        + "WHERE id = ?";

                try (PreparedStatement findItem =
                             conn.prepareStatement(findItemSql);
                     PreparedStatement markItemSold =
                             conn.prepareStatement(markItemSoldSql)) {
                    for (ExportItemDTO item : exportList) {
                        findItem.setString(1, item.getSerial());
                        findItem.setString(2, item.getSku());
                        findItem.setInt(3, orderId);

                        int productItemId;
                        int orderItemId;
                        int productId;
                        double price;
                        try (ResultSet rs = findItem.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException("Serial number "
                                        + item.getSerial()
                                        + " does not exist or does not belong "
                                        + "to the selected product.");
                            }

                            String status = rs.getString("status");
                            if (!"AVAILABLE".equalsIgnoreCase(status)) {
                                throw new SQLException("Serial number "
                                        + item.getSerial() + " is currently "
                                        + status + " and cannot be exported.");
                            }

                            productItemId = rs.getInt("id");
                            orderItemId = rs.getInt("order_item_id");
                            productId = rs.getInt("product_id");
                            price = rs.getDouble("price");
                        }

                        markItemSold.setDouble(1, price);
                        markItemSold.setInt(2, productItemId);
                        markItemSold.executeUpdate();

                        productItemIds.add(productItemId);
                        itemOrderItemIds.add(orderItemId);

                        int detailIndex = detailOrderItemIds.indexOf(orderItemId);
                        if (detailIndex == -1) {
                            detailOrderItemIds.add(orderItemId);
                            detailProductIds.add(productId);
                            detailQuantities.add(1);
                            detailPrices.add(price);
                        } else {
                            int quantity = detailQuantities.get(detailIndex);
                            detailQuantities.set(detailIndex, quantity + 1);
                        }

                        int productIndex = productIds.indexOf(productId);
                        if (productIndex == -1) {
                            productIds.add(productId);
                            productQuantities.add(1);
                        } else {
                            int quantity = productQuantities.get(productIndex);
                            productQuantities.set(productIndex, quantity + 1);
                        }
                    }
                }

                List<Integer> detailIds = new ArrayList<>();
                String insertDetailSql =
                        "INSERT INTO export_receipt_details"
                        + "(export_receipt_id, order_item_id, product_id, "
                        + "quantity, unit_price) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(
                        insertDetailSql, Statement.RETURN_GENERATED_KEYS)) {
                    for (int i = 0; i < detailOrderItemIds.size(); i++) {
                        ps.setInt(1, exportReceiptId);
                        ps.setInt(2, detailOrderItemIds.get(i));
                        ps.setInt(3, detailProductIds.get(i));
                        ps.setInt(4, detailQuantities.get(i));
                        ps.setDouble(5, detailPrices.get(i));
                        ps.executeUpdate();

                        try (ResultSet rs = ps.getGeneratedKeys()) {
                            if (!rs.next()) {
                                throw new SQLException(
                                        "Cannot create export receipt detail.");
                            }
                            detailIds.add(rs.getInt(1));
                        }
                    }
                }

                String insertSerialSql =
                        "INSERT INTO export_receipt_serials"
                        + "(export_receipt_detail_id, product_item_id) "
                        + "VALUES (?, ?)";
                try (PreparedStatement ps =
                             conn.prepareStatement(insertSerialSql)) {
                    for (int i = 0; i < productItemIds.size(); i++) {
                        int detailIndex = detailOrderItemIds.indexOf(
                                itemOrderItemIds.get(i));
                        if (detailIndex == -1) {
                            throw new SQLException(
                                    "Cannot find export receipt detail for a product.");
                        }
                        ps.setInt(1, detailIds.get(detailIndex));
                        ps.setInt(2, productItemIds.get(i));
                        ps.executeUpdate();
                    }
                }

                String decreaseInventorySql =
                        "UPDATE inventory SET quantity = quantity - ? "
                        + "WHERE product_id = ?";
                String insertMovementSql =
                        "INSERT INTO stock_movement"
                        + "(productid, quantity, type, reference_type, reference_id) "
                        + "VALUES (?, ?, 'DECREASED', 'EXPORT', ?)";
                try (PreparedStatement decreaseInventory =
                             conn.prepareStatement(decreaseInventorySql);
                     PreparedStatement insertMovement =
                             conn.prepareStatement(insertMovementSql)) {
                    for (int i = 0; i < productIds.size(); i++) {
                        decreaseInventory.setInt(1, productQuantities.get(i));
                        decreaseInventory.setInt(2, productIds.get(i));
                        decreaseInventory.executeUpdate();

                        insertMovement.setInt(1, productIds.get(i));
                        insertMovement.setInt(2, productQuantities.get(i));
                        insertMovement.setInt(3, exportReceiptId);
                        insertMovement.executeUpdate();
                    }
                }

                conn.commit();
                return "SUCCESS";
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
                e.printStackTrace();
                if (e.getMessage() == null || e.getMessage().trim().isEmpty()) {
                    return "Unknown system error while saving to Database!";
                }
                return e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() == null || e.getMessage().trim().isEmpty()) {
                return "Unknown system error while saving to Database!";
            }
            return e.getMessage();
        }
    }

    public List<ExportDetailItemDTO> getExportedItemsByOrderId(int orderId) {
        List<ExportDetailItemDTO> list = new ArrayList<>();

        String sql = "SELECT p.name, p.img_url, p.sku, pi.serial, erd.unit_price AS price " +
                "FROM export_receipts er " +
                "JOIN export_receipt_details erd ON er.id = erd.export_receipt_id " +
                "JOIN export_receipt_serials ers ON erd.id = ers.export_receipt_detail_id " +
                "JOIN products p ON erd.product_id = p.productid " +
                "JOIN product_items pi ON ers.product_item_id = pi.id " +
                "WHERE er.order_id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String imgUrl = rs.getString("img_url");
                    String sku = rs.getString("sku");
                    String serial = rs.getString("serial");
                    double price = rs.getDouble("price");

                    list.add(new ExportDetailItemDTO(name, imgUrl, sku, serial, price));
                }
            }
        } catch (Exception e) {
            System.err.println("error");
            e.printStackTrace();
        }

        return list;
    }

    public ExportReceiptInfoDTO getExportReceiptInfoByOrderId(int orderId) {
        String sql = "SELECT er.id AS receipt_id, er.code AS receipt_code, er.order_id, "
                + "o.code AS order_code, o.createdat AS order_created_at, "
                + "created_user.fullname AS sale_created_by, processed_user.fullname AS sale_processed_by "
                + "FROM export_receipts er "
                + "JOIN orders o ON er.order_id = o.id "
                + "LEFT JOIN users created_user ON o.createdby = created_user.userid "
                + "LEFT JOIN users processed_user ON o.processedby = processed_user.userid "
                + "WHERE er.order_id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ExportReceiptInfoDTO(
                            rs.getInt("receipt_id"),
                            rs.getString("receipt_code"),
                            rs.getInt("order_id"),
                            rs.getString("order_code"),
                            rs.getTimestamp("order_created_at"),
                            rs.getString("sale_created_by"),
                            rs.getString("sale_processed_by")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getExportReceiptStatusByOrderId(int orderId) {
        String sql = "SELECT status FROM export_receipts WHERE order_id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
