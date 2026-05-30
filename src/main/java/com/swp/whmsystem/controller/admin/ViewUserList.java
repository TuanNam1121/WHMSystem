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


/**
 *
 * @author Admin
 */
@WebServlet(name = "ViewUserList", urlPatterns = {"/ViewUserList"})
public class ViewUserList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        String roleId = request.getParameter("roleId");
        
        UserDAO user = new UserDAO();
        RoleDAO role = new RoleDAO();
        List<User> userList = new ArrayList<>();
        List<Role> roleList = role.getAllRoleToAssign();
        if(keyword == null && sortBy == null && roleId == null){
            userList = user.getAllUsers();
        }
        else{
            userList = user.searchUser(keyword, roleId, sortBy);
        }
        request.setAttribute("roleList", roleList);
        request.setAttribute("roleDao", role);
        request.setAttribute("userlist", userList);
        request.getRequestDispatcher("view/UserList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
