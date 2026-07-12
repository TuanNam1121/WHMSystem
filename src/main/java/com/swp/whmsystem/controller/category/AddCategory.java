package com.swp.whmsystem.controller.category;

import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.InputStandization;
import com.swp.whmsystem.utils.PermissionConstants;
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
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_CATEGORY,
                "You don't have permission to add a category!")) {
            return;
        }
        request.getRequestDispatcher("WEB-INF/view/category/addCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_CATEGORY,
                "You don't have permission to add a category!")) {
            return;
        }
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");

        if (InputStandization.validateInput(categoryName)) {
            categoryName = InputStandization.validateName(categoryName);
        } else {
            String error = "Please do not input space!";
            request.setAttribute("error", error);
            request.getRequestDispatcher("WEB-INF/view/category/addCategory.jsp").forward(request, response);
            return;
        }

        if (!InputStandization.validateInput(description)) {
            String error = "Please do not input space!";
            request.setAttribute("error", error);
            request.getRequestDispatcher("WEB-INF/view/category/addCategory.jsp").forward(request, response);
            return;
        }

        CategoryDAO categoryDAO = new CategoryDAO();
        if (categoryDAO.getCategoryByName(categoryName) != null) {
            String error = "Category name has already exsisted! Please input another one!";
            request.setAttribute("error", error);
            request.getRequestDispatcher("WEB-INF/view/category/addCategory.jsp").forward(request, response);
            return;
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(description);

        if (categoryDAO.addNewCategory(category)) {
            response.sendRedirect("categoryList");
        } else {
            String message = "Error occured! Please try again!";
            request.setAttribute("error", message);
            request.getRequestDispatcher("WEB-INF/view/category/addCategory.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

