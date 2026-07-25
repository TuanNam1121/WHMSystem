package com.swp.whmsystem.controller.purchaseRequest;

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

@WebServlet(name = "ManagerPurchaseRequestList", urlPatterns = {"/managerPurchaseRequestList"})
public class ManagerPurchaseRequestList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.APPROVE_REJECT_PURCHASE_REQUEST,
                "You don't have permission to manage purchase requests!")) {
            return;
        }

        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();

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

        int totalRequests = purchaseRequestDAO.countPurchaseItem(0, code, status, dateStr);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalRequests / pageSize));
        page = Math.min(page, totalPages);

        List<PurchaseRequest> purchaseRequests = purchaseRequestDAO.searchPurchaseItem(0, code, status, dateStr, sort, pageSize, page);

        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("purchaseList", purchaseRequests);
        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/managerPurchaseRequestList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Manager Purchase Request List";
    }
}
