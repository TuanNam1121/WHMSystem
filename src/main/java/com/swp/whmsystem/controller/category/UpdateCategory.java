package com.swp.whmsystem.controller.category;

import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ProductDAO;
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
        String id_raw = request.getParameter("cateid");
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
        String message = "";

        Category category = new Category();
        category.setCategoryId(id);
        category.setName(categoryName);
        category.setDescription(description);
        category.setIsActive(isActive.equals("true"));

        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();

        Category oldCategory = categoryDAO.getCategoryById(id);
        Category existingCategory = categoryDAO.getCategoryByName(categoryName);
        if (existingCategory != null && existingCategory.getCategoryId() != id) {
            String error = "Category name has already exsisted! Please input another one!";
            request.setAttribute("error", error);
            request.setAttribute("category", category);
            request.getRequestDispatcher("view/updateCategory.jsp").forward(request, response);
            return;
        }

        if (!category.isIsActive() && category.isIsActive() != oldCategory.isIsActive()) {
//            if (productDAO.getProductFromCategoryId(category.getCategoryId()) != null) {
//                if (!categoryDAO.deactiveCategory(category.getCategoryId())) {
//                    message = "Đã xảy ra lỗi khi deactive danh mục này!";
//                    request.setAttribute("error", message);
//                    request.setAttribute("category", category);
//                    request.getRequestDispatcher("view/updateCategory.jsp").forward(request, response);
//                    return;
//                }
//                message = "Đã deactive danh mục " + categoryName + " ! Các sản phẩm thuộc danh mục này đã inactive!";
//            } else {
//                message = "Đã deactive danh mục " + categoryName + " !";
//            }
            message = "Đã deactive danh mục " + categoryName + " !";
        }

        if (category.isIsActive() && category.isIsActive() != oldCategory.isIsActive()) {
//            if (!categoryDAO.reactiveCategory(category.getCategoryId())) {
//                message = "Đã xảy ra lỗi khi reactive danh mục này!";
//                request.setAttribute("error", message);
//                request.setAttribute("category", category);
//                request.getRequestDispatcher("view/updateCategory.jsp").forward(request, response);
//                return;
//            }
            message = "Đã reactive danh mục " + categoryName + " ! Hãy kiểm tra lại các sản phẩm liên quan danh mục này!";
        }

        if (categoryDAO.updateCategory(category)) {
            if (!message.equals(""))
                request.getSession().setAttribute("error", message);
            response.sendRedirect("categoryList");
        } else {
            message = "Đã xảy ra lỗi!";
            request.setAttribute("error", message);
            request.setAttribute("category", category);
            request.getRequestDispatcher("view/updateCategory.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
