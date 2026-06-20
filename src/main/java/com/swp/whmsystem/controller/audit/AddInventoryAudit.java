package com.swp.whmsystem.controller.audit;

import com.swp.whmsystem.dal.InventoryAuditDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.model.InventoryAuditItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddInventoryAudit", urlPatterns = { "/AddInventoryAudit" })
public class AddInventoryAudit extends HttpServlet {
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
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_INVENTORY_AUDIT,
                "Only managers with create audit permission are authorized to create inventory audits.")) {
            return;
        }

        List<Product> products = productDAO.getProductList();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/view/audit/addInventoryAudit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_INVENTORY_AUDIT,
                "Only managers with create audit permission are authorized to create inventory audits.")) {
            return;
        }

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String[] selectedProductIds = request.getParameterValues("selectedProductIds");
        if (selectedProductIds == null || selectedProductIds.length == 0) {
            request.setAttribute("message", "Please select at least one product to audit.");
            List<Product> products = productDAO.getProductList();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/view/audit/addInventoryAudit.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        InventoryAuditStatus status = "submit".equalsIgnoreCase(action) ? InventoryAuditStatus.SUBMITTED
                : InventoryAuditStatus.DRAFT;

        InventoryAudit audit = new InventoryAudit();
        audit.setUserId(user.getId());
        audit.setStatus(status);

        int auditId = inventoryAuditDAO.insertInventoryAudit(audit);
        if (auditId <= 0) {
            request.setAttribute("message", "Failed to create inventory audit record.");
            List<Product> products = productDAO.getProductList();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/view/audit/addInventoryAudit.jsp").forward(request, response);
            return;
        }

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
            } catch (NumberFormatException ignored) {
            }
        }

        response.sendRedirect("InventoryAuditList");
    }
}
