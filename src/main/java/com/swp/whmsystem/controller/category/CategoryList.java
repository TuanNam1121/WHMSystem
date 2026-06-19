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

        String pageRaw = request.getParameter("page");
        int page = 1;
        int pageSize = 10;
        
        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageRaw.trim());
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String categoryIdRaw = request.getParameter("categoryId");
        String activeRaw = request.getParameter("active");
        String sortBy = request.getParameter("sortBy");
        String keyword = request.getParameter("keyword");
        
        int categoryId = -1;
        int active = -1;

        if (categoryIdRaw != null && !categoryIdRaw.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdRaw.trim());
            } catch (NumberFormatException e) {
                categoryId = -1;
            }
        }

        if (activeRaw != null && !activeRaw.trim().isEmpty()) {
            try {
                active = Integer.parseInt(activeRaw.trim());
            } catch (NumberFormatException e) {
                active = -1;
            }
        }
        
        int totalRecords = categoryDAO.getTotalCategory(categoryId, active, keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        } else if (page < 1) {
            page = 1;
        }
        
        int offset = (page - 1) * pageSize;

        searchedCategoryList = categoryDAO.searchCategory(categoryId, active, keyword, sortBy, offset, pageSize);

        session.setAttribute("searchedCategoryList", searchedCategoryList);
        session.setAttribute("allCategoryList", allCategoryList);
        
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("categoryIdStr", categoryIdRaw != null ? categoryIdRaw : "");
        request.setAttribute("activeStr", activeRaw != null ? activeRaw : "");
        request.setAttribute("sortByStr", sortBy != null ? sortBy : "");
        request.setAttribute("keywordStr", keyword != null ? keyword : "");

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


