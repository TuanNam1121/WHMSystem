/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.authentication;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;  
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="forgetPassword", urlPatterns={"/forgetPassword"})
public class ForgetPassword extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgetPassword</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgetPassword at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/authentication/forgetPassword.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        UserDAO daoU = new UserDAO();
        RequestDAO daoR = new RequestDAO();
        
        User user = daoU.getUser(username, email);
        if (user == null) {
            request.setAttribute("error", "Username or Email is invalid! Please try again!");
            request.getRequestDispatcher("WEB-INF/view/authentication/forgetPassword.jsp").forward(request, response);
            return;
        }
        
        Request req = new Request();
        req.setUserId(user.getId());
        req.setStatus("NEW");
        req.setMessage("ResetPassword");
        daoR.addNewRequest(req);
        
        request.setAttribute("error", "Request sent! Please wait for an email!");
        request.getRequestDispatcher("WEB-INF/view/authentication/login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

