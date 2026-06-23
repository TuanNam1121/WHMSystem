package com.swp.whmsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryAuditItemSerial {
    private int id;
    private int auditItemId;
    private int productItemId;
    private String type;
    private String serialNumber;
}
