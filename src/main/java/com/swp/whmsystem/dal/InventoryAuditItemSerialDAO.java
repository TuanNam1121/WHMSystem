package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.InventoryAuditItemSerial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryAuditItemSerialDAO {

    private InventoryAuditItemSerial mapInventoryAuditItemSerial(ResultSet rs) throws SQLException {
        InventoryAuditItemSerial item = new InventoryAuditItemSerial();
        item.setId(rs.getInt("id"));
        item.setAuditItemId(rs.getInt("audit_item_id"));
        item.setProductItemId(rs.getInt("product_item_id"));
        item.setType(rs.getString("type"));
        item.setSerialNumber(rs.getString("serial_number"));
        return item;
    }

    public List<InventoryAuditItemSerial> getSerialsByAuditItemId(int auditItemId) {
        String sql = "SELECT s.*, p.serial AS serial_number FROM inventory_audit_item_serials s " +
                     "JOIN product_items p ON s.product_item_id = p.id " +
                     "WHERE s.audit_item_id = ?";
        List<InventoryAuditItemSerial> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, auditItemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapInventoryAuditItemSerial(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insertSerial(InventoryAuditItemSerial serial) {
        String sql = "INSERT INTO inventory_audit_item_serials (audit_item_id, product_item_id, type) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serial.getAuditItemId());
            ps.setInt(2, serial.getProductItemId());
            ps.setString(3, serial.getType());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteSerialsByAuditItemId(int auditItemId) {
        String sql = "DELETE FROM inventory_audit_item_serials WHERE audit_item_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, auditItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
