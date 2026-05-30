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
    private String sku;
    private String imgUrl;
    private int totalQuantity;
    private boolean isActive;
    private Ram ram;
    private Rom rom;
    private Unit unit;
    private Chip chip;
    private Model model;
    private Category category;
    private Brand brand;

    public Product() {
    }

    public Product(int productId, String name, String description, String sku, String imgUrl, int totalQuantity, boolean isActive, Ram ram, Rom rom, Unit unit, Chip chip, Model model, Category category, Brand brand) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.imgUrl = imgUrl;
        this.totalQuantity = totalQuantity;
        this.isActive = isActive;
        this.ram = ram;
        this.rom = rom;
        this.unit = unit;
        this.chip = chip;
        this.model = model;
        this.category = category;
        this.brand = brand;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
    
}
