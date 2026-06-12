package com.swp.whmsystem.dto;

public class ExportDetailItemDTO {
    private String name;
    private String imgUrl;
    private String sku;
    private String serial;
    private double price;

    public ExportDetailItemDTO() {
    }

    public ExportDetailItemDTO(String name, String imgUrl, String sku, String serial, double price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.sku = sku;
        this.serial = serial;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ExportDetailItemDTO{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", sku='" + sku + '\'' +
                ", serial='" + serial + '\'' +
                ", price=" + price +
                '}';
    }
}