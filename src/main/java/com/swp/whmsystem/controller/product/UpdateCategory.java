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

@WebServlet(name = "UpdateCategory", urlPatterns = {"/updateCategory"})
public class UpdateCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        int id = Integer.parseInt(id_raw);
        CategoryDAO categoryDAO = new CategoryDAO();
        Category c = categoryDAO.getCategoryById(id);
        request.setAttribute("category", c);
        request.getRequestDispatcher("view/updateCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = InputStandization.validateName(request.getParameter("categoryName"));
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("categoryid"));
        String isActive = request.getParameter("isActive");

        CategoryDAO categoryDAO = new CategoryDAO();
        if (categoryDAO.getCategoryByName(categoryName) != null) {
            String error = "Category name has already exsisted! Please input another one!";
            request.getRequestDispatcher("CategoryDetail.jsp").forward(request, response);
        }

        Category category = new Category();
        category.setCategoryId(id);
        category.setName(categoryName);
        category.setDescription(description);
        if (isActive.equals("true"))
            category.setIsActive(true);
        else if (isActive.equals("false"))
            category.setIsActive(false);

        if (category.isIsActive() == false) {
            if (!categoryDAO.deactiveCategory(category.getCategoryId())) {
                String message = "Đã xảy ra lỗi khi deactive danh mục này!";
                request.setAttribute("error", message);
                request.getRequestDispatcher("CategoryDetail.jsp").forward(request, response);
            }
        }

        if (categoryDAO.updateCategory(category)) {
            response.sendRedirect("ViewCategoryList");
        } else {
            String message = "Đã xảy ra lỗi!";
            request.setAttribute("error", message);
            request.getRequestDispatcher("CategoryDetail.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
