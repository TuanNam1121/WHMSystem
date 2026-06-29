package com.swp.whmsystem.controller.inventoryTransaction;

import com.swp.whmsystem.dto.InventoryTransactionDTO;
import com.swp.whmsystem.dal.InventoryTransactionDAO;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InventoryTransaction", urlPatterns = { "/InventoryTransaction" })
public class InventoryTransaction extends HttpServlet {
    InventoryTransactionDAO inventoryTransactionDAO;

    @Override
    public void init() {
        inventoryTransactionDAO = new InventoryTransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_INVENTORY_TRANSACTION,
                "You are not authorized to view the inventory transactions.")) {
            return;
        }
        String type = request.getParameter("type");
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

        List<InventoryTransactionDTO> transactions = inventoryTransactionDAO.getCompletedTransaction(type, offset,
                pageSize);

        int totalRecords = inventoryTransactionDAO.totalCompletedTransaction(type);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("type", type);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("WEB-INF/view/inventoryTransaction/inventoryTransaction.jsp").forward(request,
                response);
    }
}
