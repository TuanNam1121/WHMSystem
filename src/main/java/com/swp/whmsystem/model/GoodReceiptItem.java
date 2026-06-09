package com.swp.whmsystem.model;

public class GoodReceiptItem {
    private int id;
    private int goodReceiptId;
    private int productId;
    private int actualQuantity;

    public GoodReceiptItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodReceiptId() {
        return goodReceiptId;
    }

    public void setGoodReceiptId(int goodReceiptId) {
        this.goodReceiptId = goodReceiptId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    @Override
    public String toString() {
        return "GoodReceiptItem{" +
                "actualQuantity=" + actualQuantity +
                ", id=" + id +
                ", goodReceiptId=" + goodReceiptId +
                ", productId=" + productId +
                '}';
    }
}
