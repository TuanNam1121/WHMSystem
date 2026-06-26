package com.swp.whmsystem.model;

public class InventorySummary {
    private String sku;
    private String productName;
    private String unit;
    private int openingStock;
    private int importStock;
    private int exportStock;
    private int closingStock;

    public InventorySummary() {
    }

    public InventorySummary(String sku, String productName, String unit, int openingStock, int importStock, int exportStock, int closingStock) {
        this.sku = sku;
        this.productName = productName;
        this.unit = unit;
        this.openingStock = openingStock;
        this.importStock = importStock;
        this.exportStock = exportStock;
        this.closingStock = closingStock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(int openingStock) {
        this.openingStock = openingStock;
    }

    public int getImportStock() {
        return importStock;
    }

    public void setImportStock(int importStock) {
        this.importStock = importStock;
    }

    public int getExportStock() {
        return exportStock;
    }

    public void setExportStock(int exportStock) {
        this.exportStock = exportStock;
    }

    public int getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(int closingStock) {
        this.closingStock = closingStock;
    }
}
