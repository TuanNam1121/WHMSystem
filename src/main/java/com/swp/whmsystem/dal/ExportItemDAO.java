package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.ExportDetailItemDTO;
import com.swp.whmsystem.dto.ExportItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExportItemDAO {
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

    public String processExportTransaction(int orderId, List<ExportItemDTO> exportList) {
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            try {
                String updateOrderStatusSql =
                        "UPDATE orders SET status = 'COMPLETED', "
                                + "updatedat = CURRENT_TIMESTAMP, "
                                + "completedat = CURRENT_TIMESTAMP WHERE id = ?";

                try (PreparedStatement updateOrderStatus =
                             conn.prepareStatement(updateOrderStatusSql)) {
                    updateOrderStatus.setInt(1, orderId);
                    updateOrderStatus.executeUpdate();
                }

                String findProductItemSql =
                        "SELECT pi.id, pi.product_id, pi.status, oi.id AS order_item_id, oi.price "
                                + "FROM product_items pi "
                                + "JOIN products p ON pi.product_id = p.productid "
                                + "JOIN order_items oi ON pi.product_id = oi.productid "
                                + "WHERE pi.serial = ? AND p.sku = ? AND oi.orderid = ?";
                String insertOrderProductItemSql =
                        "INSERT INTO order_items_product_items(orderitemid, productitemid) VALUES (?, ?)";
                String updateProductItemSql =
                        "UPDATE product_items SET status = 'SOLD', export_price = ? WHERE id = ?";
                String decreaseProductQuantitySql =
                        "UPDATE products SET total_quantity = total_quantity - ?, "
                                + "updatedat = CURRENT_TIMESTAMP WHERE productid = ?";
                String insertStockMovementSql =
                        "INSERT INTO stock_movement(productid, quantity, reference_type, type) "
                                + "VALUES (?, ?, 'EXPORT', 'DECREASED')";

                List<Integer> productIds = new ArrayList<>();
                List<Integer> quantities = new ArrayList<>();

                try (PreparedStatement findProductItem =
                             conn.prepareStatement(findProductItemSql);
                     PreparedStatement insertOrderProductItem =
                             conn.prepareStatement(insertOrderProductItemSql);
                     PreparedStatement updateProductItem =
                             conn.prepareStatement(updateProductItemSql);
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

                        insertOrderProductItem.setInt(1, orderItemId);
                        insertOrderProductItem.setInt(2, productItemId);
                        insertOrderProductItem.executeUpdate();

                        updateProductItem.setDouble(1, price);
                        updateProductItem.setInt(2, productItemId);
                        updateProductItem.executeUpdate();

                        int index = productIds.indexOf(productId);
                        if (index == -1) {
                            productIds.add(productId);
                            quantities.add(1);
                        } else {
                            quantities.set(index, quantities.get(index) + 1);
                        }
                    }

                    for (int i = 0; i < productIds.size(); i++) {
                        decreaseProductQuantity.setInt(1, quantities.get(i));
                        decreaseProductQuantity.setInt(2, productIds.get(i));
                        decreaseProductQuantity.executeUpdate();

                        insertStockMovement.setInt(1, productIds.get(i));
                        insertStockMovement.setInt(2, quantities.get(i));
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

    public List<ExportDetailItemDTO> getExportedItemsByOrderId(int orderId) {
        List<ExportDetailItemDTO> list = new ArrayList<>();

        String sql = "SELECT p.name, p.img_url, p.sku, pi.serial, oi.price " +
                "FROM order_items oi " +
                "JOIN products p ON oi.productid = p.productid " +
                "JOIN order_items_product_items oipi ON oi.id = oipi.orderitemid " +
                "JOIN product_items pi ON oipi.productitemid = pi.id " +
                "WHERE oi.orderid = ?";

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
