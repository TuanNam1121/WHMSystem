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

    public ExportItemDTO getItemBySKU(String sku, int orderId) {
        ExportItemDTO dto = null;

        String sql = "SELECT p.sku, p.name, p.img_url, oi.price, " +
                "(SELECT COUNT(*) FROM product_items pi " +
                "WHERE pi.product_id = p.productid AND pi.status = 'AVAILABLE') AS available_quantity " +
                "FROM order_items oi " +
                "JOIN products p ON oi.productid = p.productid " +
                "WHERE p.sku = ? AND oi.orderid = ? AND p.isactive = 1";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sku);
            ps.setInt(2, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = mapResultSetToExportItemDTO(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
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
                deleteDraftReceiptIfExists(conn, orderId);

                String findProductItemSql =
                        "SELECT pi.id, pi.product_id, pi.status, oi.id AS order_item_id, oi.price "
                                + "FROM product_items pi "
                                + "JOIN products p ON pi.product_id = p.productid "
                                + "JOIN order_items oi ON pi.product_id = oi.productid "
                                + "WHERE pi.serial = ? AND p.sku = ? AND oi.orderid = ?";
                String updateOrderStatusSql =
                        "UPDATE orders SET status = 'COMPLETED', processedby = ?, "
                                + "updatedat = CURRENT_TIMESTAMP, completedat = CURRENT_TIMESTAMP "
                                + "WHERE id = ?";
                String insertExportReceiptSql =
                        "INSERT INTO export_receipts(code, order_id, status, created_by, exported_by, exported_at) "
                                + "VALUES (?, ?, 'COMPLETED', ?, ?, CURRENT_TIMESTAMP)";
                String insertExportReceiptDetailSql =
                        "INSERT INTO export_receipt_details(export_receipt_id, order_item_id, product_id, quantity, unit_price) "
                                + "VALUES (?, ?, ?, ?, ?)";
                String insertExportReceiptSerialSql =
                        "INSERT INTO export_receipt_serials(export_receipt_detail_id, product_item_id) "
                                + "VALUES (?, ?)";
                String updateProductItemSql =
                        "UPDATE product_items SET status = 'SOLD', export_price = ? WHERE id = ?";
                String decreaseProductQuantitySql =
                        "UPDATE inventory SET quantity = quantity - ? WHERE product_id = ?";
                String insertStockMovementSql =
                        "INSERT INTO stock_movement(productid, quantity, type, reference_type, reference_id) "
                                + "VALUES (?, ?, 'DECREASED', 'EXPORT', ?)";

                int exportReceiptId;
                String exportReceiptCode = "PX-" + orderId + "-" + System.currentTimeMillis();

                try (PreparedStatement updateOrderStatus =
                             conn.prepareStatement(updateOrderStatusSql)) {
                    updateOrderStatus.setInt(1, userId);
                    updateOrderStatus.setInt(2, orderId);
                    updateOrderStatus.executeUpdate();
                }

                try (PreparedStatement insertExportReceipt =
                             conn.prepareStatement(insertExportReceiptSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertExportReceipt.setString(1, exportReceiptCode);
                    insertExportReceipt.setInt(2, orderId);
                    insertExportReceipt.setInt(3, userId);
                    insertExportReceipt.setInt(4, userId);
                    insertExportReceipt.executeUpdate();

                    try (ResultSet rs = insertExportReceipt.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Cannot create export receipt.");
                        }
                        exportReceiptId = rs.getInt(1);
                    }
                }

                List<Integer> productItemIds = new ArrayList<>();
                List<Integer> itemOrderItemIds = new ArrayList<>();
                List<Integer> productIds = new ArrayList<>();
                List<Integer> quantities = new ArrayList<>();
                List<Integer> detailOrderItemIds = new ArrayList<>();
                List<Integer> detailProductIds = new ArrayList<>();
                List<Integer> detailQuantities = new ArrayList<>();
                List<Double> detailPrices = new ArrayList<>();
                List<Integer> detailIds = new ArrayList<>();

                try (PreparedStatement findProductItem =
                             conn.prepareStatement(findProductItemSql);
                     PreparedStatement updateProductItem =
                             conn.prepareStatement(updateProductItemSql);
                     PreparedStatement insertExportReceiptDetail =
                             conn.prepareStatement(insertExportReceiptDetailSql, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement insertExportReceiptSerial =
                             conn.prepareStatement(insertExportReceiptSerialSql);
                     PreparedStatement decreaseProductQuantity =
                             conn.prepareStatement(decreaseProductQuantitySql);
                     PreparedStatement insertStockMovement =
                             conn.prepareStatement(insertStockMovementSql)) {

                    for (ExportItemDTO item : exportList) {
                        findProductItem.setString(1, item.getSerial());
                        findProductItem.setString(2, item.getSku());
                        findProductItem.setInt(3, orderId);

                        int productItemId;
                        int orderItemId;
                        int productId;
                        double price;

                        try (ResultSet rs = findProductItem.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException("Serial number " + item.getSerial()
                                        + " does not exist or does not belong to the selected product.");
                            }
                            if (!"AVAILABLE".equalsIgnoreCase(rs.getString("status"))) {
                                throw new SQLException("Serial number " + item.getSerial()
                                        + " is currently " + rs.getString("status")
                                        + " and cannot be exported.");
                            }

                            productItemId = rs.getInt("id");
                            orderItemId = rs.getInt("order_item_id");
                            productId = rs.getInt("product_id");
                            price = rs.getDouble("price");
                        }

                        updateProductItem.setDouble(1, price);
                        updateProductItem.setInt(2, productItemId);
                        updateProductItem.executeUpdate();

                        productItemIds.add(productItemId);
                        itemOrderItemIds.add(orderItemId);

                        int index = productIds.indexOf(productId);
                        if (index == -1) {
                            productIds.add(productId);
                            quantities.add(1);
                        } else {
                            quantities.set(index, quantities.get(index) + 1);
                        }

                        int detailIndex = detailOrderItemIds.indexOf(orderItemId);
                        if (detailIndex == -1) {
                            detailOrderItemIds.add(orderItemId);
                            detailProductIds.add(productId);
                            detailQuantities.add(1);
                            detailPrices.add(price);
                        } else {
                            detailQuantities.set(detailIndex,
                                    detailQuantities.get(detailIndex) + 1);
                        }
                    }

                    for (int i = 0; i < detailOrderItemIds.size(); i++) {
                        insertExportReceiptDetail.setInt(1, exportReceiptId);
                        insertExportReceiptDetail.setInt(2, detailOrderItemIds.get(i));
                        insertExportReceiptDetail.setInt(3, detailProductIds.get(i));
                        insertExportReceiptDetail.setInt(4, detailQuantities.get(i));
                        insertExportReceiptDetail.setDouble(5, detailPrices.get(i));
                        insertExportReceiptDetail.executeUpdate();

                        try (ResultSet rs = insertExportReceiptDetail.getGeneratedKeys()) {
                            if (!rs.next()) {
                                throw new SQLException("Cannot create export receipt detail.");
                            }
                            detailIds.add(rs.getInt(1));
                        }
                    }

                    for (int i = 0; i < productItemIds.size(); i++) {
                        int detailIndex = detailOrderItemIds.indexOf(itemOrderItemIds.get(i));

                        insertExportReceiptSerial.setInt(1, detailIds.get(detailIndex));
                        insertExportReceiptSerial.setInt(2, productItemIds.get(i));
                        insertExportReceiptSerial.executeUpdate();
                    }

                    for (int i = 0; i < productIds.size(); i++) {
                        decreaseProductQuantity.setInt(1, quantities.get(i));
                        decreaseProductQuantity.setInt(2, productIds.get(i));
                        decreaseProductQuantity.executeUpdate();

                        insertStockMovement.setInt(1, productIds.get(i));
                        insertStockMovement.setInt(2, quantities.get(i));
                        insertStockMovement.setInt(3, exportReceiptId);
                        insertStockMovement.executeUpdate();
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
                return e.getMessage() == null
                        ? "Unknown system error while saving to Database!"
                        : e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage() != null ? e.getMessage() : "Unknown system error while saving to Database!";
        }
    }

    public String saveDraftExportReceipt(int orderId, int userId, List<ExportItemDTO> exportList) {
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            try {
                String currentStatus = getExportReceiptStatusByOrderId(orderId);
                if ("COMPLETED".equalsIgnoreCase(currentStatus)) {
                    return "This order has already been exported.";
                }

                deleteDraftReceiptIfExists(conn, orderId);

                String findProductItemSql =
                        "SELECT pi.id, pi.product_id, pi.status, oi.id AS order_item_id, oi.price "
                                + "FROM product_items pi "
                                + "JOIN products p ON pi.product_id = p.productid "
                                + "JOIN order_items oi ON pi.product_id = oi.productid "
                                + "WHERE pi.serial = ? AND p.sku = ? AND oi.orderid = ?";
                String insertExportReceiptSql =
                        "INSERT INTO export_receipts(code, order_id, status, created_by) "
                                + "VALUES (?, ?, 'DRAFT', ?)";
                String insertExportReceiptDetailSql =
                        "INSERT INTO export_receipt_details(export_receipt_id, order_item_id, product_id, quantity, unit_price) "
                                + "VALUES (?, ?, ?, ?, ?)";
                String insertExportReceiptSerialSql =
                        "INSERT INTO export_receipt_serials(export_receipt_detail_id, product_item_id) "
                                + "VALUES (?, ?)";

                int exportReceiptId;
                String exportReceiptCode = "PX-DRAFT-" + orderId + "-" + System.currentTimeMillis();

                try (PreparedStatement insertExportReceipt =
                             conn.prepareStatement(insertExportReceiptSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertExportReceipt.setString(1, exportReceiptCode);
                    insertExportReceipt.setInt(2, orderId);
                    insertExportReceipt.setInt(3, userId);
                    insertExportReceipt.executeUpdate();

                    try (ResultSet rs = insertExportReceipt.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Cannot create draft export receipt.");
                        }
                        exportReceiptId = rs.getInt(1);
                    }
                }

                List<Integer> productItemIds = new ArrayList<>();
                List<Integer> itemOrderItemIds = new ArrayList<>();
                List<Integer> detailOrderItemIds = new ArrayList<>();
                List<Integer> detailProductIds = new ArrayList<>();
                List<Integer> detailQuantities = new ArrayList<>();
                List<Double> detailPrices = new ArrayList<>();
                List<Integer> detailIds = new ArrayList<>();

                try (PreparedStatement findProductItem =
                             conn.prepareStatement(findProductItemSql);
                     PreparedStatement insertExportReceiptDetail =
                             conn.prepareStatement(insertExportReceiptDetailSql, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement insertExportReceiptSerial =
                             conn.prepareStatement(insertExportReceiptSerialSql)) {

                    for (ExportItemDTO item : exportList) {
                        findProductItem.setString(1, item.getSerial());
                        findProductItem.setString(2, item.getSku());
                        findProductItem.setInt(3, orderId);

                        int productItemId;
                        int orderItemId;
                        int productId;
                        double price;

                        try (ResultSet rs = findProductItem.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException("Serial number " + item.getSerial()
                                        + " does not exist or does not belong to the selected product.");
                            }
                            if (!"AVAILABLE".equalsIgnoreCase(rs.getString("status"))) {
                                throw new SQLException("Serial number " + item.getSerial()
                                        + " is currently " + rs.getString("status")
                                        + " and cannot be saved.");
                            }

                            productItemId = rs.getInt("id");
                            orderItemId = rs.getInt("order_item_id");
                            productId = rs.getInt("product_id");
                            price = rs.getDouble("price");
                        }

                        productItemIds.add(productItemId);
                        itemOrderItemIds.add(orderItemId);

                        int detailIndex = detailOrderItemIds.indexOf(orderItemId);
                        if (detailIndex == -1) {
                            detailOrderItemIds.add(orderItemId);
                            detailProductIds.add(productId);
                            detailQuantities.add(1);
                            detailPrices.add(price);
                        } else {
                            detailQuantities.set(detailIndex,
                                    detailQuantities.get(detailIndex) + 1);
                        }
                    }

                    for (int i = 0; i < detailOrderItemIds.size(); i++) {
                        insertExportReceiptDetail.setInt(1, exportReceiptId);
                        insertExportReceiptDetail.setInt(2, detailOrderItemIds.get(i));
                        insertExportReceiptDetail.setInt(3, detailProductIds.get(i));
                        insertExportReceiptDetail.setInt(4, detailQuantities.get(i));
                        insertExportReceiptDetail.setDouble(5, detailPrices.get(i));
                        insertExportReceiptDetail.executeUpdate();

                        try (ResultSet rs = insertExportReceiptDetail.getGeneratedKeys()) {
                            if (!rs.next()) {
                                throw new SQLException("Cannot create draft export receipt detail.");
                            }
                            detailIds.add(rs.getInt(1));
                        }
                    }

                    for (int i = 0; i < productItemIds.size(); i++) {
                        int detailIndex = detailOrderItemIds.indexOf(itemOrderItemIds.get(i));

                        insertExportReceiptSerial.setInt(1, detailIds.get(detailIndex));
                        insertExportReceiptSerial.setInt(2, productItemIds.get(i));
                        insertExportReceiptSerial.executeUpdate();
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
                return e.getMessage() == null
                        ? "Unknown system error while saving draft!"
                        : e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage() != null ? e.getMessage() : "Unknown system error while saving draft!";
        }
    }

    private void deleteDraftReceiptIfExists(Connection conn, int orderId) throws SQLException {
        String findDraftReceiptSql =
                "SELECT id, status FROM export_receipts WHERE order_id = ?";
        String deleteDraftSerialsSql =
                "DELETE ers FROM export_receipt_serials ers "
                        + "JOIN export_receipt_details erd ON ers.export_receipt_detail_id = erd.id "
                        + "WHERE erd.export_receipt_id = ?";
        String deleteDraftDetailsSql =
                "DELETE FROM export_receipt_details WHERE export_receipt_id = ?";
        String deleteDraftReceiptSql =
                "DELETE FROM export_receipts WHERE id = ?";

        int exportReceiptId = 0;
        String status = null;

        try (PreparedStatement findDraftReceipt =
                     conn.prepareStatement(findDraftReceiptSql)) {
            findDraftReceipt.setInt(1, orderId);

            try (ResultSet rs = findDraftReceipt.executeQuery()) {
                if (rs.next()) {
                    exportReceiptId = rs.getInt("id");
                    status = rs.getString("status");
                }
            }
        }

        if (exportReceiptId == 0) {
            return;
        }

        if (!"DRAFT".equalsIgnoreCase(status)) {
            throw new SQLException("This order has already been exported.");
        }

        try (PreparedStatement deleteDraftSerials =
                     conn.prepareStatement(deleteDraftSerialsSql);
             PreparedStatement deleteDraftDetails =
                     conn.prepareStatement(deleteDraftDetailsSql);
             PreparedStatement deleteDraftReceipt =
                     conn.prepareStatement(deleteDraftReceiptSql)) {

            deleteDraftSerials.setInt(1, exportReceiptId);
            deleteDraftSerials.executeUpdate();

            deleteDraftDetails.setInt(1, exportReceiptId);
            deleteDraftDetails.executeUpdate();

            deleteDraftReceipt.setInt(1, exportReceiptId);
            deleteDraftReceipt.executeUpdate();
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
        String sql = "SELECT er.id AS receipt_id, er.order_id, o.createdat AS order_created_at, "
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
                            rs.getInt("order_id"),
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

    public List<ExportItemDTO> getDraftItemsByOrderId(int orderId) {
        List<ExportItemDTO> list = new ArrayList<>();

        String sql = "SELECT p.sku, p.name, p.img_url, erd.unit_price AS price, "
                + "pi.serial, "
                + "(SELECT COUNT(*) FROM product_items pi2 "
                + "WHERE pi2.product_id = p.productid AND pi2.status = 'AVAILABLE') AS available_quantity "
                + "FROM export_receipts er "
                + "JOIN export_receipt_details erd ON er.id = erd.export_receipt_id "
                + "JOIN export_receipt_serials ers ON erd.id = ers.export_receipt_detail_id "
                + "JOIN product_items pi ON ers.product_item_id = pi.id "
                + "JOIN products p ON erd.product_id = p.productid "
                + "WHERE er.order_id = ? AND er.status = 'DRAFT'";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExportItemDTO dto = new ExportItemDTO();
                    dto.setSku(rs.getString("sku"));
                    dto.setName(rs.getString("name"));
                    dto.setImgUrl(rs.getString("img_url"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setStock(rs.getInt("available_quantity"));
                    dto.setSerial(rs.getString("serial"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        ExportItemDTO dto = exportItemDAO.getItemBySKU("B12-423", 4);
        System.out.println(dto);

        List<ExportDetailItemDTO> list = exportItemDAO.getExportedItemsByOrderId(4);
        for (ExportDetailItemDTO e : list) {
            System.out.println(e);
        }
    }
}
