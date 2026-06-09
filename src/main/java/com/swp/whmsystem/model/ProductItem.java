package com.swp.whmsystem.model;

import java.sql.Timestamp;

public class ProductItem {
    private int id;
    private String serial;
    private int importPrice;
    private Timestamp importAt;
    private boolean isActive;
    private int goodReceiptItemId;
    private int productId;

    public ProductItem() {
    }

    public ProductItem(int goodReceiptItemId, int id, Timestamp importAt, int importPrice, boolean isActive, int productId, String serial) {
        this.goodReceiptItemId = goodReceiptItemId;
        this.id = id;
        this.importAt = importAt;
        this.importPrice = importPrice;
        this.isActive = isActive;
        this.productId = productId;
        this.serial = serial;
    }

    public int getGoodReceiptItemId() {
        return goodReceiptItemId;
    }

    public void setGoodReceiptItemId(int goodReceiptItemId) {
        this.goodReceiptItemId = goodReceiptItemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
