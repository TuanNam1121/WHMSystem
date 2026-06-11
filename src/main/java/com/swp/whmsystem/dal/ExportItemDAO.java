package com.swp.whmsystem.dal;

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

    public static void main(String[] args) {
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        ExportItemDTO dto = exportItemDAO.getItemBySKU("D15-23");
        System.out.println(dto);
    }
}
