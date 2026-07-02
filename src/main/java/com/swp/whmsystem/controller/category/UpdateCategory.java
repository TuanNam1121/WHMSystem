package com.swp.whmsystem.controller.category;

import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ProductDAO;
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

@WebServlet(name = "UpdateCategory", urlPatterns = {"/updateCategory"})
public class UpdateCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.UPDATE_CATEGORY,
                "You are not authorized to update category.")) {
            return;
        }
        String id_raw = request.getParameter("cateid");
        int id = Integer.parseInt(id_raw);
        CategoryDAO categoryDAO = new CategoryDAO();
        Category c = categoryDAO.getCategoryById(id);
        request.setAttribute("category", c);
        request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");

        int id = Integer.parseInt(request.getParameter("categoryid"));
        String isActive = request.getParameter("isActive");
        String message = "";

        Category category = new Category();
        category.setCategoryId(id);
        category.setName(categoryName);
        category.setDescription(description);
        category.setIsActive(isActive.equals("true"));

        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();

        if (InputStandization.validateInput(categoryName)) {
            categoryName = InputStandization.validateName(categoryName);
        } else {
            String error = "Please do not input space!";
            request.setAttribute("error", error);
            request.setAttribute("category", category);
            request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
            return;
        }

        if (!InputStandization.validateInput(description)) {
            String error = "Please do not input space!";
            request.setAttribute("error", error);
            request.setAttribute("category", category);
            request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
            return;
        }

        Category oldCategory = categoryDAO.getCategoryById(id);
        Category existingCategory = categoryDAO.getCategoryByName(categoryName);
        if (existingCategory != null && existingCategory.getCategoryId() != id) {
            String error = "Category name has already exsisted! Please input another one!";
            request.setAttribute("error", error);
            request.setAttribute("category", category);
            request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
            return;
        }

        if (!category.isIsActive() && category.isIsActive() != oldCategory.isIsActive()) {
            if (categoryDAO.isCategoryUsed(category.getCategoryId())) {
                String error = "Cannot disable this Category because it is currently used by a product.";
                request.setAttribute("error", error);
                request.setAttribute("category", category);
                request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
                return;
            }
            message = "Category deactivated: " + categoryName + " !";
        }

        if (category.isIsActive() && category.isIsActive() != oldCategory.isIsActive()) {
//            if (!categoryDAO.reactiveCategory(category.getCategoryId())) {
//                message = "Đã xảy ra lỗi khi reactive danh mục này!";
//                request.setAttribute("error", message);
//                request.setAttribute("category", category);
//                request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
//                return;
//            }
            message = "Reactived category " + categoryName + " ! Please double-check the related products in this category!";
        }

        if (categoryDAO.updateCategory(category)) {
            if (!message.equals(""))
                request.getSession().setAttribute("error", message);
            response.sendRedirect("categoryList");
        } else {
            message = "Đã xảy ra lỗi!";
            request.setAttribute("error", message);
            request.setAttribute("category", category);
            request.getRequestDispatcher("WEB-INF/view/category/updateCategory.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

