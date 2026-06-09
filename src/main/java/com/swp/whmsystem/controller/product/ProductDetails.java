package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ProductDetails", urlPatterns = {"/productDetails"})
public class ProductDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductDAO productDAO = new ProductDAO();

        int productId = Integer.parseInt(request.getParameter("productId"));
        Product product = productDAO.getProductFromId(productId);


        session.setAttribute("product", product);
        request.getRequestDispatcher("WEB-INF/view/product/productDetails.jsp").forward(request, response);
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
