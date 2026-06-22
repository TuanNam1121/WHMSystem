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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

            Map<Integer, Product> productMap = new HashMap<>();
            for (PurchaseItem item : items) {
                Product p = pDao.getProductFromId(item.getProductId());
                if (p != null) {
                    productMap.put(item.getProductId(), p);
                }
                request.setAttribute("purchaseRequest", pr);
                request.setAttribute("salesman", salesman);
                request.setAttribute("purchaseItems", items);
                request.setAttribute("productMap", productMap);
            }
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/view/purchaseRequest/managerPurchaseRequestDetail.jsp").forward(request,
                    response);
        }

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/managerPurchaseRequestDetail.jsp").forward(request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String button = request.getParameter("buttonSubmit");
        int purReqId = Integer.parseInt(request.getParameter("purchaseRequestId"));
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        PurchaseRequest purchaseRequest = purchaseRequestDAO.getPurchaseRequestById(purReqId);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            if (user == null) {
                response.sendRedirect("login");
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("login");
            return;
        }

        if (button.equalsIgnoreCase("Reject")) {
            purchaseRequest.setStatus("REJECTED");
            purchaseRequestDAO.updatePurchaseRequest(purchaseRequest);
            response.sendRedirect("managerPurchaseRequestList");
        } else {
            purchaseRequest.setApprovedBy(user.getId());
            purchaseRequest.setStatus("APPROVED");
            purchaseRequestDAO.updatePurchaseRequest(purchaseRequest);
            response.sendRedirect("managerPurchaseRequestList");
        }
    }
}
