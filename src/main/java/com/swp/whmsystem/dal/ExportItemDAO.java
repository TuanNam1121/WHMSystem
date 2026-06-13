package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.ExportDetailItemDTO;
import com.swp.whmsystem.dto.ExportItemDTO;

import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExportItemDAO {
    public ExportItemDTO getItemBySKU(String sku) {
        ExportItemDTO dto = null;

        String sql = "SELECT p.sku, p.name, p.img_url, p.total_quantity, " +
                "(SELECT current_price FROM product_items pi WHERE pi.product_id = p.productid LIMIT 1) AS price " +
                "FROM products p " +
                "WHERE p.sku = ? AND p.isactive = 1";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sku);

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

    public boolean processExportTransaction(int orderId, List<ExportItemDTO> exportList, String status) {
        Connection conn = null;
        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false); // BẮT ĐẦU TRANSACTION

            // =====================================================================
            // BƯỚC 1: CẬP NHẬT TRẠNG THÁI ORDERS + THỜI GIAN (updatedat, completedat)
            // =====================================================================
            // Dùng hàm UPPER để đảm bảo status luôn là chữ IN HOA khớp với ENUM ('NEW', 'DOING', 'COMPLETED')
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

            // =====================================================================
            // CHUẨN BỊ SQL CHO CÁC BƯỚC TRONG VÒNG LẶP (KHỚP 100% CẤU TRÚC DB)
            // =====================================================================

            // Tìm chính xác ID của sản phẩm, mã seri và chi tiết đơn hàng
            String sqlGetIds = "SELECT pi.id AS productitemid, oi.id AS orderitemid, p.productid AS product_id " +
                    "FROM product_items pi " +
                    "JOIN products p ON pi.product_id = p.productid " +
                    "JOIN order_items oi ON oi.productid = p.productid " +
                    "WHERE pi.serial = ? AND p.sku = ? AND oi.orderid = ?";

            String sqlMapItem = "INSERT INTO order_items_product_items (orderitemid, productitemid) VALUES (?, ?)";

            // ĐÃ FIX THEO ENUM CỦA BRO: Dùng chữ 'SOLD' thay vì 'EXPORTED'
            String sqlUpdateProductItem = "UPDATE product_items SET status = 'SOLD' WHERE id = ?";

            // Cập nhật tồn kho (total_quantity) và giờ cập nhật (updatedat)
            String sqlUpdateProductQty = "UPDATE products SET total_quantity = total_quantity - 1, updatedat = CURRENT_TIMESTAMP WHERE productid = ?";

            // Ghi log vào stock_movement
            String sqlStockMovement = "INSERT INTO stock_movement (productid, quantity, reference_type, type) " +
                    "VALUES (?, 1, 'EXPORT', 'DECREASED')";

            try (PreparedStatement psGetIds = conn.prepareStatement(sqlGetIds);
                 PreparedStatement psMapItem = conn.prepareStatement(sqlMapItem);
                 PreparedStatement psUpdateProductItem = conn.prepareStatement(sqlUpdateProductItem);
                 PreparedStatement psUpdateQty = conn.prepareStatement(sqlUpdateProductQty);
                 PreparedStatement psMovement = conn.prepareStatement(sqlStockMovement)) {

                for (ExportItemDTO item : exportList) {

                    // 1. TÌM ID
                    psGetIds.setString(1, item.getSerial());
                    psGetIds.setString(2, item.getSku());
                    psGetIds.setInt(3, orderId);

                    int productItemId = 0;
                    int orderItemId = 0;
                    int productId = 0;

                    try (ResultSet rs = psGetIds.executeQuery()) {
                        if (rs.next()) {
                            productItemId = rs.getInt("productitemid");
                            orderItemId = rs.getInt("orderitemid");
                            productId = rs.getInt("product_id");
                        } else {
                            throw new SQLException("Không tìm thấy Order Item hoặc Serial Number hợp lệ: " + item.getSerial());
                        }
                    }

                    // 2. MAP VÀO BẢNG order_items_product_items
                    psMapItem.setInt(1, orderItemId);
                    psMapItem.setInt(2, productItemId);
                    psMapItem.executeUpdate();

                    // 3. ĐỔI TRẠNG THÁI SẢN PHẨM VẬT LÝ VỀ 'SOLD'
                    psUpdateProductItem.setInt(1, productItemId);
                    psUpdateProductItem.executeUpdate();

                    // 4. TRỪ TỒN KHO TỔNG BẢNG products
                    psUpdateQty.setInt(1, productId);
                    psUpdateQty.executeUpdate();

                    // 5. GHI LOG LỊCH SỬ KHO (stock_movement)
                    psMovement.setInt(1, productId);
                    psMovement.executeUpdate();
                }
            }

            // CHỐT GIAO DỊCH XUỐNG DB
            conn.commit();
            return true;

        } catch (Exception e) {
            System.err.println("=== LỖI TRANSACTION XUẤT KHO ===");
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Có biến là quay xe ngay lập tức
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
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
        ExportItemDTO dto = exportItemDAO.getItemBySKU("D15-23");
        System.out.println(dto);

        List<ExportDetailItemDTO> list = exportItemDAO.getExportedItemsByOrderId(4);
        for (ExportDetailItemDTO e : list) {
            System.out.println(e);
        }
    }
}
