package com.swp.whmsystem.dto;

import java.util.UUID;

public class ExportItemDTO {
    private String tempId;
    private String sku;
    private String imgUrl;
    private String name;
    private String serial;
    private int qty;
    private double price;
    private int stock;

    public ExportItemDTO() {
        this.tempId = UUID.randomUUID().toString();
        this.qty = 1;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getTotalCost() {
        return this.qty * this.price;
    }

    @Override
    public String toString() {
        return "ExportItemDTO{" +
                "tempId='" + tempId + '\'' +
                ", sku='" + sku + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}