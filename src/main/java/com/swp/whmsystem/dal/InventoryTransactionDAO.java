package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.InventoryTransactionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryTransactionDAO {
    public List<InventoryTransactionDTO> getCompletedTransaction(String type, String searchId, String startDate, String endDate, int offset, int pageSize) {
        List<InventoryTransactionDTO> list = new ArrayList<>();
        
        String auditCondition = "";
        String importCondition = "";
        String exportCondition = "";
        if (searchId != null && !searchId.isBlank()) {
            auditCondition = " AND EXISTS (SELECT 1 FROM inventory_audit_items iai JOIN inventory_audit_item_serials iais ON iais.audit_item_id = iai.id JOIN product_items pi ON pi.id = iais.product_item_id WHERE iai.auditid = ia.id AND pi.serial LIKE ?) ";
            importCondition = " AND EXISTS (SELECT 1 FROM good_receipts_items gri JOIN product_items pi ON pi.goodreceiptsitemid = gri.id WHERE gri.goodreceiptid = gr.id AND pi.serial LIKE ?) ";
            exportCondition = " AND EXISTS (SELECT 1 FROM export_receipt_details erd JOIN export_receipt_serials ers ON ers.export_receipt_detail_id = erd.id JOIN product_items pi ON pi.id = ers.product_item_id WHERE erd.export_receipt_id = er.id AND pi.serial LIKE ?) ";
        }

        String sql = """
                SELECT id, type, date, processor
                FROM (
                    SELECT 
                        ia.id AS id, 
                        'AUDIT' AS type, 
                        ia.updatedat AS date, 
                        u.fullname AS processor
                    FROM inventory_audit ia
                    LEFT JOIN users u ON ia.processedby = u.userid
                    WHERE ia.status = 'COMPLETED' """ + auditCondition + """
                    
                    UNION ALL
                    
                    SELECT 
                        gr.id AS id, 
                        'IMPORT' AS type, 
                        gr.created_at AS date, 
                        u.fullname AS processor
                    FROM good_receipts gr
                    LEFT JOIN users u ON gr.processedby = u.userid
                    WHERE gr.status = 'COMPLETED' """ + importCondition + """
                    
                    UNION ALL
                    
                    SELECT 
                        er.order_id AS id, 
                        'EXPORT' AS type, 
                        er.exported_at AS date, 
                        u.fullname AS processor
                    FROM export_receipts er
                    LEFT JOIN users u ON er.exported_by = u.userid
                    WHERE er.status = 'COMPLETED' """ + exportCondition + """
                ) AS combined
                WHERE 1=1
                """;
        if (type != null && !type.isBlank()) {
            sql += " AND type = ? ";
        }
        if (startDate != null && !startDate.isBlank()) {
            sql += " AND date >= ? ";
        }
        if (endDate != null && !endDate.isBlank()) {
            sql += " AND date <= ? ";
        }
        sql += " ORDER BY date DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchId != null && !searchId.isBlank()) {
                String searchPattern = "%" + searchId.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (type != null && !type.isBlank()) {
                ps.setString(paramIndex++, type);
            }
            if (startDate != null && !startDate.isBlank()) {
                ps.setString(paramIndex++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.isBlank()) {
                ps.setString(paramIndex++, endDate + " 23:59:59");
            }
            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex++, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InventoryTransactionDTO(
                    rs.getInt("id"), 
                    rs.getString("type"), 
                    rs.getTimestamp("date"),
                    rs.getString("processor")
                ));
            }
            return list;
        } catch (Exception e) {
            System.out.println("y" + e.getMessage());
        }
        return null;
    }

    public int totalCompletedTransaction(String type, String searchId, String startDate, String endDate) {
        String auditCondition = "";
        String importCondition = "";
        String exportCondition = "";
        if (searchId != null && !searchId.isBlank()) {
            auditCondition = " AND EXISTS (SELECT 1 FROM inventory_audit_items iai JOIN inventory_audit_item_serials iais ON iais.audit_item_id = iai.id JOIN product_items pi ON pi.id = iais.product_item_id WHERE iai.auditid = ia.id AND pi.serial LIKE ?) ";
            importCondition = " AND EXISTS (SELECT 1 FROM good_receipts_items gri JOIN product_items pi ON pi.goodreceiptsitemid = gri.id WHERE gri.goodreceiptid = gr.id AND pi.serial LIKE ?) ";
            exportCondition = " AND EXISTS (SELECT 1 FROM export_receipt_details erd JOIN export_receipt_serials ers ON ers.export_receipt_detail_id = erd.id JOIN product_items pi ON pi.id = ers.product_item_id WHERE erd.export_receipt_id = er.id AND pi.serial LIKE ?) ";
        }

        String sql = """
                SELECT COUNT(*) FROM (
                    SELECT ia.id, 'AUDIT' as type, ia.updatedat as date FROM inventory_audit ia WHERE ia.status = 'COMPLETED' """ + auditCondition + """
                    UNION ALL
                    SELECT gr.id, 'IMPORT' as type, gr.created_at as date FROM good_receipts gr WHERE gr.status = 'COMPLETED' """ + importCondition + """
                    UNION ALL
                    SELECT er.order_id as id, 'EXPORT' as type, er.exported_at as date FROM export_receipts er WHERE er.status = 'COMPLETED' """ + exportCondition + """
                ) AS combined
                WHERE 1=1
                """;
        if (type != null && !type.isBlank()) {
            sql += " AND type = ? ";
        }
        if (startDate != null && !startDate.isBlank()) {
            sql += " AND date >= ? ";
        }
        if (endDate != null && !endDate.isBlank()) {
            sql += " AND date <= ? ";
        }
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchId != null && !searchId.isBlank()) {
                String searchPattern = "%" + searchId.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (type != null && !type.isBlank()) {
                ps.setString(paramIndex++, type);
            }
            if (startDate != null && !startDate.isBlank()) {
                ps.setString(paramIndex++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.isBlank()) {
                ps.setString(paramIndex++, endDate + " 23:59:59");
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
