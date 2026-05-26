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

/**
 * Servlet for handling the Role Management View
 */
@WebServlet(name = "ViewRoleList", urlPatterns = {"/ViewRoleList"})
public class ViewRoleList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RoleDAO roleDAO = new RoleDAO();

        String keyword= request.getParameter("keyword");
        String sortBy=request.getParameter("sortBy");
        List<Role> roleList = roleDAO.findRoleByFilter(keyword,sortBy);
        request.setAttribute("rolelist", roleList);
        request.getRequestDispatcher("ViewRoleList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
