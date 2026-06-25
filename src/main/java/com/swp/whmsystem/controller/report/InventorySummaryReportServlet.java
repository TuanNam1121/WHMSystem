package com.swp.whmsystem.controller.report;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for Inventory Summary Report (Báo cáo tổng hợp nhập xuất tồn)
 */
@WebServlet(name = "InventorySummaryReport", urlPatterns = { "/InventorySummaryReport" })
public class InventorySummaryReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Truyền các tham số phân trang mặc định để tránh lỗi JspTagException trong pagination.jsp
        request.setAttribute("page", 1);
        request.setAttribute("pageSize", 10);
        request.setAttribute("totalPages", 1);
        
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
