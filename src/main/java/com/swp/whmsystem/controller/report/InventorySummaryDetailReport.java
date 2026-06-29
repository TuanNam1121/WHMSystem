package com.swp.whmsystem.controller.report;

import java.io.IOException;
import java.util.List;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.StockMovementDAO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.StockMovement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "InventorySummaryDetailReport", urlPatterns = { "/inventorySummaryDetail" })
public class InventorySummaryDetailReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdRaw = request.getParameter("productId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String typeFilter = request.getParameter("typeFilter");

        int productId = -1;
        if (productIdRaw != null && !productIdRaw.trim().isEmpty()) {
            try {
                productId = Integer.parseInt(productIdRaw.trim());
            } catch (NumberFormatException ignored) {
            }
        }

        if (productId == -1) {
            response.sendRedirect("/inventorySummaryReport");
            return;
        }

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductFromId(productId);
        if (product == null) {
            response.sendRedirect("/inventorySummaryReport");
            return;
        }

        StockMovementDAO stockMovementDAO = new StockMovementDAO();
        List<StockMovement> movementList = stockMovementDAO.getStockMovementByProductIdAndDateRange(productId, fromDate, toDate, typeFilter);

        int totalImportQty = 0;
        int totalExportQty = 0;

        for (StockMovement item : movementList) {
            if ("INCREASED".equalsIgnoreCase(item.getType())) {
                totalImportQty += item.getQuantity();
            } else if ("DECREASED".equalsIgnoreCase(item.getType())) {
                totalExportQty += item.getQuantity();
            }
        }

        request.setAttribute("product", product);
        request.setAttribute("movementList", movementList);
        request.setAttribute("totalImportQty", totalImportQty);
        request.setAttribute("totalExportQty", totalExportQty);
        request.setAttribute("typeFilter", typeFilter != null ? typeFilter : "ALL");
        request.setAttribute("fromDate", fromDate != null ? fromDate : "");
        request.setAttribute("toDate", toDate != null ? toDate : "");

        request.getRequestDispatcher("WEB-INF/view/report/inventorySummaryDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Inventory Summary Detail Report Servlet";
    }
}
