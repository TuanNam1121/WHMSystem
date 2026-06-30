package com.swp.whmsystem.controller.report;

import java.io.IOException;
import java.util.List;

import com.swp.whmsystem.dal.InventorySummaryDAO;
import com.swp.whmsystem.model.InventorySummary;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "InventorySummaryReport", urlPatterns = { "/inventorySummaryReport" })
public class InventorySummaryReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int pageSize = 10;
        int page = 1;

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

        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageRaw.trim()));
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        InventorySummaryDAO dao = new InventorySummaryDAO();
        int totalRecords = dao.countAll(keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }

        List<InventorySummary> reportList = dao.showAll(fromDate, toDate, keyword, page, pageSize);

        int[] grandTotals = dao.getGrandTotals(fromDate, toDate, keyword);
        int totalOpeningStock = grandTotals[0];
        int totalImportQty = grandTotals[1];
        int totalExportQty = grandTotals[2];
        int totalClosingStock = grandTotals[3];

        request.setAttribute("reportList", reportList);
        request.setAttribute("totalOpeningStock", totalOpeningStock);
        request.setAttribute("totalImportQty", totalImportQty);
        request.setAttribute("totalExportQty", totalExportQty);
        request.setAttribute("totalClosingStock", totalClosingStock);

        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("focusTable",
                keyword != null || fromDate != null || toDate != null
                        || pageSizeRaw != null || pageRaw != null);

        request.getRequestDispatcher("WEB-INF/view/report/inventorySummaryReport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Inventory Summary Report Servlet";
    }
}
