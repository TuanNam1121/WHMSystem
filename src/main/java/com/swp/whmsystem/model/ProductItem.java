package com.swp.whmsystem.model;

import java.sql.Timestamp;

public class ProductItem {
    private int id;
    private String serial;
    private Timestamp importAt;
    private int importPrice;
    private int exportPrice;
    private boolean isActive;
    private int goodReceiptItemId;
    private int productId;
    private String status;

    public ProductItem() {
    }

    public ProductItem(int id, String serial, Timestamp importAt, int importPrice, int exportPrice, boolean isActive, int goodReceiptItemId, int productId, String status) {
        this.id = id;
        this.serial = serial;
        this.importAt = importAt;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.isActive = isActive;
        this.goodReceiptItemId = goodReceiptItemId;
        this.productId = productId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Timestamp getImportAt() {
        return importAt;
    }

    public void setImportAt(Timestamp importAt) {
        this.importAt = importAt;
    }

    public int getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(int importPrice) {
        this.importPrice = importPrice;
    }

    public int getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(int exportPrice) {
        this.exportPrice = exportPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getGoodReceiptItemId() {
        return goodReceiptItemId;
    }

    public void setGoodReceiptItemId(int goodReceiptItemId) {
        this.goodReceiptItemId = goodReceiptItemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                ", importAt=" + importAt +
                ", importPrice=" + importPrice +
                ", exportPrice=" + exportPrice +
                ", isActive=" + isActive +
                ", goodReceiptItemId=" + goodReceiptItemId +
                ", productId=" + productId +
                ", status='" + status + '\'' +
                '}';
    }
}
