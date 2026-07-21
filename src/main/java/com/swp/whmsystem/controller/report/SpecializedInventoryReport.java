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
import java.util.Collections;
import java.util.List;

@WebServlet(name = "SpecializedInventoryReport", urlPatterns = {
        "/importReport", "/exportReport", "/stockReport"
})
public class SpecializedInventoryReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SpecializedReportType reportType = SpecializedReportType.fromServletPath(request.getServletPath());
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_REPORT,
                "You don't have permission to view reports!")) {
            return;
        }

        String keyword = request.getParameter("keyword");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String sortColumn = request.getParameter("sortColumn");
        String sortOrder = request.getParameter("sortOrder");
        int pageSize = parsePageSize(request.getParameter("pageSize"));
        int requestedPage = parsePage(request.getParameter("page"));

        boolean validDateRange = validateDateRange(request, fromDate, toDate);
        InventorySummaryDAO dao = new InventorySummaryDAO();
        int totalRecords = 0;
        long totalQuantity = 0L;
        long totalOpeningStock = 0L;
        long totalClosingStock = 0L;

        if (validDateRange) {
            if (reportType.isMovementReport()) {
                totalRecords = dao.countMovementReport(reportType.getMovementType(),
                        reportType.getReferenceType(), fromDate, toDate, keyword);
                totalQuantity = dao.getMovementReportTotal(reportType.getMovementType(),
                        reportType.getReferenceType(), fromDate, toDate, keyword);
            } else {
                totalRecords = dao.countStockReport(keyword);
                long[] stockTotals = dao.getStockReportTotals(fromDate, toDate, keyword);
                totalOpeningStock = stockTotals[0];
                totalClosingStock = stockTotals[1];
                totalQuantity = totalClosingStock;
            }
        }

        int totalPages = Math.max(1, (int) Math.ceil((double) totalRecords / pageSize));
        int page = Math.min(requestedPage, totalPages);
        List<InventorySummary> reportList = Collections.emptyList();
        if (validDateRange) {
            if (reportType.isMovementReport()) {
                reportList = dao.forMovementReport(reportType.getMovementType(),
                        reportType.getReferenceType(), fromDate, toDate, keyword,
                        page, pageSize, sortOrder);
            } else {
                reportList = dao.forStockReport(fromDate, toDate, keyword, page, pageSize,
                        sortColumn, sortOrder);
            }
        }

        request.setAttribute("reportList", reportList);
        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("totalOpeningStock", totalOpeningStock);
        request.setAttribute("totalClosingStock", totalClosingStock);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("reportType", reportType.getCode());
        request.setAttribute("reportTitle", reportType.getTitle());
        request.setAttribute("reportSubtitle", reportType.getSubtitle());
        request.setAttribute("quantityLabel", reportType.getQuantityLabel());
        request.setAttribute("reportPath", reportType.getReportPath());
        request.setAttribute("excelPath", reportType.getExcelPath());
        request.setAttribute("showDateFilter", true);
        request.setAttribute("focusTable", request.getQueryString() != null);
        request.getRequestDispatcher("/WEB-INF/view/report/specializedInventoryReport.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean validateDateRange(HttpServletRequest request, String fromDate, String toDate) {
        LocalDate from = parseOptionalDate(fromDate);
        LocalDate to = parseOptionalDate(toDate);
        if ((hasText(fromDate) && from == null) || (hasText(toDate) && to == null)) {
            request.setAttribute("filterError", "Dates must use the DD-MM-YYYY format.");
            return false;
        }
        if (from != null && to != null && from.isAfter(to)) {
            request.setAttribute("filterError", "From Date cannot be after To Date.");
            return false;
        }
        return true;
    }

    private LocalDate parseOptionalDate(String value) {
        return hasText(value) ? DateUtils.parseDate(value) : null;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private int parsePageSize(String rawValue) {
        try {
            int value = Integer.parseInt(rawValue);
            return value > 0 && value <= 100 ? value : 10;
        } catch (NumberFormatException | NullPointerException e) {
            return 10;
        }
    }

    private int parsePage(String rawValue) {
        try {
            return Math.max(1, Integer.parseInt(rawValue));
        } catch (NumberFormatException | NullPointerException e) {
            return 1;
        }
    }
}
