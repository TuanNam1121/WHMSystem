package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ImportRequestDetail", urlPatterns = {"/importRequestDetail"})
public class ImportRequestDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("goodReId"));
        GoodReceiptDAO goodReceiptDAO = new GoodReceiptDAO();
        GoodReceipt goodReceipt = goodReceiptDAO.getGoodReceiptById(id);
        if (goodReceipt != null) {
            PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
            PurchaseRequest purchaseRequest = purchaseRequestDAO.getPurchaseRequestById(goodReceipt.getPurchaseRequestId());
            UserDAO userDAO = new UserDAO();
            RoleDAO roleDAO = new RoleDAO();
            
            String approvedByRoleName = "Unknown";
            if (purchaseRequest != null) {
                User user = userDAO.getUserFromId(purchaseRequest.getApprovedBy());
                if (user != null) {
                    Role role = roleDAO.getRoleById(user.getRoleId());
                    if (role != null) {
                        approvedByRoleName = role.getRoleName();
                    }
                }
            }

            PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
            List<PurchaseItem> listItems = purchaseItemDAO.getItemsByPurchaseRequestId(goodReceipt.getPurchaseRequestId());

            ProductDAO productDAO = new ProductDAO();
            Map<Integer, Product> productMap = new HashMap<>();
            for (PurchaseItem item : listItems) {
                Product p = productDAO.getProductFromId(item.getProductId());
                if (p != null) {
                    productMap.put(item.getProductId(), p);
                }
            }

            request.setAttribute("goodReceipt", goodReceipt);
            request.setAttribute("approvedBy", approvedByRoleName);
            request.setAttribute("purchaseItems", listItems);
            request.setAttribute("productMap", productMap);
        }

        request.getRequestDispatcher("WEB-INF/view/import/importRequestDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int goodReId = Integer.parseInt(request.getParameter("goodReId"));
        GoodReceiptDAO goodReceiptDAO = new GoodReceiptDAO();
        GoodReceipt gr = goodReceiptDAO.getGoodReceiptById(goodReId);
        gr.setStatus("PENDING");
        goodReceiptDAO.updateGoodReceipt(gr);

        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        PurchaseRequest pr = purchaseRequestDAO.getPurchaseRequestById(gr.getPurchaseRequestId());
        pr.setStatus("PROCESSING");
        purchaseRequestDAO.updatePurchaseRequest(pr);
        request.getSession().setAttribute("message", "Accepted the import request with Id: " + goodReId);
        response.sendRedirect("importRequestList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}