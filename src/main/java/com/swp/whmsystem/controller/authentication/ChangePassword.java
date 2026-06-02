
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
            response.sendRedirect("view/login.jsp");
            return;
        }

        request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("view/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        UserDAO uDao = new UserDAO();

        String currPass = request.getParameter("currentPass");
        String newPass = request.getParameter("newPass");
        String cfNewPass = request.getParameter("cfNewPass");

        String currentHashedInDB = uDao.getPasswordById(user.getId());
        if (currentHashedInDB == null) {
            request.setAttribute("error", "System error, user not found!");
            request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
            return;
        }

        if (!InputValidationUtil.isValidPassword(newPass)) {
            request.setAttribute("error", "New password must contain at least 1 uppercase, 1 digit and at least 6 characters!");
            request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
            return;
        }

        if (!BCrypt.checkpw(currPass, currentHashedInDB)) {
            request.setAttribute("error", "Incorrect current password!");
            request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
            return;
        }

        if (BCrypt.checkpw(newPass, currentHashedInDB)) {
            request.setAttribute("error", "New password cannot be the same as your current password!");
            request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
            return;
        }

        if (!newPass.equals(cfNewPass)) {
            request.setAttribute("error", "Confirm password does not match!");
            request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
            return;
        }

        String hashedNewPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
        boolean isUpdated = uDao.updateUserPassword(user.getId(), hashedNewPass);

        if (isUpdated) {
            user.setPassword(hashedNewPass);
            request.setAttribute("message", "Change password successfully!");
        } else {
            request.setAttribute("error", "Database error! Please try again!");
        }

        request.getRequestDispatcher("view/changePassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
