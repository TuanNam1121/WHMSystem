package com.swp.whmsystem.controller.audit;

import com.swp.whmsystem.dal.InventoryAuditDAO;
import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.model.InventoryAuditItem;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PerformInventoryAudit", urlPatterns = {"/PerformInventoryAudit"})
public class PerformInventoryAudit extends HttpServlet {
    private InventoryAuditDAO inventoryAuditDAO;

    @Override
    public void init() {
        inventoryAuditDAO = new InventoryAuditDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PERFORM_INVENTORY_AUDIT, "Only staff with perform audit permission are authorized to perform inventory audits.")) {
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
            return;
        }

        try {
            int auditId = Integer.parseInt(idParam);
            InventoryAudit audit = inventoryAuditDAO.getInventoryAuditById(auditId);
            if (audit == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory audit not found.");
                return;
            }

            if (audit.getStatus() != InventoryAuditStatus.SUBMITTED) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only submitted audits can be performed.");
                return;
            }

            request.setAttribute("audit", audit);
            request.getRequestDispatcher("/WEB-INF/view/audit/performInventoryAudit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PERFORM_INVENTORY_AUDIT, "Only staff with perform audit permission are authorized to perform inventory audits.")) {
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
            return;
        }

        try {
            int auditId = Integer.parseInt(idParam);
            InventoryAudit audit = inventoryAuditDAO.getInventoryAuditById(auditId);
            if (audit == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory audit not found.");
                return;
            }

            if (audit.getStatus() != InventoryAuditStatus.SUBMITTED) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only submitted audits can be performed.");
                return;
            }

            List<InventoryAuditItem> items = audit.getInventoryAuditItems();
            boolean hasError = false;
            String errorMessage = "";

            for (InventoryAuditItem item : items) {
                String qtyStr = request.getParameter("physicalQuantity_" + item.getId());
                String reason = request.getParameter("reason_" + item.getId());

                if (qtyStr == null || qtyStr.trim().isEmpty()) {
                    hasError = true;
                    errorMessage = "Physical quantity is required for all items.";
                    break;
                }

                try {
                    int physicalQuantity = Integer.parseInt(qtyStr.trim());
                    if (physicalQuantity < 0) {
                        hasError = true;
                        errorMessage = "Physical quantity cannot be negative.";
                        break;
                    }


                    if (physicalQuantity != item.getSystemQuantity()) {
                        if (reason == null || reason.trim().isEmpty()) {
                            hasError = true;
                            errorMessage = "Reason is required for products with discrepancies.";
                            break;
                        }
                    }

                    item.setPhysicalQuantity(physicalQuantity);
                    item.setReason(reason == null ? "" : reason.trim());
                } catch (NumberFormatException e) {
                    hasError = true;
                    errorMessage = "Physical quantity must be a valid number.";
                    break;
                }
            }

            if (hasError) {
                request.setAttribute("message", errorMessage);
                request.setAttribute("audit", audit);
                request.getRequestDispatcher("/WEB-INF/view/audit/performInventoryAudit.jsp").forward(request, response);
                return;
            }


            boolean hasDiscrepancy = false;
            for (InventoryAuditItem item : items) {
                if (item.getPhysicalQuantity() != item.getSystemQuantity()) {
                    hasDiscrepancy = true;
                    break;
                }
            }
            
            inventoryAuditDAO.updateAuditItems(items);

            if (hasDiscrepancy) {
                response.sendRedirect(request.getContextPath() + "/AdjustInventoryAuditStock?id=" + auditId);
            } else {
                inventoryAuditDAO.updateInventoryAuditStatus(auditId, InventoryAuditStatus.PENDING);
                response.sendRedirect("InventoryAuditList");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }
}
