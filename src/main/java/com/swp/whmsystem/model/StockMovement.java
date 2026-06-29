package com.swp.whmsystem.model;

import java.sql.Timestamp;

public class StockMovement {
    private int id;
    private int productId;
    private int quantity;
    private String type;
    private String reference_type;
    private int reference_id;
    private Timestamp createdAt;

    public StockMovement() {
    }

    public StockMovement(int id, int productId, int quantity, String type, String reference_type, Timestamp createdAt) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.type = type;
        this.reference_type = reference_type;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference_type() {
        return reference_type;
    }

    public void setReference_type(String reference_type) {
        this.reference_type = reference_type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getReference_id() {
        return reference_id;
    }

    public void setReference_id(int reference_id) {
        this.reference_id = reference_id;
    }
}
