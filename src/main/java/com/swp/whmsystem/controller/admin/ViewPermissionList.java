/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ViewPermissionList extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PermissionDAO pd = new PermissionDAO();
        RoleDAO rd = new RoleDAO();
        request.setAttribute("roles", rd.getAllRole());

        String keyword = request.getParameter("keyword");
        String roleid = request.getParameter("roleid");
        int role = 0;
        if (roleid != null && !roleid.trim().isEmpty()) {
            try {
                role = Integer.parseInt(roleid);
            } catch (Exception e) {
                role = 0;
            }
        }

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
        
        List<Permission> permissions = pd.getPermissionPaginated(keyword, role, offset, pageSize);
        int totalRecords = pd.countPermission(keyword, role);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("keyword", keyword);
        request.setAttribute("roleid", role);
        request.setAttribute("permissions", permissions);
        
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("WEB-INF/view/admin/viewPermissionList.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

