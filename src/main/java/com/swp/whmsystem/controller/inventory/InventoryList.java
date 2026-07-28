package com.swp.whmsystem.controller.inventory;

import com.swp.whmsystem.dal.InventoryDAO;
import com.swp.whmsystem.dto.InventoryItemDTO;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.InputValidationUtil;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "InventoryList", urlPatterns = {"/inventory"})
public class InventoryList extends HttpServlet {
    private static final int ADMIN_ROLE_ID = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_INVENTORY,
                "You don't have permission to view inventory!")) {
            return;
        }

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser.getRoleId() == ADMIN_ROLE_ID) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.setAttribute("errorMessage", "Administrators are not allowed to view inventory!");
            request.getRequestDispatcher("/WEB-INF/view/error/error-403.jsp").forward(request, response);
            return;
        }

        InventoryDAO inventoryDAO = new InventoryDAO();
        String keyword = InputValidationUtil.normalizeSearchText(
                request.getParameter("keyword"), 100);
        String stockStatus = request.getParameter("stockStatus");
        String sortBy = request.getParameter("sortBy");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int pageSize = 10;
        int page = 1;

        if (stockStatus != null && !List.of(
                "inStock", "lowStock", "outOfStock").contains(stockStatus)) {
            stockStatus = null;
        }

        if (sortBy == null || !List.of(
                "quantityAsc", "quantityDesc", "valueAsc", "valueDesc").contains(sortBy)) {
            sortBy = "quantityDesc";
        }

        if (pageSizeRaw != null && !pageSizeRaw.trim().isEmpty()) {
            try {
                int parsedPageSize = Integer.parseInt(pageSizeRaw.trim());
                if (parsedPageSize > 0 && parsedPageSize <= 100) {
                    pageSize = parsedPageSize;
                }
            } catch (NumberFormatException e) {
                pageSize = 10;
            }
        }

        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageRaw.trim()));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int totalRecords = inventoryDAO.countInventory(keyword, stockStatus);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalRecords / pageSize));
        page = Math.min(page, totalPages);

        List<InventoryItemDTO> inventoryList =
                inventoryDAO.searchInventory(keyword, stockStatus, sortBy, pageSize, page);
        List<InventoryItemDTO> allInventoryList = inventoryDAO.getInventoryList();

        int totalProducts = allInventoryList.size();
        int lowStockProducts = 0;
        int outOfStockProducts = 0;
        BigDecimal totalInventoryValue = BigDecimal.ZERO;

        for (InventoryItemDTO item : allInventoryList) {
            if (item.getQuantity() == 0) {
                outOfStockProducts++;
            } else if (item.getQuantity() <= 10) {
                lowStockProducts++;
            }
            totalInventoryValue = totalInventoryValue.add(item.getTotalValue());
        }

        request.setAttribute("inventoryList", inventoryList);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("lowStockProducts", lowStockProducts);
        request.setAttribute("outOfStockProducts", outOfStockProducts);
        request.setAttribute("totalInventoryValue", totalInventoryValue);
        request.setAttribute("keyword", keyword);
        request.setAttribute("stockStatus", stockStatus);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/view/inventory/inventoryList.jsp").forward(request, response);
    }
}
