package com.swp.whmsystem.model;

public class RequestItemDTO {
    private String id;
    private String name;
    private String sku;
    private String category;
    private int stock;
    private int reqQty;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getReqQty() {
        return reqQty;
    }

    public void setReqQty(int reqQty) {
        this.reqQty = reqQty;
    }
}
