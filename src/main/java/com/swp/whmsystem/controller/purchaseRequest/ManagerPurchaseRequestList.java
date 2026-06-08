package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.model.PurchaseRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerPurchaseRequestList", urlPatterns = {"/managerPurchaseRequestList"})
public class ManagerPurchaseRequestList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        List<PurchaseRequest> purchaseRequests = purchaseRequestDAO.getAllPurchaseRequest();
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
