package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.InventoryTransactionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryTransactionDAO {
    public List<InventoryTransactionDTO> getCompletedTransaction(String type, int offset, int pageSize) {
        List<InventoryTransactionDTO> list = new ArrayList<>();
        String sql = """
                SELECT * FROM (
                    SELECT id, 'AUDIT' as type, updatedat as date
                    FROM inventory_audit WHERE status = 'COMPLETED'
                    UNION ALL
                    SELECT id, 'IMPORT' as type, created_at as date
                    FROM good_receipts WHERE status = 'COMPLETED'
                    UNION ALL
                    SELECT order_id as id, 'EXPORT' as type, exported_at as date
                    FROM export_receipts WHERE status = 'COMPLETED'
                ) AS combined
                WHERE 1=1
                """;
        if (type != null && !type.isBlank()) {
            sql += " AND type = ? ";
        }
        sql += " ORDER BY date DESC LIMIT ? OFFSET ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int paramIndex = 1;
            if (type != null && !type.isBlank()) {
                ps.setString(paramIndex++, type);
            }
            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex++, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InventoryTransactionDTO(rs.getInt("id"), rs.getString("type"), rs.getTimestamp("date")));
            }
            return list;
        } catch (Exception e) {
            System.out.println("y" + e.getMessage());
        }
        return null;
    }

    public int totalCompletedTransaction(String type) {
        String sql = """
                SELECT COUNT(*) FROM (
                    SELECT id, 'AUDIT' as type FROM inventory_audit WHERE status = 'COMPLETED'
                    UNION ALL
                    SELECT id, 'IMPORT' as type FROM good_receipts WHERE status = 'COMPLETED'
                    UNION ALL
                    SELECT order_id as id, 'EXPORT' as type FROM export_receipts WHERE status = 'COMPLETED'
                ) AS combined
                WHERE 1=1
                """;
        if (type != null && !type.isBlank()) {
            sql += " AND type = ? ";
        }
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (type != null) {
                ps.setString(1, type);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("x" + e.getMessage());
        }
        return 0;
    }
}
