/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.report;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for the Import/Export Detail Report by Day page.
 *
 * Query parameters accepted (all optional):
 *   date     – DD-MM-YYYY  : the day to report on
 *   keyword  – String      : search by product name or SKU (LIKE %keyword%)
 *   sortBy   – String      : sku | name | importQty | exportQty
 *   sortDir  – String      : asc | desc
 *   page     – int         : pagination page number (default 1)
 *
 * Attributes set for the JSP:
 *   reportList        – List of daily import/export rows
 *   top5Import        – Top-5 products by import qty
 *   top5Export        – Top-5 products by export qty
 *   totalImportQty    – long
 *   totalExportQty    – long
 *   totalAdjustQty    – long
 *   totalImportValue  – BigDecimal
 *   totalExportValue  – BigDecimal
 *   page, pageSize    – pagination helpers
 *   focusTable        – boolean (scroll to table after submit)
 *
 * @author Admin
 */
@WebServlet(name = "ImportExportByDayReport", urlPatterns = {"/ImportExportByDayReport"})
public class ImportExportByDayReport extends HttpServlet {
    private static final int DEFAULT_PAGE_SIZE = 15;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ── 1. Read filter params ────────────────────────────────────────────
        String date    = request.getParameter("date");
        String keyword = request.getParameter("keyword");
        String sortBy  = request.getParameter("sortBy");
        String sortDir = request.getParameter("sortDir");

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) page = 1;
        } catch (NumberFormatException ignored) {
            page = 1;
        }

        //   ImportExportByDayDAO dao = new ImportExportByDayDAO();
        //   List<ImportExportByDayDTO> reportList =
        //       dao.getReport(date, keyword, sortBy, sortDir, page, DEFAULT_PAGE_SIZE);
        //   long totalImportQty  = dao.getTotalImportQty(date, keyword);
        //   long totalExportQty  = dao.getTotalExportQty(date, keyword);
        //   long totalAdjustQty  = dao.getTotalAdjustQty(date, keyword);
        //   BigDecimal totalImportValue = dao.getTotalImportValue(date, keyword);
        //   BigDecimal totalExportValue = dao.getTotalExportValue(date, keyword);
        //   List<ImportExportByDayDTO> top5Import = dao.getTop5Import(date);
        //   List<ImportExportByDayDTO> top5Export = dao.getTop5Export(date);

        request.setAttribute("page",       page);
        request.setAttribute("pageSize",   DEFAULT_PAGE_SIZE);
        request.setAttribute("totalPages", 1); // default; replace with dao.getTotalPages(...) when DAO is ready
        request.setAttribute("focusTable", (date != null && !date.isEmpty())
                                        || (keyword != null && !keyword.isEmpty()));

        // ── 4. Forward to the JSP ────────────────────────────────────────────
        request.getRequestDispatcher("/WEB-INF/view/report/importExportByDayReport.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method – delegates to GET.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Import/Export Detail Report By Day Servlet";
    }
}

