package com.swp.whmsystem.controller.report;

import com.swp.whmsystem.dal.InventorySummaryDAO;
import com.swp.whmsystem.model.InventorySummary;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StockReport", urlPatterns = {"/stockReport"})
public class StockReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_REPORT,
                "You don't have permission to view reports!")) {
            return;
        }

        String keyword = request.getParameter("keyword");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String sortColumn = request.getParameter("sortColumn");
        String sortOrder = request.getParameter("sortOrder");
        
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
        int totalRecords = dao.countStockReport(keyword);
        long[] stockTotals = dao.getStockReportTotals(fromDate, toDate, keyword);
        long totalOpeningStock = stockTotals[0];
        long totalClosingStock = stockTotals[1];

        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }
        
        page = Math.min(page, totalPages);

        List<InventorySummary> reportList = dao.forStockReport(fromDate, toDate, keyword, page, pageSize, sortColumn, sortOrder);

        request.setAttribute("reportList", reportList);
        request.setAttribute("totalOpeningStock", totalOpeningStock);
        request.setAttribute("totalClosingStock", totalClosingStock);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("reportType", "stock");
        request.setAttribute("reportTitle", "Stock Report");
        request.setAttribute("reportSubtitle", "View opening and closing stock by date");
        request.setAttribute("reportPath", "stockReport");
        request.setAttribute("excelPath", "ExportStockReport");
        request.setAttribute("showDateFilter", true);
        request.setAttribute("focusTable", request.getQueryString() != null);
        request.getRequestDispatcher("/WEB-INF/view/report/stockReport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
