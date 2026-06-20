package com.swp.whmsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryAuditItem {
    private int id;
    private int inventoryAuditId;
    private int productId;
    private int systemQuantity;
    private int physicalQuantity;
    private String reason;
    private String productName;
    private String productSku;
    private String categoryName;
    private List<InventoryAuditItemSerial> serials;
}
