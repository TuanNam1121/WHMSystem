/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.model;

/**
 *
 * @author Admin
 */
public class Request {
    private int requestId;
    private int userId;
    private String message;
    private String status;
    private String createdAt;

    public Request() {
    
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Request{" + "requestId=" + requestId + ", userId=" + userId + ", message=" + message + ", status=" + status + ", createdAt=" + createdAt + '}';
    }
    
    
}
