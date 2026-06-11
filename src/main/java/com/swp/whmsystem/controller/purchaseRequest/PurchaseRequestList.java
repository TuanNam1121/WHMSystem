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

        try {
            if (user == null) {
                response.sendRedirect("login");
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("login");
            return;
        }
        List<PurchaseRequest> purchaseRequests = purchaseRequestDAO.getAllPurchaseRequestForSaleman(user.getId());
        request.setAttribute("purchaseList", purchaseRequests);
        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/purchaseRequestList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int prId = Integer.parseInt(request.getParameter("id"));
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
        purchaseItemDAO.softDeletePurItemByPurReqId(prId);
        purchaseRequestDAO.softDeletePurchaseRequest(prId);

        HttpSession session = request.getSession();
        session.setAttribute("error", "Purchase Request with Id: " + prId + " has been deleted!");
        response.sendRedirect("purchaseRequestList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
