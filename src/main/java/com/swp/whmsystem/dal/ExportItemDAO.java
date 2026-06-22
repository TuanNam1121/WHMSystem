package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.ExportDetailItemDTO;
import com.swp.whmsystem.dto.ExportItemDTO;

import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExportItemDAO {
    public ExportItemDTO getItemBySKU(String sku, int orderId) {
        ExportItemDTO dto = null;

        String sql = "SELECT p.sku, p.name, p.img_url, p.total_quantity, oi.price " +
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
        dto.setStock(rs.getInt("total_quantity"));
        dto.setPrice(rs.getDouble("price"));
        dto.setSerial("");

        return dto;
    }

    public String processExportTransaction(int orderId, List<ExportItemDTO> exportList, String status) {
        Connection conn = null;
        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false);

            // BƯỚC 1: CẬP NHẬT TRẠNG THÁI ORDERS
            String dbStatus = status.toUpperCase();
            String sqlUpdateOrder = "UPDATE orders SET status = ?, updatedat = CURRENT_TIMESTAMP, " +
                    "completedat = CASE WHEN ? = 'COMPLETED' THEN CURRENT_TIMESTAMP ELSE completedat END " +
                    "WHERE id = ?";
            try (PreparedStatement psOrder = conn.prepareStatement(sqlUpdateOrder)) {
                psOrder.setString(1, dbStatus);
                psOrder.setString(2, dbStatus);
                psOrder.setInt(3, orderId);
                psOrder.executeUpdate();
            }

            // BƯỚC 2: CHUẨN BỊ SQL
            String sqlGetIds = "SELECT pi.id AS productitemid, oi.id AS orderitemid, p.productid AS product_id, pi.status " +
                    "FROM product_items pi " +
                    "JOIN products p ON pi.product_id = p.productid " +
                    "JOIN order_items oi ON oi.productid = p.productid " +
                    "WHERE pi.serial = ? AND p.sku = ? AND oi.orderid = ?";

            String sqlMapItem = "INSERT INTO order_items_product_items (orderitemid, productitemid) VALUES (?, ?)";
            String sqlUpdateProductItem = "UPDATE product_items SET status = 'SOLD' WHERE id = ?";
            String sqlUpdateProductQty = "UPDATE products SET total_quantity = total_quantity - 1, updatedat = CURRENT_TIMESTAMP WHERE productid = ?";
            String sqlStockMovement = "INSERT INTO stock_movement (productid, quantity, reference_type, type) VALUES (?, 1, 'EXPORT', 'DECREASED')";

            try (PreparedStatement psGetIds = conn.prepareStatement(sqlGetIds);
                 PreparedStatement psMapItem = conn.prepareStatement(sqlMapItem);
                 PreparedStatement psUpdateProductItem = conn.prepareStatement(sqlUpdateProductItem);
                 PreparedStatement psUpdateQty = conn.prepareStatement(sqlUpdateProductQty);
                 PreparedStatement psMovement = conn.prepareStatement(sqlStockMovement)) {

                for (ExportItemDTO item : exportList) {

                    psGetIds.setString(1, item.getSerial());
                    psGetIds.setString(2, item.getSku());
                    psGetIds.setInt(3, orderId);

                    int productItemId = 0;
                    int orderItemId = 0;
                    int productId = 0;

                    try (ResultSet rs = psGetIds.executeQuery()) {
                        if (rs.next()) {
                            String itemStatus = rs.getString("status");

                            // CHỐT CHẶN VALIDATE S/N: Kiểm tra xem hàng có đang AVAILABLE không
                            if (!"AVAILABLE".equalsIgnoreCase(itemStatus)) {
                                throw new SQLException("S/N [" + item.getSerial() + "] is currently in " + itemStatus + " status and cannot be exported!");
                            }

                            productItemId = rs.getInt("productitemid");
                            orderItemId = rs.getInt("orderitemid");
                            productId = rs.getInt("product_id");
                        } else {
                            throw new SQLException("S/N [" + item.getSerial() + "] does not exist or SKU mismatch!");
                        }
                    }

                    // Map dữ liệu và cập nhật DB
                    psMapItem.setInt(1, orderItemId);
                    psMapItem.setInt(2, productItemId);
                    psMapItem.executeUpdate();

                    psUpdateProductItem.setInt(1, productItemId);
                    psUpdateProductItem.executeUpdate();

                    psUpdateQty.setInt(1, productId);
                    psUpdateQty.executeUpdate();

                    psMovement.setInt(1, productId);
                    psMovement.executeUpdate();
                }
            }

            conn.commit();
            return "SUCCESS";

        } catch (Exception e) {
            System.err.println("=== EXPORT TRANSACTION ERROR ===");
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return e.getMessage() != null ? e.getMessage() : "Unknown system error while saving to Database!";
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            System.err.println(">>> [Lỗi DAO] Không lấy được chi tiết Export cho Order ID: " + orderId);
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
