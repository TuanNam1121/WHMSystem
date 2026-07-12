package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PurchaseRequestList", urlPatterns = {"/purchaseRequestList"})
public class PurchaseRequestList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_PURCHASE_ORDER,
                "You don't have permission to view your purchase requests!")) {
            return;
        }

        String code = request.getParameter("code");
        
        String status = request.getParameter("status");
        String dateStr = request.getParameter("date");
        String sort = request.getParameter("sort");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int pageSize = 10;
        int page = 1;

        if (pageSizeRaw != null && !pageSizeRaw.trim().isEmpty()) {
            try {
                int parsedPageSize = Integer.parseInt(pageSizeRaw.trim());
                if (parsedPageSize > 0 && parsedPageSize <= 100) {
                    pageSize = parsedPageSize;
                }
            } catch (NumberFormatException ignored) {
                pageSize = 10;
            }
        }

        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageRaw.trim()));
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        int totalRequests = purchaseRequestDAO.countPurchaseItem(user.getId(), code, status, dateStr);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalRequests / pageSize));
        page = Math.min(page, totalPages);

        List<PurchaseRequest> purchaseRequests = purchaseRequestDAO.searchPurchaseItem(user.getId(), code, status, dateStr, sort, pageSize, page);

        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("purchaseList", purchaseRequests);
        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/purchaseRequestList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_PURCHASE_ORDER,
                "You don't have permission to delete purchase requests!")) {
            return;
        }
        int prId = Integer.parseInt(request.getParameter("id"));
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
        purchaseItemDAO.deletePurchaseItemByRequestId(prId);
        purchaseRequestDAO.deletePurchaseRequest(prId);

        HttpSession session = request.getSession();
        session.setAttribute("error", "Purchase Request with Id: " + prId + " has been deleted!");
        response.sendRedirect("purchaseRequestList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
