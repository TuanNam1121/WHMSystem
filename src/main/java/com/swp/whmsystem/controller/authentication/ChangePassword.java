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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "ChangePassword", urlPatterns = {"/changePassword"})
public class ChangePassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        UserDAO uDao = new UserDAO();

        String currPass = request.getParameter("currentPass");
        String newPass = request.getParameter("newPass");
        String cfNewPass = request.getParameter("cfNewPass");

        if (currPass == null || newPass == null || newPass.trim().isEmpty()) {
            request.setAttribute("error", "Passwords cannot be empty!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        if (!cfNewPass.equals(newPass)) {
            request.setAttribute("error", "Confirm password does not match");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        String currentHashedInDB = uDao.getPasswordById(user.getId());

        if (currentHashedInDB == null) {
            request.setAttribute("error", "System error, please try again!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        if (!BCrypt.checkpw(currPass, currentHashedInDB)) {
            request.setAttribute("error", "Incorrect current password !");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        String hashedNewPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));

        boolean isUpdated = uDao.updateUserPassword(user.getId(),
                hashedNewPass);

        if (isUpdated) {
            user.setPassword(hashedNewPass);
            request.setAttribute("message", "Change password successfully !");
        } else {
            request.setAttribute("error", "Error ! Please try again !");
        }
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
