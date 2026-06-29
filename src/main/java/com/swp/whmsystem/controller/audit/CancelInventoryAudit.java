package com.swp.whmsystem.controller.audit;

import com.swp.whmsystem.dal.InventoryAuditDAO;
import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CancelInventoryAudit", urlPatterns = { "/CancelInventoryAudit" })
public class CancelInventoryAudit extends HttpServlet {
    private InventoryAuditDAO inventoryAuditDAO;

    @Override
    public void init() {
        inventoryAuditDAO = new InventoryAuditDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_INVENTORY_AUDIT,
                "You are not authorized to cancel inventory audits.")) {
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

            if (audit.getStatus() == InventoryAuditStatus.DRAFT) {

                inventoryAuditDAO.deleteInventoryAuditAndItems(auditId);
            } else if (audit.getStatus() == InventoryAuditStatus.SUBMITTED) {

                inventoryAuditDAO.updateInventoryAuditStatus(auditId, InventoryAuditStatus.CANCELLED);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Only draft or submitted audits can be cancelled.");
                return;
            }

            response.sendRedirect("InventoryAuditList");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }
}
