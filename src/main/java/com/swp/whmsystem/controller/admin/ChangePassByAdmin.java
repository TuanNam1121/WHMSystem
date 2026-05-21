/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.api.EmailApi;
import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.swp.whmsystem.model.Request;
import org.mindrot.jbcrypt.BCrypt;

public class ChangePassByAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePassByAdmin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassByAdmin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO uDao = new UserDAO();
        RequestDAO rDao = new RequestDAO();
        String userId_raw = request.getParameter("userId");
        int userId = Integer.parseInt(userId_raw);
        User user = uDao.getUserFromId(userId);

        String newPass = request.getParameter("newPass");
        String cfNewPass = request.getParameter("cfNewPass");

        if (newPass.isEmpty() || cfNewPass.isEmpty()) {
            request.setAttribute("error", "Please fill out this field");
            request.setAttribute("userId", userId);
            request.getRequestDispatcher("ChangePassByAdmin.jsp").forward(request, response);
            return;
        }
        
        if (!cfNewPass.equals(newPass)) {
            request.setAttribute("error", "Confirm password does not match");
            request.setAttribute("userId", userId);
            request.getRequestDispatcher("ChangePassByAdmin.jsp").forward(request, response);
            return;
        }

        String hashedNewPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));

        boolean isUpdated = uDao.updateUserPassword(user.getId(),
                hashedNewPass);

        if (isUpdated) {
            user.setPassword(hashedNewPass);
            Request req = rDao.getLatestRequestByUserId(userId);
            req.setStatus("COMPLETED");
            rDao.updateRequestStatus(req.getRequestId(), req.getStatus());
            
            boolean isEmailSent = EmailApi.sendEmail(user.getEmail(), newPass);
            if (isEmailSent) {
                request.setAttribute("message", "Change password successfully and email sent!");
            } else {
                request.setAttribute("message", "Error occur in updating!");
            }
        } else {
            request.setAttribute("message", "Error! Please try again!");
        }
        request.getRequestDispatcher("AdminDashBoard").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
