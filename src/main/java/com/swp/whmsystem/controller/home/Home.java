package com.swp.whmsystem.controller.home;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;

@WebServlet(name = "Home", urlPatterns = {"/home"})
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        RoleDAO role = new RoleDAO();
        if (session == null || session.getAttribute("user") == null) {
            request.getRequestDispatcher("login").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        if (role.getRoleNamFromRoleID(user.getRoleId()).equals("ADMIN")) {
            response.sendRedirect("AdminDashBoard");
            return;
        }

        // -------------------------------------------------------------------------------------------------------------
        // First row: financial summary cards.
        OrderDAO orderDAO = new OrderDAO();
        GoodReceiptDAO goodReceiptDAO = new GoodReceiptDAO();
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();

        request.setAttribute("newPurchaseOrderTotalPrice", purchaseRequestDAO.getNewPurchaseOrderTotalPrice());
        request.setAttribute("newSaleOrderTotalPrice", orderDAO.getNewSaleOrderTotalPrice());
        request.setAttribute("completedImportTotalPrice", goodReceiptDAO.getCompletedImportTotalPrice());
        request.setAttribute("completedSaleOrderTotalPrice", orderDAO.getCompletedSaleOrderTotalPrice());

        // -------------------------------------------------------------------------------------------------------------
        // Second row: customer, supplier, purchase invoice, and sales invoice cards.
        CustomerDAO customerDAO = new CustomerDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        ExportItemDAO exportItemDAO = new ExportItemDAO();

        request.setAttribute("customerCount", customerDAO.countCustomers());
        request.setAttribute("activeSupplierCount", supplierDAO.countActiveSuppliers());
        request.setAttribute("completedPurchaseInvoiceCount",
                purchaseRequestDAO.countPurchaseItem(0, 0, "COMPLETED", null));
        request.setAttribute("completedSaleInvoiceCount", exportItemDAO.countCompletedExportReceipts());

        request.getRequestDispatcher("WEB-INF/view/home/home.jsp").forward(request, response);
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
