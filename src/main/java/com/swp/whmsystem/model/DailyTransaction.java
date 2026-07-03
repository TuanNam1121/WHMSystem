/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class DailyTransaction {
    private int productId;
    private String productName;
    private String sku;
    private String unit;
    private Date date;
    private Long totalImport;
    private Long totalExport;

    public DailyTransaction() {
    }

    public DailyTransaction(int productId, String productName, String sku, String unit, Date date, Long totalImport, Long totalExport) {
        this.productId = productId;
        this.productName = productName;
        this.sku = sku;
        this.unit = unit;
        this.date = date;
        this.totalImport = totalImport;
        this.totalExport = totalExport;
    }

    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(Long totalImport) {
        this.totalImport = totalImport;
    }

    public Long getTotalExport() {
        return totalExport;
    }

    public void setTotalExport(Long totalExport) {
        this.totalExport = totalExport;
    }
    
    
    
}
