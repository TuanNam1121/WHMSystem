package com.swp.whmsystem.model;

public class PurchaseItem {
    private int id;
    private int purchaseRequestId;
    private int productId;
    private int requiredQty;

    public PurchaseItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public void setPurchaseRequestId(int purchaseRequestId) {
        this.purchaseRequestId = purchaseRequestId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRequiredQuantity() {
        return requiredQty;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQty = requiredQuantity;
    }
}
