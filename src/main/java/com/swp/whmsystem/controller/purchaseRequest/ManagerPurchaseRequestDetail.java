package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.*;
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
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@WebServlet(name = "ManagerPurchaseRequestDetail", urlPatterns = {"/managerPurchaseRequestDetail"})
public class ManagerPurchaseRequestDetail extends HttpServlet {

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

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("managerPurchaseRequestList");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            PurchaseRequestDAO prDao = new PurchaseRequestDAO();
            PurchaseItemDAO piDao = new PurchaseItemDAO();
            ProductDAO pDao = new ProductDAO();
            UserDAO userDao = new UserDAO();

            PurchaseRequest pr = prDao.getPurchaseRequestById(id);
            if (pr == null) {
                response.sendRedirect("managerPurchaseRequestList");
                return;
            }

            User salesman = userDao.getUserFromId(pr.getCreatedBy());
            List<PurchaseItem> items = piDao.getItemsByPurchaseRequestId(id);

            long totalAmount = 0;
            List<Product> productList = new ArrayList<>();
            for (PurchaseItem item : items) {
                Product p = pDao.getProductFromId(item.getProductId());
                productList.add(p);
                totalAmount += (long) item.getPrice() * item.getRequiredQty();
            }
            request.setAttribute("purchaseRequest", pr);
            request.setAttribute("salesman", salesman);
            request.setAttribute("purchaseItems", items);
            request.setAttribute("productList", productList);
            request.setAttribute("totalAmount", totalAmount);
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
        }

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/managerPurchaseRequestDetail.jsp").forward(request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        String button = request.getParameter("buttonSubmit");
        int purReqId = Integer.parseInt(request.getParameter("purchaseRequestId"));
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        PurchaseRequest purchaseRequest = purchaseRequestDAO.getPurchaseRequestById(purReqId);

        if (button.equalsIgnoreCase("Reject")) {
            purchaseRequest.setStatus("REJECTED");
            purchaseRequestDAO.updatePurchaseRequest(purchaseRequest);
            session.setAttribute("message", "Purchase request PR-" + purReqId + " has been rejected successfully.");
            response.sendRedirect("managerPurchaseRequestList");
        } else {
            purchaseRequest.setApprovedBy(user.getId());
            purchaseRequest.setStatus("APPROVED");
            purchaseRequestDAO.updatePurchaseRequest(purchaseRequest);
            session.setAttribute("message", "Purchase request PR-" + purReqId + " has been approved successfully.");
            response.sendRedirect("managerPurchaseRequestList");
        }
    }
}
