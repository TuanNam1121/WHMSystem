package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.model.Order;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ExportHistory", urlPatterns = {"/exportHistory"})
public class ExportHistory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_EXPORT_HISTORY,
                "You don't have permission to view export history!")) {
            return;
        }

        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = new ArrayList<>();
        String keyword = request.getParameter("keyword");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String sortBy = request.getParameter("sortBy");
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

        int totalOrders = orderDAO.countExportHistory(keyword, fromDate, toDate);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalOrders / pageSize));
        page = Math.min(page, totalPages);

        orderList = orderDAO.searchExportHistory(
                keyword, fromDate, toDate, sortBy, pageSize, page
        );

        session.setAttribute("orderList", orderList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("focusTable",
                keyword != null || fromDate != null || toDate != null
                        || sortBy != null || pageSizeRaw != null || pageRaw != null);
        request.getRequestDispatcher("WEB-INF/view/export/exportHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
