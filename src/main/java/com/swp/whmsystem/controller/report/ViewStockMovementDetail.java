package com.swp.whmsystem.controller.report;

import java.io.IOException;
import com.swp.whmsystem.dal.StockMovementDAO;
import com.swp.whmsystem.model.StockMovement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewStockMovementDetail", urlPatterns = { "/viewStockMovementDetail" })
public class ViewStockMovementDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String movementIdRaw = request.getParameter("movementId");
        if (movementIdRaw != null && !movementIdRaw.trim().isEmpty()) {
            try {
                int movementId = Integer.parseInt(movementIdRaw.trim());
                StockMovementDAO dao = new StockMovementDAO();
                StockMovement sm = dao.getStockMovementById(movementId);
                if (sm != null) {
                    if ("INCREASED".equalsIgnoreCase(sm.getType())) {
                        int receiptId = sm.getReference_id();
                        if (receiptId <= 0 || !dao.goodReceiptExists(receiptId)) {
                            receiptId = dao.getAssociatedGoodReceiptId(sm.getProductId(), sm.getCreatedAt());
                        }
                        if (receiptId > 0) {
                            response.sendRedirect(request.getContextPath() + "/ImportHistoryDetail?receiptId=" + receiptId);
                            return;
                        }
                    } else if ("DECREASED".equalsIgnoreCase(sm.getType())) {
                        int orderId = dao.getAssociatedOrderIdForExport(sm.getReference_id(), sm.getProductId(), sm.getCreatedAt());
                        if (orderId > 0) {
                            response.sendRedirect(request.getContextPath() + "/exportDetail?orderId=" + orderId);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Fallback if detail not found
        response.sendRedirect(request.getContextPath() + "/inventorySummaryReport");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
