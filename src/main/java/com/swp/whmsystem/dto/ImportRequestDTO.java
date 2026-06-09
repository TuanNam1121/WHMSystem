/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class ImportRequestDTO {
    //GoodReceiptID | PurchaseRequestID | CreatedBy | Created At | Total Item | Action
    private int goodReceiptId;
    private int purchaseRequestId;
    private String createdBy;
    private Timestamp createdAt;
    private int totalItem;

    public ImportRequestDTO(int goodReceiptId, int purchaseRequestId, String createdBy, Timestamp createdAt, int totalItem) {
        this.goodReceiptId = goodReceiptId;
        this.purchaseRequestId = purchaseRequestId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.totalItem = totalItem;
    }

    public ImportRequestDTO() {
    }

    public int getGoodReceiptId() {
        return goodReceiptId;
    }

    public void setGoodReceiptId(int goodReceiptId) {
        this.goodReceiptId = goodReceiptId;
    }

    public int getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public void setPurchaseRequestId(int purchaseRequestId) {
        this.purchaseRequestId = purchaseRequestId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }
    
    
}
