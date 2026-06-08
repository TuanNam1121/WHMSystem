/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.swp.whmsystem.model;
import java.sql.Timestamp;

public class Order {
    private int id;
    private enum status{NEW,COMPLETED};
    private double totalPrice;
    private String note;
    private Timestamp orderDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp completedAt;
    private int createdBy;
    private int processdBy;
    private int customerId;

    public Order() {
    }

    public Order(int id, double totalPrice, String note, Timestamp orderDate, Timestamp createdAt, Timestamp updatedAt, Timestamp completedAt, int createdBy, int processdBy, int customerId) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderDate = orderDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.createdBy = createdBy;
        this.processdBy = processdBy;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getProcessdBy() {
        return processdBy;
    }

    public void setProcessdBy(int processdBy) {
        this.processdBy = processdBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", totalPrice=" + totalPrice + ", note=" + note + ", orderDate=" + orderDate + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", completedAt=" + completedAt + ", createdBy=" + createdBy + ", processdBy=" + processdBy + ", customerId=" + customerId + '}';
    }
    
    
    
}
