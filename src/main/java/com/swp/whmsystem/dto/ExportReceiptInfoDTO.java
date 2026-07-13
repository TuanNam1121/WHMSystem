package com.swp.whmsystem.dto;

import java.sql.Timestamp;

public class ExportReceiptInfoDTO {
    private int receiptId;
    private String receiptCode;
    private int orderId;
    private String orderCode;
    private Timestamp orderCreatedAt;
    private String saleCreatedBy;
    private String saleProcessedBy;

    public ExportReceiptInfoDTO() {
    }

    public ExportReceiptInfoDTO(int receiptId, String receiptCode, int orderId, String orderCode, Timestamp orderCreatedAt,
                                String saleCreatedBy, String saleProcessedBy) {
        this.receiptId = receiptId;
        this.receiptCode = receiptCode;
        this.orderId = orderId;
        this.orderCode = orderCode;
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

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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
