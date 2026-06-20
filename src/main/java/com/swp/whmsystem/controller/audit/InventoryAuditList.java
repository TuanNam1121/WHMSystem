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

@WebServlet(name = "InventoryAuditList", urlPatterns = { "/InventoryAuditList" })
public class InventoryAuditList extends HttpServlet {
    private InventoryAuditDAO inventoryAuditDAO;

    @Override
    public void init() {
        inventoryAuditDAO = new InventoryAuditDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_INVENTORY_AUDIT,
                "You are not authorized to view the inventory audits.")) {
            return;
        }

        String keyword = request.getParameter("keyword");

        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * pageSize;

        List<InventoryAudit> inventoryAudits = inventoryAuditDAO.getInventoryAuditsByFilter(keyword, offset, pageSize);
        int totalRecords = inventoryAuditDAO.countInventoryAuditsByFilter(keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("inventoryAudits", inventoryAudits);
        request.setAttribute("keyword", keyword);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("/WEB-INF/view/audit/inventoryAuditList.jsp").forward(request, response);
    }
}
