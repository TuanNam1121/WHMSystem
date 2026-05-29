/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.model;

/**
 *
 * @author Admin
 */
public class Product {
    private int productId;
    private String name;
    private String description;
    private String imgUrl;
    private int totalQuantity;
    private boolean isActive;
    private Ram ram;
    private Rom rom;
    private Chip chip;
    private Model model;
    private int categoryId;
    private int brandId;

    public Product() {
    }

    public Product(int productId, String name, String description, String imgUrl, int totalQuantity, boolean isActive, Ram ram, Rom rom, Chip chip, Model model, int categoryId, int brandId) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.totalQuantity = totalQuantity;
        this.isActive = isActive;
        this.ram = ram;
        this.rom = rom;
        this.chip = chip;
        this.model = model;
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Rom getRom() {
        return rom;
    }

    public void setRom(Rom rom) {
        this.rom = rom;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
    
   
    
}
