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
import java.util.List;

@WebServlet(name = "ExportHistory", urlPatterns = {"/exportHistory"})
public class ExportHistory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getExportHistory();

        session.setAttribute("orderList", orderList);
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