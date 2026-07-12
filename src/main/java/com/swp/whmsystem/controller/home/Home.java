package com.swp.whmsystem.controller.home;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

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
    private static final int MIN_CHART_YEAR = 2021;
    private static final int MAX_CHART_YEAR = 9999;

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

        // -------------------------------------------------------------------------------------------------------------
        // First row: financial summary cards.
        OrderDAO orderDAO = new OrderDAO();
        GoodReceiptDAO goodReceiptDAO = new GoodReceiptDAO();
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();

        request.setAttribute("newPurchaseOrderTotalPrice",
                purchaseRequestDAO.getApprovedAndIncompletedPurchaseRequestTotalPrice());
        request.setAttribute("newSaleOrderTotalPrice", orderDAO.getNewSaleOrderTotalPrice());
        request.setAttribute("completedImportTotalPrice", goodReceiptDAO.getCompletedImportTotalPrice());
        request.setAttribute("completedSaleOrderTotalPrice", orderDAO.getCompletedSaleOrderTotalPrice());

        // -------------------------------------------------------------------------------------------------------------
        // Second row: customer, supplier, purchase invoice, and sales invoice cards.
        CustomerDAO customerDAO = new CustomerDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        InventoryDAO inventoryDAO = new InventoryDAO();

        request.setAttribute("customerCount", customerDAO.countCustomers());
        request.setAttribute("activeSupplierCount", supplierDAO.countActiveSuppliers());
        request.setAttribute("completedPurchaseInvoiceCount",
                purchaseRequestDAO.countPurchaseItem(0, null, "COMPLETED", null));
        request.setAttribute("completedSaleInvoiceCount", exportItemDAO.countCompletedExportReceipts());
        request.setAttribute("lowStockProducts", inventoryDAO.getLowStockProducts(10));
        request.setAttribute("topSellingProducts", orderDAO.getTopSellingProducts(5));

        // -------------------------------------------------------------------------------------------------------------
        // Purchase and sales chart.
        int chartYear = getChartYear(request);
        request.setAttribute("chartYear", chartYear);
        request.setAttribute("monthlySalesChartData", toJsonArray(orderDAO.getMonthlySaleTotals(chartYear)));
        request.setAttribute("monthlyPurchaseChartData", toJsonArray(purchaseRequestDAO.getMonthlyPurchaseTotals(chartYear)));

        request.getRequestDispatcher("WEB-INF/view/home/home.jsp").forward(request, response);
    }

    private int getChartYear(HttpServletRequest request) {
        String yearRaw = request.getParameter("year");
        int currentYear = Year.now().getValue();
        if (yearRaw == null || yearRaw.trim().isEmpty()) {
            return currentYear;
        }
        try {
            int year = Integer.parseInt(yearRaw.trim());
            return year >= MIN_CHART_YEAR && year <= MAX_CHART_YEAR ? year : currentYear;
        } catch (NumberFormatException e) {
            return currentYear;
        }
    }

    private String toJsonArray(List<BigDecimal> values) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            BigDecimal value = values.get(i);
            json.append(value == null ? BigDecimal.ZERO.toPlainString() : value.stripTrailingZeros().toPlainString());
        }
        json.append("]");
        return json.toString();
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
