package com.swp.whmsystem.controller.category;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;

import java.util.*;

@WebServlet(name = "CategoryList", urlPatterns = {"/categoryList"})
public class CategoryList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> searchedCategoryList = new ArrayList<>();
        List<Category> allCategoryList = categoryDAO.getAllCategory();

        String categoryIdRaw = request.getParameter("categoryId");
        String activeRaw = request.getParameter("active");
        String sortBy = request.getParameter("sortBy");
        if (categoryIdRaw == null
                && activeRaw == null
                && sortBy == null) {
            searchedCategoryList = categoryDAO.getAllCategory();
        } else {
            int categoryId = -1;
            int brandId = -1;

            if (categoryIdRaw != null && !categoryIdRaw.trim().isEmpty()) {
                try {
                    categoryId = Integer.parseInt(categoryIdRaw.trim());
                } catch (NumberFormatException e) {
                    categoryId = -1;
                }
            }

            if (activeRaw != null && !activeRaw.trim().isEmpty()) {
                try {
                    brandId = Integer.parseInt(activeRaw.trim());
                } catch (NumberFormatException e) {
                    brandId = -1;
                }
            }
            searchedCategoryList = categoryDAO.searchCategory(categoryId, brandId, sortBy);

        }


        session.setAttribute("searchedCategoryList", searchedCategoryList);
        session.setAttribute("allCategoryList", allCategoryList);

        request.getRequestDispatcher("WEB-INF/view/category/categoryList.jsp").forward(request, response);
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


