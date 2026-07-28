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
        int currentYear = Year.now().getValue();
        int chartYear = currentYear;
        String yearRaw = request.getParameter("year");
        if (yearRaw != null && !yearRaw.trim().isEmpty()) {
            try {
                int selectedYear = Integer.parseInt(yearRaw.trim());
                if (selectedYear >= MIN_CHART_YEAR && selectedYear <= currentYear) {
                    chartYear = selectedYear;
                }
            } catch (NumberFormatException ignored) {
                chartYear = currentYear;
            }
        }

        List<BigDecimal> monthlySales = orderDAO.getMonthlySaleTotals(chartYear);
        StringBuilder salesJson = new StringBuilder("[");
        for (int i = 0; i < monthlySales.size(); i++) {
            if (i > 0) {
                salesJson.append(",");
            }
            BigDecimal value = monthlySales.get(i);
            if (value == null) {
                salesJson.append("0");
            } else {
                salesJson.append(value.stripTrailingZeros().toPlainString());
            }
        }
        salesJson.append("]");

        List<BigDecimal> monthlyPurchases =
                purchaseRequestDAO.getMonthlyPurchaseTotals(chartYear);
        StringBuilder purchaseJson = new StringBuilder("[");
        for (int i = 0; i < monthlyPurchases.size(); i++) {
            if (i > 0) {
                purchaseJson.append(",");
            }
            BigDecimal value = monthlyPurchases.get(i);
            if (value == null) {
                purchaseJson.append("0");
            } else {
                purchaseJson.append(value.stripTrailingZeros().toPlainString());
            }
        }
        purchaseJson.append("]");

        request.setAttribute("chartYear", chartYear);
        request.setAttribute("maxChartYear", currentYear);
        request.setAttribute("monthlySalesChartData", salesJson.toString());
        request.setAttribute("monthlyPurchaseChartData", purchaseJson.toString());

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
