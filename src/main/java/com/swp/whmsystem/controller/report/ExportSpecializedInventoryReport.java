package com.swp.whmsystem.controller.report;

import com.swp.whmsystem.dal.InventorySummaryDAO;
import com.swp.whmsystem.enums.SpecializedReportType;
import com.swp.whmsystem.model.InventorySummary;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.DateUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "ExportSpecializedInventoryReport", urlPatterns = {
        "/ExportImportReport", "/ExportExportReport", "/ExportStockReport"
})
public class ExportSpecializedInventoryReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SpecializedReportType reportType = SpecializedReportType.fromServletPath(request.getServletPath());
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_REPORT,
                "You don't have permission to export reports!")) {
            return;
        }

        String keyword = request.getParameter("keyword");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        if (!isValidDateRange(fromDate, toDate)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report date range");
            return;
        }

        InventorySummaryDAO dao = new InventorySummaryDAO();
        List<InventorySummary> reportList = reportType.isMovementReport()
                ? dao.forMovementReport(reportType.getMovementType(), reportType.getReferenceType(),
                        fromDate, toDate, keyword, 1, Integer.MAX_VALUE, "desc")
                : dao.forStockReport(fromDate, toDate, keyword, 1, Integer.MAX_VALUE,
                        "closingStock", "desc");

        String fileName = reportType.getTitle().replace(' ', '_') + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (Workbook workbook = new XSSFWorkbook()) {
            writeWorkbook(workbook, reportType, reportList, fromDate, toDate);
            workbook.write(response.getOutputStream());
        }
    }

    private void writeWorkbook(Workbook workbook, SpecializedReportType reportType,
            List<InventorySummary> reportList, String fromDate, String toDate) {
        Sheet sheet = workbook.createSheet(reportType.getTitle());
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle subtitleStyle = createSubtitleStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createBorderedStyle(workbook);

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(reportType.getTitle());
        titleCell.setCellStyle(titleStyle);
        int lastColumn = reportType == SpecializedReportType.STOCK ? 6 : 5;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, lastColumn));

        Row periodRow = sheet.createRow(1);
        Cell periodCell = periodRow.createCell(0);
        periodCell.setCellValue(buildPeriodText(fromDate, toDate));
        periodCell.setCellStyle(subtitleStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, lastColumn));

        String[] headers = reportType == SpecializedReportType.STOCK
                ? new String[] {"No.", "SKU", "Product Name", "Category", "Unit",
                        "Opening Stock", "Closing Stock"}
                : new String[] {"No.", "SKU", "Product Name", "Category", "Unit",
                        reportType.getQuantityLabel()};
        Row headerRow = sheet.createRow(3);
        for (int column = 0; column < headers.length; column++) {
            Cell cell = headerRow.createCell(column);
            cell.setCellValue(headers[column]);
            cell.setCellStyle(headerStyle);
        }

        int rowNumber = 4;
        long total = 0L;
        long totalOpening = 0L;
        for (int index = 0; index < reportList.size(); index++) {
            InventorySummary item = reportList.get(index);
            int quantity = getQuantity(reportType, item);
            total += quantity;
            totalOpening += item.getOpeningStock();
            Row row = sheet.createRow(rowNumber++);
            setCell(row, 0, index + 1, dataStyle);
            setCell(row, 1, item.getSku(), dataStyle);
            setCell(row, 2, item.getProductName(), dataStyle);
            setCell(row, 3, item.getCategory(), dataStyle);
            setCell(row, 4, item.getUnit(), dataStyle);
            if (reportType == SpecializedReportType.STOCK) {
                setCell(row, 5, item.getOpeningStock(), dataStyle);
                setCell(row, 6, item.getClosingStock(), dataStyle);
            } else {
                setCell(row, 5, quantity, dataStyle);
            }
        }

        Row totalRow = sheet.createRow(rowNumber);
        Cell totalLabel = totalRow.createCell(0);
        totalLabel.setCellValue("Total");
        totalLabel.setCellStyle(headerStyle);
        for (int column = 1; column < 5; column++) {
            totalRow.createCell(column).setCellStyle(headerStyle);
        }
        if (reportType == SpecializedReportType.STOCK) {
            Cell totalOpeningCell = totalRow.createCell(5);
            totalOpeningCell.setCellValue(totalOpening);
            totalOpeningCell.setCellStyle(headerStyle);
            Cell totalClosingCell = totalRow.createCell(6);
            totalClosingCell.setCellValue(total);
            totalClosingCell.setCellStyle(headerStyle);
        } else {
            Cell totalCell = totalRow.createCell(5);
            totalCell.setCellValue(total);
            totalCell.setCellStyle(headerStyle);
        }

        for (int column = 0; column < headers.length; column++) {
            sheet.autoSizeColumn(column);
        }
    }

    private int getQuantity(SpecializedReportType reportType, InventorySummary item) {
        return switch (reportType) {
            case IMPORT -> item.getImportStock();
            case EXPORT -> item.getExportStock();
            case STOCK -> item.getClosingStock();
        };
    }

    private boolean isValidDateRange(String fromDate, String toDate) {
        LocalDate from = parseOptionalDate(fromDate);
        LocalDate to = parseOptionalDate(toDate);
        if ((hasText(fromDate) && from == null) || (hasText(toDate) && to == null)) {
            return false;
        }
        return from == null || to == null || !from.isAfter(to);
    }

    private LocalDate parseOptionalDate(String value) {
        return hasText(value) ? DateUtils.parseDate(value) : null;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String buildPeriodText(String fromDate, String toDate) {
        if (hasText(fromDate) && hasText(toDate)) {
            return "Time Period: " + fromDate + " to " + toDate;
        }
        if (hasText(fromDate)) {
            return "Time Period: From " + fromDate;
        }
        if (hasText(toDate)) {
            return "Time Period: Until " + toDate;
        }
        return "Time Period: All time";
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createSubtitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setItalic(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = createBorderedStyle(workbook);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private CellStyle createBorderedStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private void setCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value == null ? "" : value);
        cell.setCellStyle(style);
    }

    private void setCell(Row row, int column, double value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
