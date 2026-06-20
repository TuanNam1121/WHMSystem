package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "ViewRoleList", urlPatterns = {"/ViewRoleList"})
public class ViewRoleList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RoleDAO roleDAO = new RoleDAO();

        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        
        int page = 1;
        int pageSize = 10;
        
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        int offset = (page - 1) * pageSize;
        
        List<Role> roleList = roleDAO.findRoleByFilter(keyword, sortBy, offset, pageSize);
        int totalRecords = roleDAO.countRoleByFilter(keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("rolelist", roleList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("WEB-INF/view/admin/viewRoleList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}

