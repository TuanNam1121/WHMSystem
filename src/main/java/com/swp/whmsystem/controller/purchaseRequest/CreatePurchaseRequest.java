package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.InputStandization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreatePurchaseRequest", urlPatterns = {"/createPurchaseRequest"})
public class CreatePurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getProductList();
        request.setAttribute("productListForPurchase", productList);

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/createPurchaseRequest.jsp").forward(request, response);
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

