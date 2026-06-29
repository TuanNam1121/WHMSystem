package com.swp.whmsystem.controller.report;

import java.io.IOException;

import com.swp.whmsystem.dal.StockMovementDAO;
import com.swp.whmsystem.model.StockMovement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewStockMovementDetail", urlPatterns = {"/viewStockMovementDetail"})
public class ViewStockMovementDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String movementIdRaw = request.getParameter("movementId");
        if (movementIdRaw == null || movementIdRaw.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/inventorySummaryReport");
            return;
        }

        try {
            int movementId = Integer.parseInt(movementIdRaw.trim());
            StockMovementDAO dao = new StockMovementDAO();
            StockMovement sm = dao.getStockMovementById(movementId);

            if (sm != null) {
                String refType = sm.getReference_type();
                int refId = sm.getReference_id();

                if ("IMPORT".equalsIgnoreCase(refType)) {
                    response.sendRedirect(request.getContextPath() + "/ImportHistoryDetail?receiptId=" + refId);
                    return;
                } else if ("EXPORT".equalsIgnoreCase(refType)) {
                    int orderId = dao.getOrderIdByExportReceiptId(refId);
                    if (orderId > 0) {
                        response.sendRedirect(request.getContextPath() + "/exportDetail?orderId=" + orderId);
                        return;
                    }
                } else if (refId > 0) {
                    response.sendRedirect(request.getContextPath() + "/InventoryAuditDetail?id=" + refId);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/inventorySummaryReport");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
