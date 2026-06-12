package com.swp.whmsystem.dto;

public class OrderItemDetailDTO {
    private String name;
    private String imgUrl;
    private String sku;
    private int quantity;
    private double price;

    public OrderItemDetailDTO() {
    }

    public OrderItemDetailDTO(String name, String imgUrl, String sku, int quantity, double price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.sku = sku;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDetailDTO{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", sku='" + sku + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}