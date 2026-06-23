package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ToExportList", urlPatterns = {"/toExportList"})
public class ToExportList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = new ArrayList<>();
        String keyword = request.getParameter("keyword");
        String date = request.getParameter("date");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sortBy");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int pageSize = 10;
        int page = 1;

        if (status != null && !status.trim().isEmpty()) {
            if (!status.equals("NEW") && !status.equals("DOING")
                    && !status.equals("COMPLETED") && !status.equals("CANCELLED")) {
                status = null;
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

        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageRaw.trim()));
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        int totalOrders = orderDAO.countOrdersToExport(keyword, date, status);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalOrders / pageSize));
        page = Math.min(page, totalPages);

        orderList = orderDAO.searchOrdersToExport(
                keyword, date, status, sortBy, pageSize, page
        );

        session.setAttribute("orderList", orderList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("focusTable",
                keyword != null || date != null || status != null
                        || sortBy != null || pageSizeRaw != null || pageRaw != null);
        request.getRequestDispatcher("WEB-INF/view/export/toExportList.jsp").forward(request, response);
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
