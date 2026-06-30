/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.authentication;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.AuthorizationUtils;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = { "/login" })
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/authentication/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();

        User account = userDAO.checkLogin(username, password);

        if (account != null && userDAO.isActiveUser(account)) {
            HttpSession session = request.getSession();
            User user = userDAO.getUserFullInformation(account.getId());
            String roleName = roleDAO.getRoleNameFromUserID(account.getId());
            session.setAttribute("roleName", roleName);
            session.setAttribute("user", user);
            response.sendRedirect("home");
            AuthorizationUtils.setSession(request);
        } else if (account != null && !userDAO.isActiveUser(account)) {
            request.setAttribute("error", "Your account is deactive!");
            request.getRequestDispatcher("WEB-INF/view/authentication/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Username or Password is not correct!");
            request.getRequestDispatcher("WEB-INF/view/authentication/login.jsp").forward(request, response);
        }
    }
}
