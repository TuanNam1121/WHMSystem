package com.swp.whmsystem.controller.report;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.swp.whmsystem.dal.InventorySummaryDAO;
import com.swp.whmsystem.model.InventorySummary;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ExportInventorySummary", urlPatterns = {"/ExportInventorySummary"})
public class ExportInventorySummary extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        InventorySummaryDAO dao = new InventorySummaryDAO();
        List<InventorySummary> reportList = dao.forReport(fromDate, toDate, keyword, 1, Integer.MAX_VALUE, null, null);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Inventory_Summary_Report.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Inventory Summary");

            // Title Style
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            // Subtitle Style
            CellStyle subtitleStyle = workbook.createCellStyle();
            Font subtitleFont = workbook.createFont();
            subtitleFont.setItalic(true);
            subtitleFont.setFontHeightInPoints((short) 12);
            subtitleStyle.setFont(subtitleFont);
            subtitleStyle.setAlignment(HorizontalAlignment.CENTER);

            // Row 0: Title
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Inventory Summary Report");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            // Row 1: Time period
            Row periodRow = sheet.createRow(1);
            Cell periodCell = periodRow.createCell(0);
            String periodText = "Time Period: ";
            if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                periodText += fromDate + " to " + toDate;
            } else if (fromDate != null && !fromDate.isEmpty()) {
                periodText += "From " + fromDate;
            } else if (toDate != null && !toDate.isEmpty()) {
                periodText += "Until " + toDate;
            } else {
                periodText += "All time";
            }
            periodCell.setCellValue(periodText);
            periodCell.setCellStyle(subtitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            headerStyle.setFont(headerFont);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            String[] headers = {"SKU", "Product Name", "Category", "Unit", "Opening Stock", "Import Qty", "Export Qty", "Closing Stock"};
            Row headerRow = sheet.createRow(3);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 4;
            int totalOpening = 0, totalImport = 0, totalExport = 0, totalClosing = 0;

            for (InventorySummary item : reportList) {
                Row row = sheet.createRow(rowNum++);

                Cell cellSku = row.createCell(0);
                cellSku.setCellValue(item.getSku());
                cellSku.setCellStyle(dataStyle);

                Cell cellName = row.createCell(1);
                cellName.setCellValue(item.getProductName());
                cellName.setCellStyle(dataStyle);

                Cell cellCat = row.createCell(2);
                cellCat.setCellValue(item.getCategory());
                cellCat.setCellStyle(dataStyle);

                Cell cellUnit = row.createCell(3);
                cellUnit.setCellValue(item.getUnit());
                cellUnit.setCellStyle(dataStyle);

                Cell cellOpen = row.createCell(4);
                cellOpen.setCellValue(item.getOpeningStock());
                cellOpen.setCellStyle(dataStyle);

                Cell cellImp = row.createCell(5);
                cellImp.setCellValue(item.getImportStock());
                cellImp.setCellStyle(dataStyle);

                Cell cellExp = row.createCell(6);
                cellExp.setCellValue(item.getExportStock());
                cellExp.setCellStyle(dataStyle);

                Cell cellClose = row.createCell(7);
                cellClose.setCellValue(item.getClosingStock());
                cellClose.setCellStyle(dataStyle);

                totalOpening += item.getOpeningStock();
                totalImport += item.getImportStock();
                totalExport += item.getExportStock();
                totalClosing += item.getClosingStock();
            }

            Row totalRow = sheet.createRow(rowNum);
            CellStyle totalStyle = workbook.createCellStyle();
            totalStyle.cloneStyleFrom(headerStyle); // Dùng lại style in đậm có viền

            Cell cellLabel = totalRow.createCell(0);
            cellLabel.setCellValue("Total");
            cellLabel.setCellStyle(totalStyle);

            for (int i = 1; i <= 3; i++) {
                Cell blankCell = totalRow.createCell(i);
                blankCell.setCellStyle(totalStyle);
            }

            Cell cellTotOpen = totalRow.createCell(4);
            cellTotOpen.setCellValue(totalOpening);
            cellTotOpen.setCellStyle(totalStyle);

            Cell cellTotImp = totalRow.createCell(5);
            cellTotImp.setCellValue(totalImport);
            cellTotImp.setCellStyle(totalStyle);

            Cell cellTotExp = totalRow.createCell(6);
            cellTotExp.setCellValue(totalExport);
            cellTotExp.setCellStyle(totalStyle);

            Cell cellTotClose = totalRow.createCell(7);
            cellTotClose.setCellValue(totalClosing);
            cellTotClose.setCellStyle(totalStyle);

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
