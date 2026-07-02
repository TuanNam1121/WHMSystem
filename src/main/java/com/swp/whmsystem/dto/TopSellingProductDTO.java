package com.swp.whmsystem.dto;

import java.math.BigDecimal;

public class TopSellingProductDTO {
    private int productId;
    private String productName;
    private String imgUrl;
    private String brandName;
    private String categoryName;
    private int totalItemSold;
    private BigDecimal totalPrice;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalItemSold() {
        return totalItemSold;
    }

    public void setTotalItemSold(int totalItemSold) {
        this.totalItemSold = totalItemSold;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
