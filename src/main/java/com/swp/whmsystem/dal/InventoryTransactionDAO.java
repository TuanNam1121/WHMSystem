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
                SELECT id, type, date, processor, total_items
                FROM (
                    SELECT 
                        ia.id AS id, 
                        'AUDIT' AS type, 
                        ia.updatedat AS date, 
                        u.fullname AS processor,
                        COALESCE((SELECT SUM(iai.physicalquantity) FROM inventory_audit_items iai WHERE iai.auditid = ia.id), 0) AS total_items
                    FROM inventory_audit ia
                    LEFT JOIN users u ON ia.processedby = u.userid
                    WHERE ia.status = 'COMPLETED'
                    
                    UNION ALL
                    
                    SELECT 
                        gr.id AS id, 
                        'IMPORT' AS type, 
                        gr.created_at AS date, 
                        u.fullname AS processor,
                        COALESCE((SELECT SUM(gri.actual_quantity) FROM good_receipts_items gri WHERE gri.goodreceiptid = gr.id), 0) AS total_items
                    FROM good_receipts gr
                    LEFT JOIN users u ON gr.processedby = u.userid
                    WHERE gr.status = 'COMPLETED'
                    
                    UNION ALL
                    
                    SELECT 
                        er.order_id AS id, 
                        'EXPORT' AS type, 
                        er.exported_at AS date, 
                        u.fullname AS processor,
                        COALESCE((SELECT SUM(erd.quantity) FROM export_receipt_details erd WHERE erd.export_receipt_id = er.id), 0) AS total_items
                    FROM export_receipts er
                    LEFT JOIN users u ON er.exported_by = u.userid
                    WHERE er.status = 'COMPLETED'
                ) AS combined
                WHERE 1=1
                """;
        if (type != null && !type.isBlank()) {
            sql += " AND type = ? ";
        }
        sql += " ORDER BY date DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (type != null && !type.isBlank()) {
                ps.setString(paramIndex++, type);
            }
            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex++, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InventoryTransactionDTO(
                    rs.getInt("id"), 
                    rs.getString("type"), 
                    rs.getTimestamp("date"),
                    rs.getString("processor"),
                    rs.getInt("total_items")
                ));
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
