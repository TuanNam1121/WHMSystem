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

@WebServlet(name = "ReviewInventoryAudit", urlPatterns = { "/ReviewInventoryAudit" })
public class ReviewInventoryAudit extends HttpServlet {
    private InventoryAuditDAO inventoryAuditDAO;

    @Override
    public void init() {
        inventoryAuditDAO = new InventoryAuditDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.APPROVE_INVENTORY_AUDIT,
                "Only managers with approve audit permission are authorized to review inventory audits.")) {
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

            if (audit.getStatus() != InventoryAuditStatus.PENDING) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only pending approval audits can be reviewed.");
                return;
            }

            request.setAttribute("audit", audit);
            request.getRequestDispatcher("/WEB-INF/view/audit/reviewInventoryAudit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.APPROVE_INVENTORY_AUDIT,
                "Only managers with approve audit permission are authorized to review inventory audits.")) {
            return;
        }

        String idParam = request.getParameter("id");
        String action = request.getParameter("action");
        if (idParam == null || idParam.trim().isEmpty() || action == null) {
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

            if (audit.getStatus() != InventoryAuditStatus.PENDING) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only pending approval audits can be reviewed.");
                return;
            }

            List<InventoryAuditItem> items = audit.getInventoryAuditItems();

            if ("approve".equalsIgnoreCase(action)) {
                inventoryAuditDAO.approveInventoryAudit(auditId, items);
            } else if ("decline".equalsIgnoreCase(action)) {
                inventoryAuditDAO.updateInventoryAuditStatus(auditId, InventoryAuditStatus.REJECTED);
            }

            response.sendRedirect("InventoryAuditList");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }
}
