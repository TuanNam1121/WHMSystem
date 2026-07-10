package com.swp.whmsystem.dto;

import java.sql.Timestamp;

public class ExportReceiptInfoDTO {
    private int receiptId;
    private int orderId;
    private Timestamp orderCreatedAt;
    private String saleCreatedBy;
    private String saleProcessedBy;

    public ExportReceiptInfoDTO() {
    }

    public ExportReceiptInfoDTO(int receiptId, int orderId, Timestamp orderCreatedAt,
                                String saleCreatedBy, String saleProcessedBy) {
        this.receiptId = receiptId;
        this.orderId = orderId;
        this.orderCreatedAt = orderCreatedAt;
        this.saleCreatedBy = saleCreatedBy;
        this.saleProcessedBy = saleProcessedBy;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(Timestamp orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public String getSaleCreatedBy() {
        return saleCreatedBy;
    }

    public void setSaleCreatedBy(String saleCreatedBy) {
        this.saleCreatedBy = saleCreatedBy;
    }

    public String getSaleProcessedBy() {
        return saleProcessedBy;
    }

    public void setSaleProcessedBy(String saleProcessedBy) {
        this.saleProcessedBy = saleProcessedBy;
    }
}
