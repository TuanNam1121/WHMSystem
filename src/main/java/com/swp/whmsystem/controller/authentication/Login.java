/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.authentication;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User account = dao.checkLogin(user, pass);
        
        if(account != null && dao.isActiveUser(account)){
            HttpSession session = request.getSession();
            session.setAttribute("user", account);
            response.sendRedirect("home");
        }
        else if (account != null && !dao.isActiveUser(account)) {
            request.setAttribute("error", "Your account is deative!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        else {
            request.setAttribute("error", "Username or Password is not correct!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
