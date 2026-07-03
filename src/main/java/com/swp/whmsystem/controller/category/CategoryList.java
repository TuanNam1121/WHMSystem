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
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import java.util.*;

@WebServlet(name = "CategoryList", urlPatterns = {"/categoryList"})
public class CategoryList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_CATEGORY,
                "You don't have permission to view the category list!")) {
            return;
        }

        HttpSession session = request.getSession();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> searchedCategoryList = new ArrayList<>();
        String keyword = request.getParameter("keyword");
        String isActiveRaw = request.getParameter("isActive");
        String sortBy = request.getParameter("sortBy");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int isActive = -1;
        int pageSize = 10;
        int page = 1;

        if (isActiveRaw != null && !isActiveRaw.trim().isEmpty()) {
            try {
                int parsedStatus = Integer.parseInt(isActiveRaw.trim());
                if (parsedStatus == 0 || parsedStatus == 1) {
                    isActive = parsedStatus;
                }
            } catch (NumberFormatException e) {
                isActive = -1;
            }
        }

        if (pageSizeRaw != null && !pageSizeRaw.trim().isEmpty()) {
            try {
                int parsedPageSize = Integer.parseInt(pageSizeRaw.trim());
                if (parsedPageSize > 0 && parsedPageSize <= 100) {
                    pageSize = parsedPageSize;
                }
            } catch (NumberFormatException ignored) {
                pageSize = 10;
            }
        }

        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageRaw.trim()));
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        int totalCategories = categoryDAO.countCategories(keyword, isActive);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalCategories / pageSize));
        page = Math.min(page, totalPages);

        searchedCategoryList = categoryDAO.searchCategory(
                keyword, isActive, sortBy, pageSize, page
        );

        session.setAttribute("searchedCategoryList", searchedCategoryList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("focusTable",
                keyword != null || isActiveRaw != null
                        || sortBy != null || pageSizeRaw != null || pageRaw != null);
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
