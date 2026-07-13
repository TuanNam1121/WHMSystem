/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.report;

import com.swp.whmsystem.dal.DailyTransactionDAO;
import com.swp.whmsystem.model.DailyTransaction;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Date;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ExportDailyTransaction", urlPatterns = {"/ExportDailyTransaction"})
public class ExportDailyTransaction extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //date=${param.date}&keyword=${param.keyword}&sortBy=${param.sortBy}&sortDir=${param.sortDir}"
        String date = request.getParameter("date");
        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        String sortDir = request.getParameter("sortDir");

        try {
            date = LocalDate.parse(date).toString();
        } catch (Exception e) {
            date = LocalDate.now().toString();
        }
        DailyTransactionDAO dao = new DailyTransactionDAO();
        List<DailyTransaction> list = dao.getDailyTransactionToExport(date, keyword, sortBy, sortDir);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Inventory_Summary_Report.xlsx");

        try {
            OutputStream out;
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(date);

                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);

                CellStyle title = workbook.createCellStyle();
                title.setAlignment(HorizontalAlignment.CENTER);
                title.setVerticalAlignment(VerticalAlignment.CENTER.CENTER);

                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short) 11);
                headerStyle.setFont(headerFont);

                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setBorderTop(BorderStyle.THIN);
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);

                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("BÁO CÁO CHI TIẾT XUẤT-NHẬP VẬT TƯ THEO NGÀY");
                sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
                cell.setCellStyle(title);
                
                row = sheet.createRow(4);
                cell = row.createCell(0);
                cell.setCellValue("Ngày");
                cell = row.createCell(1);
                cell.setCellValue(date);
                row = sheet.createRow(5);
                String[] headers = {"TT", "SKU", "Product Name", "Unit", "Date", "Import Quantity", "Export Quantity"};
                for (int i = 0; i < headers.length; ++i) {
                    cell = row.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(headers[i]);
                }
                int totalImport = 0, totalExport = 0;
                for (int i = 0; i < list.size(); ++i) {
                    DailyTransaction data = list.get(i);
                    totalImport += data.getTotalImport();
                    totalExport += data.getTotalExport();
                    row = sheet.createRow(6 + i);
                    Cell dataCell = row.createCell(0);
                    dataCell.setCellStyle(dataStyle);

                    dataCell.setCellValue(i + 1);
                    dataCell = row.createCell(1);
                    dataCell.setCellStyle(dataStyle);
                    dataCell.setCellValue(data.getSku());
                    dataCell = row.createCell(2);
                    dataCell.setCellStyle(dataStyle);
                    dataCell.setCellValue(data.getProductName());
                    dataCell = row.createCell(3);
                    dataCell.setCellStyle(dataStyle);
                    dataCell.setCellValue(data.getUnit());
                    dataCell = row.createCell(4);
                    dataCell.setCellStyle(dataStyle);
                    dataCell.setCellValue(data.getDate().toString());
                    dataCell = row.createCell(5);
                    dataCell.setCellStyle(dataStyle);
                    dataCell.setCellValue(data.getTotalImport());
                    dataCell = row.createCell(6);
                    dataCell.setCellStyle(dataStyle);
                    dataCell.setCellValue(data.getTotalExport());
                }
                row = sheet.createRow(6 + list.size());
                Cell dataCell = row.createCell(0);
                dataCell.setCellStyle(dataStyle);
                dataCell.setCellValue("Total");
                dataCell = row.createCell(1);
                dataCell.setCellStyle(dataStyle);
                dataCell = row.createCell(2);
                dataCell.setCellStyle(dataStyle);
                dataCell = row.createCell(3);
                dataCell.setCellStyle(dataStyle);
                dataCell = row.createCell(4);
                dataCell.setCellStyle(dataStyle);
                dataCell = row.createCell(5);
                dataCell.setCellStyle(dataStyle);
                dataCell.setCellValue(totalImport);
                dataCell = row.createCell(6);
                dataCell.setCellStyle(dataStyle);
                dataCell.setCellValue(totalExport);

                for (int i = 0; i < 7; ++i) {
                    sheet.autoSizeColumn(i);
                }

                out = response.getOutputStream();
                workbook.write(out);
            }
            out.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
