/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;

import java.io.IOException;
import java.io.PrintWriter;

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
        int role;
        try {
            role = Integer.parseInt(roleid);
        } catch (Exception e) {
            role = 0;
        }

        if (keyword != null && !keyword.trim().equals("")) {
            if (role != 0) {
                request.setAttribute("permissions", pd.searchPermissionByName(keyword, role));
            } else {
                request.setAttribute("permissions", pd.searchPermissionByName(keyword, 0));
            }
            request.setAttribute("roleid", role);
            request.getRequestDispatcher("WEB-INF/view/admin/viewPermissionList.jsp").forward(request, response);
            return;
        } else if (keyword == null || keyword.trim().equals("")) {
            if (role != 0) {
                request.setAttribute("roleid", role);
                request.setAttribute("permissions", pd.searchPermissionByName("", role));
                request.getRequestDispatcher("WEB-INF/view/admin/viewPermissionList.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("roleid", role);
        request.setAttribute("permissions", pd.getAllPermission());
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

