package com.swp.whmsystem.dto;

import java.math.BigDecimal;

public class InventoryItemDTO {
    private int productId;
    private String productName;
    private String sku;
    private String imgUrl;
    private String unitName;
    private int quantity;
    private BigDecimal totalValue;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getStockStatus() {
        if (quantity == 0) {
            return "Out of stock";
        }
        if (quantity <= 10) {
            return "Low stock";
        }
        return "In stock";
    }

    public String getStatusClass() {
        if (quantity <= 5) {
            return "bg-lightred";
        }
        if (quantity <= 10) {
            return "bg-lightyellow";
        }
        return "bg-lightgreen";
    }

}
