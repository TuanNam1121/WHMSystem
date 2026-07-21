package com.swp.whmsystem.enums;

public enum SpecializedReportType {
    IMPORT("import", "Import Report", "View imported quantities by product", "Import Qty",
            "importReport", "ExportImportReport", "INCREASED", "IMPORT"),
    EXPORT("export", "Export Report", "View exported quantities by product", "Export Qty",
            "exportReport", "ExportExportReport", "DECREASED", "EXPORT"),
    STOCK("stock", "Stock Report", "View opening and closing stock by date", "Stock Qty",
            "stockReport", "ExportStockReport", null, null);

    private final String code;
    private final String title;
    private final String subtitle;
    private final String quantityLabel;
    private final String reportPath;
    private final String excelPath;
    private final String movementType;
    private final String referenceType;

    SpecializedReportType(String code, String title, String subtitle, String quantityLabel,
            String reportPath, String excelPath, String movementType, String referenceType) {
        this.code = code;
        this.title = title;
        this.subtitle = subtitle;
        this.quantityLabel = quantityLabel;
        this.reportPath = reportPath;
        this.excelPath = excelPath;
        this.movementType = movementType;
        this.referenceType = referenceType;
    }

    public static SpecializedReportType fromServletPath(String servletPath) {
        String normalizedPath = servletPath == null ? "" : servletPath.replaceFirst("^/", "");
        for (SpecializedReportType type : values()) {
            if (type.reportPath.equals(normalizedPath) || type.excelPath.equals(normalizedPath)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported report path: " + servletPath);
    }

    public boolean isMovementReport() {
        return this != STOCK;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getQuantityLabel() {
        return quantityLabel;
    }

    public String getReportPath() {
        return reportPath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public String getMovementType() {
        return movementType;
    }

    public String getReferenceType() {
        return referenceType;
    }
}
