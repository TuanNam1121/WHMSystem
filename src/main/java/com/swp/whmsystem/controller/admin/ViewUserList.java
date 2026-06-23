/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ViewUserList", urlPatterns = {"/ViewUserList"})
public class ViewUserList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        String roleId = request.getParameter("roleId");
        
        int page = 1;
        int pageSize = 10;
        
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }
        if (pageSizeParam != null) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException ignored) {}
        }
        
        int offset = (page - 1) * pageSize;

        UserDAO user = new UserDAO();
        RoleDAO role = new RoleDAO();
        List<User> userList = user.searchUserPaginated(keyword, roleId, sortBy, offset, pageSize);
        List<Role> roleList = role.getAllRoleToAssign();
        
        int totalRecords = user.countUsers(keyword, roleId);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("roleList", roleList);
        request.setAttribute("roleDao", role);
        request.setAttribute("userlist", userList);
        
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("roleId", roleId);
        
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("WEB-INF/view/admin/viewUserList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}

