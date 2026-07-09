package com.swp.whmsystem.model;

import com.swp.whmsystem.enums.InventoryAuditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryAudit {
    private int id;
    private int userId;
    private InventoryAuditStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User creator;
    private User processor;
    private List<InventoryAuditItem> inventoryAuditItems;
    private String codeId;

    public String getFormattedCreatedAt() {
        return createdAt != null ? createdAt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "";
    }

    public String getFormattedUpdatedAt() {
        return updatedAt != null ? updatedAt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "";
    }
}
