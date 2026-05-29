package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.utils.InputStandization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AddCategory", urlPatterns = {"/addCategory"})

public class AddCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = "new";
        request.setAttribute("act", action);
        request.getRequestDispatcher("addcategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = "new";
        request.setAttribute("act", action);

        String categoryName = InputStandization.validateName(request.getParameter("categoryName"));
        String description = request.getParameter("description");

        CategoryDAO categoryDAO = new CategoryDAO();
        if (categoryDAO.getCategoryByName(categoryName) != null) {
            String error = "Category name has already exsisted! Please input another one!";
            request.getRequestDispatcher("addcategory.jsp").forward(request, response);
        }

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setDescription(description);

        if (categoryDAO.addNewCategory(category)) {
            response.sendRedirect("categorylist");
        } else {
            String message = "Đã xảy ra lỗi!";
            request.setAttribute("error", message);
            request.getRequestDispatcher("categorylist.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
