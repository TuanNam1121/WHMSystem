package com.swp.whmsystem.dal;

import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.model.InventoryAuditItem;

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
        inventoryAudit.setUserId(rs.getInt("userid"));
        inventoryAudit.setStatus(InventoryAuditStatus.valueOf(rs.getString("status")));
        inventoryAudit.setCreatedAt(rs.getObject("createdat", LocalDateTime.class));
        inventoryAudit.setUpdatedAt(rs.getObject("updatedat", LocalDateTime.class));
        inventoryAudit.setUserFullName(rs.getString("fullname"));
        return inventoryAudit;
    }

    public List<InventoryAudit> getAllInventoryAudit() {
        String sql = """
                SELECT * FROM inventory_audit join users on inventory_audit.createdby = users.userid
                ORDER BY inventory_audit.createdat DESC
                """;
        List<InventoryAudit> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapInventoryAudit(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<InventoryAudit> getInventoryAuditsByFilter(String keyword, int offset, int limit) {
        String sql = "SELECT * FROM inventory_audit JOIN users ON inventory_audit.createdby = users.userid WHERE 1=1";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND users.fullname LIKE ?";
        }
        sql += " ORDER BY inventory_audit.createdat DESC LIMIT ? OFFSET ?";

        List<InventoryAudit> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }
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

    public int countInventoryAuditsByFilter(String keyword) {
        String sql = "SELECT COUNT(*) FROM inventory_audit JOIN users ON inventory_audit.createdby = users.userid WHERE 1=1";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND users.fullname LIKE ?";
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }

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
                SELECT * FROM inventory_audit join users on inventory_audit.createdby = users.userid
                WHERE inventory_audit.id = ?
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

            String deleteItemsSql = "DELETE FROM inventory_audit_items WHERE auditid = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteItemsSql)) {
                ps.setInt(1, auditId);
                ps.executeUpdate();
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

    public boolean approveInventoryAudit(int auditId, List<InventoryAuditItem> items) {
        try (Connection conn = DBContext.getConnection()) {
            String updateAuditSql = "UPDATE inventory_audit SET status = ?, updatedat = CURRENT_TIMESTAMP WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateAuditSql)) {
                ps.setString(1, InventoryAuditStatus.COMPLETED.name());
                ps.setInt(2, auditId);
                ps.executeUpdate();
            }

            String updateProductSql = "UPDATE products SET total_quantity = ? WHERE productid = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateProductSql)) {
                for (InventoryAuditItem item : items) {
                    ps.setInt(1, item.getPhysicalQuantity());
                    ps.setInt(2, item.getProductId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            for (InventoryAuditItem item : items) {
                if (item.getPhysicalQuantity() != item.getSystemQuantity()) {
                    String getSerialsSql = "SELECT * FROM inventory_audit_item_serials WHERE audit_item_id = ?";
                    List<com.swp.whmsystem.model.InventoryAuditItemSerial> serials = new ArrayList<>();
                    try (PreparedStatement ps = conn.prepareStatement(getSerialsSql)) {
                        ps.setInt(1, item.getId());
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                com.swp.whmsystem.model.InventoryAuditItemSerial s = new com.swp.whmsystem.model.InventoryAuditItemSerial();
                                s.setSerial(rs.getString("serial"));
                                s.setType(rs.getString("type"));
                                serials.add(s);
                            }
                        }
                    }

                    for (com.swp.whmsystem.model.InventoryAuditItemSerial s : serials) {
                        if ("ADD".equals(s.getType())) {
                            String insertSerialSql = "INSERT INTO product_items (serial, product_id, imported_price, status) VALUES (?, ?, ?, 'AVAILABLE')";
                            try (PreparedStatement ps = conn.prepareStatement(insertSerialSql)) {
                                ps.setString(1, s.getSerial());
                                ps.setInt(2, item.getProductId());
                                ps.setInt(3, 0);
                                ps.executeUpdate();
                            }
                        } else if ("DELETE".equals(s.getType())) {
                            String updateSerialSql = "UPDATE product_items SET status = 'UNAVAILABLE' WHERE serial = ? AND product_id = ?";
                            try (PreparedStatement ps = conn.prepareStatement(updateSerialSql)) {
                                ps.setString(1, s.getSerial());
                                ps.setInt(2, item.getProductId());
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
        String sql = "UPDATE inventory_audit_items i " +
                "JOIN products p ON i.productid = p.productid " +
                "SET i.systemquantity = p.total_quantity " +
                "WHERE i.auditid = ?";
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
