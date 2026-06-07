/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UpdateUserInformation extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        try {
            int id = Integer.parseInt(id_raw);
            UserDAO userDao = new UserDAO();
            RoleDAO roleDao = new RoleDAO();
            List<Role> list = roleDao.getAllRoleToAssign();
            User user = userDao.getUserFromId(id);
            request.setAttribute("act", "update");
            if (user == null) {
                request.setAttribute("error", "User not found.");
                request.getRequestDispatcher("ViewUserList").forward(request, response);
                return;
            }
            request.setAttribute("u", user);
            request.setAttribute("roleDao", roleDao);
            request.setAttribute("rolelist", list);
            request.getRequestDispatcher("WEB-INF/view/admin/userDetail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            String message = ex.getMessage();
            request.setAttribute("error", message);
            request.getRequestDispatcher("WEB-INF/view/admin/viewUserList.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDao = new UserDAO();
        RoleDAO roleDao = new RoleDAO();
        List<Role> list = roleDao.getAllRoleToAssign();

        request.setAttribute("act", "update");
        request.setAttribute("roleDao", roleDao);
        request.setAttribute("rolelist", list);

        Integer id = UserFormValidation.parsePositiveInt(request.getParameter("id"));
        String userName = UserFormValidation.trimToNull(request.getParameter("username"));
        String fullname = UserFormValidation.trimToNull(request.getParameter("fullname"));
        Integer roleId = UserFormValidation.parsePositiveInt(request.getParameter("role"));
        String phone = UserFormValidation.trimToNull(request.getParameter("phone"));
        String email = UserFormValidation.trimToNull(request.getParameter("email"));
        String gender = UserFormValidation.trimToNull(request.getParameter("gender"));
        boolean isActive = "true".equals(request.getParameter("active"));
        System.out.println(roleId);
        List<String> errors = new ArrayList<>();
        if (id == null) {
            errors.add("Invalid user id.");
        }
        if (!UserFormValidation.isValidUsername(userName)) {
            errors.add("Username is required (3-30 chars, letters/numbers only).");
        }
        if (!UserFormValidation.isValidFullName(fullname)) {
            errors.add("Full name is required (2-60 chars).");
        }
        if (roleId == null) {
            errors.add("Role is required.");
        }
        if (!UserFormValidation.isValidPhone(phone)) {
            errors.add("Phone must be 9-15 digits.");
        }
        if (!UserFormValidation.isValidEmail(email)) {
            errors.add("Valid email is required.");
        }
        if (!UserFormValidation.isValidGender(gender)) {
            errors.add("Gender is required.");
        }

        if (errors.isEmpty()) {
            if (userDao.existsByUsernameExceptUserId(userName, id)) {
                errors.add("Username already exists.");
            }
            if (userDao.existsByEmailExceptUserId(email, id)) {
                errors.add("Email already exists.");
            }
        }

        User i = new User(id == null ? 0 : id, userName, fullname, roleId == null ? 0 : roleId, phone, email, gender, isActive);
        request.setAttribute("u", i);

        if (!errors.isEmpty()) {
            request.setAttribute("error", String.join("<br/>", errors));
            request.getRequestDispatcher("WEB-INF/view/user/userDetail.jsp").forward(request, response);
            return;
        }

        if (userDao.updateUserInformation(i)) {
            response.sendRedirect("ViewUserList");
        } else {
            String message = "Đã xảy ra lỗi !";
            request.setAttribute("error", message);
            request.getRequestDispatcher("WEB-INF/view/user/userDetail.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

