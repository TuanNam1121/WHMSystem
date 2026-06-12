package com.swp.whmsystem.controller.audit;

import com.swp.whmsystem.dal.InventoryAuditDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.model.InventoryAuditItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditInventoryAudit", urlPatterns = {"/EditInventoryAudit"})
public class EditInventoryAudit extends HttpServlet {
    private ProductDAO productDAO;
    private InventoryAuditDAO inventoryAuditDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        inventoryAuditDAO = new InventoryAuditDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_INVENTORY_AUDIT, "Only managers with create audit permission are authorized to edit inventory audits.")) {
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

            if (audit.getStatus() != InventoryAuditStatus.DRAFT) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only DRAFT audits can be edited.");
                return;
            }

            inventoryAuditDAO.refreshSystemQuantities(auditId);
            audit = inventoryAuditDAO.getInventoryAuditById(auditId);

            List<Product> products = productDAO.getProductList();
            request.setAttribute("products", products);
            request.setAttribute("audit", audit);
            request.getRequestDispatcher("/WEB-INF/view/audit/addInventoryAudit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_INVENTORY_AUDIT, "Only managers with create audit permission are authorized to edit inventory audits.")) {
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
            return;
        }

        int auditId;
        try {
            auditId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
            return;
        }

        InventoryAudit audit = inventoryAuditDAO.getInventoryAuditById(auditId);
        if (audit == null || audit.getStatus() != InventoryAuditStatus.DRAFT) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid audit request.");
            return;
        }

        String[] selectedProductIds = request.getParameterValues("selectedProductIds");
        if (selectedProductIds == null || selectedProductIds.length == 0) {
            request.setAttribute("message", "Please select at least one product to audit.");
            List<Product> products = productDAO.getProductList();
            request.setAttribute("products", products);
            request.setAttribute("audit", audit);
            request.getRequestDispatcher("/WEB-INF/view/audit/addInventoryAudit.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        InventoryAuditStatus status = "submit".equalsIgnoreCase(action) ? InventoryAuditStatus.SUBMITTED : InventoryAuditStatus.DRAFT;

        inventoryAuditDAO.updateInventoryAuditStatus(auditId, status);
        inventoryAuditDAO.deleteInventoryAuditItemsByAuditId(auditId);

        for (String idStr : selectedProductIds) {
            try {
                int productId = Integer.parseInt(idStr);
                String sysQtyStr = request.getParameter("systemQuantity_" + productId);
                int systemQuantity = (sysQtyStr != null && !sysQtyStr.isEmpty()) ? Integer.parseInt(sysQtyStr) : 0;

                InventoryAuditItem item = new InventoryAuditItem();
                item.setInventoryAuditId(auditId);
                item.setProductId(productId);
                item.setSystemQuantity(systemQuantity);
                item.setPhysicalQuantity(0);
                item.setReason("");

                inventoryAuditDAO.insertInventoryAuditItem(item);
            } catch (NumberFormatException ignored) {}
        }

        response.sendRedirect("InventoryAuditList");
    }
}
