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
        request.getRequestDispatcher("view/view/addCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = InputStandization.validateName(request.getParameter("categoryName"));
        String description = request.getParameter("description");

        CategoryDAO categoryDAO = new CategoryDAO();
        if (categoryDAO.getCategoryByName(categoryName) != null) {
            String error = "Category name has already exsisted! Please input another one!";
            request.setAttribute("error", error);
            request.getRequestDispatcher("view/view/addCategory.jsp").forward(request, response);
            return;
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(description);

        if (categoryDAO.addNewCategory(category)) {
            response.sendRedirect("ViewCategoryList");
        } else {
            String message = "Đã xảy ra lỗi!";
            request.setAttribute("error", message);
            request.getRequestDispatcher("view/view/addCategory.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
