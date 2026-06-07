package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.utils.InputStandization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CreatePurchaseRequest", urlPatterns = {"/createPurchaseRequest"})
public class CreatePurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        com.swp.whmsystem.dal.ProductDAO productDAO = new com.swp.whmsystem.dal.ProductDAO();
        java.util.List<com.swp.whmsystem.model.Product> productList = productDAO.getProductList();
        
        request.setAttribute("productList", productList);
        
        request.getRequestDispatcher("WEB-INF/view/product/createPurchaseRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

