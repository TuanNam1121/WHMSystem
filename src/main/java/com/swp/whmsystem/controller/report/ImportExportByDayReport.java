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

/*
 * @author Admin
 */
@WebServlet(name = "ImportExportByDayReport", urlPatterns = {"/ImportExportByDayReport"})
public class ImportExportByDayReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String date = request.getParameter("date");
            String keyword = request.getParameter("keyword");
            String sortBy = request.getParameter("sortBy");
            String sortDir = request.getParameter("sortDir");
            String pageSizeRaw = request.getParameter("pageSize");
            String pageRaw = request.getParameter("page");

            try {
                date = LocalDate.parse(date).toString();
            } catch (Exception e) {
                date = LocalDate.now().toString();
            }
            int pageSize = 10;
            int page = 1;

            if (pageRaw != null && !pageRaw.trim().isEmpty()) {
                try {
                    page = Integer.parseInt(pageRaw);
                    if (page < 1) {
                        page = 1;
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            if (pageSizeRaw != null && !pageSizeRaw.trim().isEmpty()) {
                try {
                    int parsedPageSize = Integer.parseInt(pageSizeRaw.trim());
                    if (parsedPageSize > 0 && parsedPageSize <= 100) {
                        pageSize = parsedPageSize;
                    }
                } catch (NumberFormatException ignored) {
                    pageSize = 10;
                }
            }

            DailyTransactionDAO dao = new DailyTransactionDAO();
            List<DailyTransaction> list = dao.searchDailyTransaction(date, keyword, sortBy, sortDir, page, pageSize);
            long totalImportQty = dao.getTotalImportQty(date, keyword);
            long totalExportQty = dao.getTotalExportQty(date, keyword);
            long totalAdjustQty = dao.getTotalAdjustQty(date, keyword);
            long totalImportValue = dao.getTotalImportValue(date, keyword);
            long totalExportValue = dao.getTotalExportValue(date, keyword);
            int totalRows = dao.countDailyTransaction(keyword);
            int totalPages = (int) Math.ceil((double) totalRows / pageSize);
            request.setAttribute("totalImportQty", totalImportQty);
            request.setAttribute("totalExportQty", totalExportQty);
            request.setAttribute("totalAdjustQty", totalAdjustQty);
            request.setAttribute("totalImportValue", totalImportValue);
            request.setAttribute("totalExportValue", totalExportValue);
            request.setAttribute("list", list);
            request.setAttribute("page", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPages", totalPages);
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
