package com.swp.whmsystem.dal;

import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.model.InventoryAuditItem;
import com.swp.whmsystem.model.InventoryAuditItemSerial;
import com.swp.whmsystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryAuditDAO {
    private InventoryAudit mapInventoryAudit(ResultSet rs) throws SQLException {
        InventoryAudit inventoryAudit = new InventoryAudit();
        inventoryAudit.setId(rs.getInt("id"));
        inventoryAudit.setUserId(rs.getInt("createdby"));
        inventoryAudit.setStatus(InventoryAuditStatus.valueOf(rs.getString("status")));
        inventoryAudit.setCreatedAt(rs.getObject("createdat", LocalDateTime.class));
        inventoryAudit.setUpdatedAt(rs.getObject("updatedat", LocalDateTime.class));
        inventoryAudit.setCodeId(rs.getString("code"));
        User creator = new User();
        creator.setId(rs.getInt("createdby"));
        creator.setFullName(rs.getString("creator_fullname"));
        inventoryAudit.setCreator(creator);

        int processedById = rs.getInt("processedby");
        if (!rs.wasNull()) {
            User processor = new User();
            processor.setId(processedById);
            processor.setFullName(rs.getString("processor_fullname"));
            inventoryAudit.setProcessor(processor);
        } else {
            inventoryAudit.setProcessor(null);
        }

        return inventoryAudit;
    }

    public List<InventoryAudit> getInventoryAuditsByFilter(String searchId, String startDate, String endDate, int offset, int limit, int userid) {
        String sql = "SELECT ia.*, u1.userid AS userid, u1.fullname AS creator_fullname, u2.fullname AS processor_fullname "
                +
                "FROM inventory_audit ia " +
                "JOIN users u1 ON ia.createdby = u1.userid " +
                "LEFT JOIN users u2 ON ia.processedby = u2.userid " +
                "WHERE 1=1";
        if (searchId != null && !searchId.trim().isEmpty()) {
            sql += " AND (ia.code LIKE ? OR EXISTS (SELECT 1 FROM inventory_audit_items iai JOIN inventory_audit_item_serials iais ON iais.audit_item_id = iai.id JOIN product_items pi ON pi.id = iais.product_item_id WHERE iai.auditid = ia.id AND pi.serial LIKE ?)) ";
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql += " AND ia.createdat >= ? ";
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql += " AND ia.createdat <= ? ";
        }
        sql += " AND ( (ia.status = ? and u1.userid=?) or ia.status != ?)";
        sql += " ORDER BY ia.createdat DESC LIMIT ? OFFSET ?";

        List<InventoryAudit> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchId != null && !searchId.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchId.trim() + "%");
                ps.setString(paramIndex++, "%" + searchId.trim() + "%");
            }
            if (startDate != null && !startDate.trim().isEmpty()) {
                ps.setString(paramIndex++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.trim().isEmpty()) {
                ps.setString(paramIndex++, endDate + " 23:59:59");
            }
            ps.setString(paramIndex++, InventoryAuditStatus.DRAFT.name());
            ps.setInt(paramIndex++, userid);
            ps.setString(paramIndex++, InventoryAuditStatus.DRAFT.name());
            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapInventoryAudit(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int countInventoryAuditsByFilter(String searchId, String startDate, String endDate, int userid) {
        String sql = "SELECT COUNT(*) FROM inventory_audit ia JOIN users ON ia.createdby = users.userid WHERE 1=1";
        if (searchId != null && !searchId.trim().isEmpty()) {
            sql += " AND (ia.code LIKE ? OR EXISTS (SELECT 1 FROM inventory_audit_items iai JOIN inventory_audit_item_serials iais ON iais.audit_item_id = iai.id JOIN product_items pi ON pi.id = iais.product_item_id WHERE iai.auditid = ia.id AND pi.serial LIKE ?)) ";
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql += " AND ia.createdat >= ? ";
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql += " AND ia.createdat <= ? ";
        }
        sql += " AND ( (ia.status = ? and users.userid=?) or ia.status != ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchId != null && !searchId.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchId.trim() + "%");
                ps.setString(paramIndex++, "%" + searchId.trim() + "%");
            }
            if (startDate != null && !startDate.trim().isEmpty()) {
                ps.setString(paramIndex++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.trim().isEmpty()) {
                ps.setString(paramIndex++, endDate + " 23:59:59");
            }
            ps.setString(paramIndex++, InventoryAuditStatus.DRAFT.name());
            ps.setInt(paramIndex++, userid);
            ps.setString(paramIndex++, InventoryAuditStatus.DRAFT.name());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public InventoryAudit getInventoryAuditById(int id) {
        String sql = """
                SELECT ia.*, u1.userid AS userid, u1.fullname AS creator_fullname, u2.fullname AS processor_fullname
                FROM inventory_audit ia
                JOIN users u1 ON ia.createdby = u1.userid
                LEFT JOIN users u2 ON ia.processedby = u2.userid
                WHERE ia.id = ?
                """;
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    InventoryAudit audit = mapInventoryAudit(rs);
                    audit.setInventoryAuditItems(getInventoryAuditItemsByAuditId(id));
                    return audit;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<InventoryAuditItem> getInventoryAuditItemsByAuditId(int auditId) {
        String sql = """
                SELECT i.*, p.name AS productname, p.sku AS productsku, c.name AS categoryname
                FROM inventory_audit_items i
                LEFT JOIN products p ON i.productid = p.productid
                LEFT JOIN categories c ON p.categoryid = c.categoryid
                WHERE i.auditid = ?
                """;
        List<InventoryAuditItem> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, auditId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventoryAuditItem item = new InventoryAuditItem();
                    item.setId(rs.getInt("id"));
                    item.setInventoryAuditId(rs.getInt("auditid"));
                    item.setProductId(rs.getInt("productid"));
                    item.setSystemQuantity(rs.getInt("systemquantity"));
                    item.setPhysicalQuantity(rs.getInt("physicalquantity"));
                    item.setReason(rs.getString("reasons"));
                    item.setProductName(rs.getString("productname"));
                    item.setProductSku(rs.getString("productsku"));
                    item.setCategoryName(rs.getString("categoryname"));

                    InventoryAuditItemSerialDAO serialDAO = new InventoryAuditItemSerialDAO();
                    item.setSerials(serialDAO.getSerialsByAuditItemId(item.getId()));

                    list.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int insertInventoryAudit(InventoryAudit audit) {
        String sql = "INSERT INTO inventory_audit (createdby, status, createdat, updatedat) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, audit.getUserId());
            ps.setString(2, audit.getStatus().name());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public boolean insertInventoryAuditItem(InventoryAuditItem item) {
        String sql = "INSERT INTO inventory_audit_items (auditid, productid, systemquantity, physicalquantity, reasons) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getInventoryAuditId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getSystemQuantity());
            ps.setInt(4, item.getPhysicalQuantity());
            ps.setString(5, item.getReason());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateInventoryAuditStatus(int id, InventoryAuditStatus status) {
        String sql = "UPDATE inventory_audit SET status = ?, updatedat = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateInventoryAuditStatus(int id, InventoryAuditStatus status, int processedBy) {
        String sql = "UPDATE inventory_audit SET status = ?, processedby = ?, updatedat = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, processedBy);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteInventoryAuditItemsByAuditId(int auditId) {
        String sql = "DELETE FROM inventory_audit_items WHERE auditid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, auditId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteInventoryAuditAndItems(int auditId) {
        try (Connection conn = DBContext.getConnection()) {
            String getProductItemsToDelete = "SELECT s.product_item_id FROM inventory_audit_item_serials s " +
                    "JOIN inventory_audit_items i ON s.audit_item_id = i.id " +
                    "WHERE i.auditid = ? AND s.type = 'ADD'";
            List<Integer> idsToDelete = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(getProductItemsToDelete)) {
                ps.setInt(1, auditId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        idsToDelete.add(rs.getInt(1));
                    }
                }
            }

            String deleteItemsSql = "DELETE FROM inventory_audit_items WHERE auditid = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteItemsSql)) {
                ps.setInt(1, auditId);
                ps.executeUpdate();
            }

            String deleteProductItems = "DELETE FROM product_items WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteProductItems)) {
                for (int pid : idsToDelete) {
                    ps.setInt(1, pid);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String deleteAuditSql = "DELETE FROM inventory_audit WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteAuditSql)) {
                ps.setInt(1, auditId);
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean submitAuditForReview(int auditId, List<InventoryAuditItem> items) {
        try (Connection conn = DBContext.getConnection()) {

            String updateAuditSql = "UPDATE inventory_audit SET status = ?, updatedat = CURRENT_TIMESTAMP WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateAuditSql)) {
                ps.setString(1, InventoryAuditStatus.PENDING.name());
                ps.setInt(2, auditId);
                ps.executeUpdate();
            }

            String updateItemSql = "UPDATE inventory_audit_items SET physicalquantity = ?, reasons = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateItemSql)) {
                for (InventoryAuditItem item : items) {
                    ps.setInt(1, item.getPhysicalQuantity());
                    ps.setString(2, item.getReason());
                    ps.setInt(3, item.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateAuditItems(List<InventoryAuditItem> items) {
        try (Connection conn = DBContext.getConnection()) {
            String updateItemSql = "UPDATE inventory_audit_items SET physicalquantity = ?, reasons = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateItemSql)) {
                for (InventoryAuditItem item : items) {
                    ps.setInt(1, item.getPhysicalQuantity());
                    ps.setString(2, item.getReason());
                    ps.setInt(3, item.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean approveInventoryAudit(int auditId, List<InventoryAuditItem> items, int processedBy) {
        try (Connection conn = DBContext.getConnection()) {
            String updateAuditSql = "UPDATE inventory_audit SET status = ?, processedby = ?, updatedat = CURRENT_TIMESTAMP WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateAuditSql)) {
                ps.setString(1, InventoryAuditStatus.COMPLETED.name());
                ps.setInt(2, processedBy);
                ps.setInt(3, auditId);
                ps.executeUpdate();
            }

            String updateProductSql = "UPDATE inventory SET quantity = ? WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateProductSql)) {
                for (InventoryAuditItem item : items) {
                    ps.setInt(1, item.getPhysicalQuantity());
                    ps.setInt(2, item.getProductId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String insertStockMovementSql = "INSERT INTO stock_movement (productid, quantity, type, reference_type, reference_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertStockMovementSql)) {
                for (InventoryAuditItem item : items) {
                    int diff = item.getPhysicalQuantity() - item.getSystemQuantity();
                    if (diff != 0) {
                        ps.setInt(1, item.getProductId());
                        ps.setInt(2, Math.abs(diff));
                        ps.setString(3, diff > 0 ? "INCREASED" : "DECREASED");
                        ps.setString(4, "INVENTORY_AUDIT");
                        ps.setInt(5, auditId);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            for (InventoryAuditItem item : items) {
                if (item.getPhysicalQuantity() != item.getSystemQuantity()) {
                    String getSerialsSql = "SELECT * FROM inventory_audit_item_serials WHERE audit_item_id = ?";
                    List<InventoryAuditItemSerial> serials = new ArrayList<>();
                    try (PreparedStatement ps = conn.prepareStatement(getSerialsSql)) {
                        ps.setInt(1, item.getId());
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                InventoryAuditItemSerial s = new InventoryAuditItemSerial();
                                s.setProductItemId(rs.getInt("product_item_id"));
                                s.setType(rs.getString("type"));
                                serials.add(s);
                            }
                        }
                    }

                    for (InventoryAuditItemSerial s : serials) {
                        if ("ADD".equals(s.getType())) {
                            String updateSerialSql = "UPDATE product_items SET status = 'AVAILABLE' WHERE id = ?";
                            try (PreparedStatement ps = conn.prepareStatement(updateSerialSql)) {
                                ps.setInt(1, s.getProductItemId());
                                ps.executeUpdate();
                            }
                        } else if ("DELETE".equals(s.getType())) {
                            String updateSerialSql = "UPDATE product_items SET status = 'UNAVAILABLE' WHERE id = ?";
                            try (PreparedStatement ps = conn.prepareStatement(updateSerialSql)) {
                                ps.setInt(1, s.getProductItemId());
                                ps.executeUpdate();
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean refreshSystemQuantities(int auditId) {
        String sql = "UPDATE inventory_audit_items iai " +
                "JOIN inventory i ON iai.productid = i.product_id " +
                "SET iai.systemquantity = i.quantity " +
                "WHERE iai.auditid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, auditId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasActiveAudit() {
        String sql = "SELECT COUNT(*) FROM inventory_audit WHERE status IN (?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, InventoryAuditStatus.SUBMITTED.name());
            ps.setString(2, InventoryAuditStatus.PENDING.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
