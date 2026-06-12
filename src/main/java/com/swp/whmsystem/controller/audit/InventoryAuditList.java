package com.swp.whmsystem.controller.audit;

import com.swp.whmsystem.dal.InventoryAuditDAO;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "InventoryAuditList", urlPatterns = {"/InventoryAuditList"})
public class InventoryAuditList extends HttpServlet {
    private InventoryAuditDAO inventoryAuditDAO;

    @Override
    public void init() {
        inventoryAuditDAO = new InventoryAuditDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_INVENTORY_AUDIT, "You are not authorized to view the inventory audits.")) {
            return;
        }

        List<InventoryAudit> inventoryAudits = inventoryAuditDAO.getAllInventoryAudit();

        request.setAttribute("inventoryAudits", inventoryAudits);
        request.getRequestDispatcher("/WEB-INF/view/audit/inventoryAuditList.jsp").forward(request, response);
    }
}
