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
        item.setSerial(rs.getString("serial"));
        item.setType(rs.getString("type"));
        return item;
        return item;
    }

    public List<InventoryAuditItemSerial> getSerialsByAuditItemId(int auditItemId) {
        String sql = "SELECT * FROM inventory_audit_item_serials WHERE audit_item_id = ?";
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
        String sql = "INSERT INTO inventory_audit_item_serials (audit_item_id, serial, type) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serial.getAuditItemId());
            ps.setString(2, serial.getSerial());
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
