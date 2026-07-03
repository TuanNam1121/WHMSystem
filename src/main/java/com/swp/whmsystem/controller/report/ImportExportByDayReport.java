/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.report;

import com.swp.whmsystem.dal.DailyTransactionDAO;
import com.swp.whmsystem.model.DailyTransaction;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet for the Import/Export Detail Report by Day page.
 *
 * Query parameters accepted (all optional): date – DD-MM-YYYY : the day to
 * report on keyword – String : search by product name or SKU (LIKE %keyword%)
 * sortBy – String : sku | name | importQty | exportQty sortDir – String : asc |
 * desc page – int : pagination page number (default 1)
 *
 * Attributes set for the JSP: reportList – List of daily import/export rows
 * totalImportQty – long totalExportQty – long totalAdjustQty – long
 * totalImportValue – BigDecimal totalExportValue – BigDecimal page, pageSize –
 * pagination helpers focusTable – boolean (scroll to table after submit)
 *
 * @author Admin
 */
@WebServlet(name = "ImportExportByDayReport", urlPatterns = {"/ImportExportByDayReport"})
public class ImportExportByDayReport extends HttpServlet {

    private static final int DEFAULT_PAGE_SIZE = 15;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String date = request.getParameter("date");
            String keyword = request.getParameter("keyword");
            String sortBy = request.getParameter("sortBy");
            String sortDir = request.getParameter("sortDir");

            int page = 1, pageSize = DEFAULT_PAGE_SIZE;
            try {
                date = LocalDate.parse(date).toString();
            } catch (Exception e) {
                date = LocalDate.now().toString();
            }
            try {
                page = Integer.parseInt(request.getParameter("page"));
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException ignored) {
                page = 1;
            }

            DailyTransactionDAO dao = new DailyTransactionDAO();
            List<DailyTransaction> list = dao.searchDailyTransaction(date, keyword, sortBy, sortDir, page, pageSize);
            long totalImportQty = dao.getTotalImportQty(date, keyword);
            long totalExportQty = dao.getTotalExportQty(date, keyword);
            long totalAdjustQty = dao.getTotalAdjustQty(date, keyword);
            long totalImportValue = dao.getTotalImportValue(date, keyword);
            long totalExportValue = dao.getTotalExportValue(date, keyword);
            request.setAttribute("totalImportQty", totalImportQty);
            request.setAttribute("totalExportQty", totalExportQty);
            request.setAttribute("totalAdjustQty", totalAdjustQty);
            request.setAttribute("totalImportValue", totalImportValue);
            request.setAttribute("totalExportValue", totalExportValue);
            request.setAttribute("list", list);
            request.setAttribute("page", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPages", dao.countDailyTransaction(keyword) / pageSize);
        } catch (Exception ex) {
            HttpSession session = request.getSession();
            session.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/report/importExportByDayReport.jsp").forward(request, response);
            return;
        }
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
