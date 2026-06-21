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
@WebServlet(name = "addNewUser", urlPatterns = {"/addNewUser"})
public class AddNewUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO roleDao = new RoleDAO();
        List<Role> list = roleDao.getAllRoleToAssign();
        String action = "new";
        request.setAttribute("act", action);
        request.setAttribute("roleDao", roleDao);
        request.setAttribute("rolelist", list);
        request.getRequestDispatcher("WEB-INF/view/admin/userDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDao = new UserDAO();
        RoleDAO roleDao = new RoleDAO();
        List<Role> list = roleDao.getAllRoleToAssign();
        String action = "new";
        request.setAttribute("act", action);
        request.setAttribute("roleDao", roleDao);
        request.setAttribute("rolelist", list);

        String userName = UserFormValidation.trimToNull(request.getParameter("username"));
        String fullname = UserFormValidation.trimToNull(request.getParameter("fullname"));
        String password = request.getParameter("password");
        Integer roleId = UserFormValidation.parsePositiveInt(request.getParameter("role"));
        String phone = UserFormValidation.trimToNull(request.getParameter("phone"));
        String email = UserFormValidation.trimToNull(request.getParameter("email"));
        String gender = UserFormValidation.trimToNull(request.getParameter("gender"));
        String firstname = userDao.getFirstnameFromFullname(fullname);
        String lastname = userDao.getLastnameFromFullname(fullname);

        List<String> errors = new ArrayList<>();
        if (!UserFormValidation.isValidUsername(userName)) {
            errors.add("Username is required (3-30 chars, letters/numbers only).");
        }
        if (!UserFormValidation.isValidFullName(fullname)) {
            errors.add("Full name is required (2-60 chars).");
        }
        if (!UserFormValidation.isValidPassword(password)) {
            errors.add("Password is required (min 6 chars).");
        }
        if (roleId == null) {
            errors.add("Role is required.");
        }
        if (!UserFormValidation.isValidPhone(phone)) {
            errors.add("Phone must be 9-15 digits");
        }
        if (!UserFormValidation.isValidEmail(email)) {
            errors.add("Valid email is required.");
        }
        if (!UserFormValidation.isValidGender(gender)) {
            errors.add("Gender is required.");
        }

        if (errors.isEmpty()) {
            if (userDao.existsByUsername(userName)) {
                errors.add("Username already exists.");
            }
            if (userDao.existsByEmail(email)) {
                errors.add("Email already exists.");
            }
        }

        User user = new User();
        user.setUserName(userName);
        user.setFullName(fullname);
        user.setRoleId(roleId == null ? 0 : roleId);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setIsActive(true);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        request.setAttribute("u", user);

        if (!errors.isEmpty()) {
            request.setAttribute("error", String.join("<br/>", errors));
            request.getRequestDispatcher("WEB-INF/view/admin/userDetail.jsp").forward(request, response);
            return;
        }

        user.setPassword(password);
        user.setRoleId(roleId);
        if (userDao.addNewUser(user)) {
            response.sendRedirect("ViewUserList");
        } else {
            String message = "Đã xảy ra lỗi !";
            request.setAttribute("error", message);
            request.getRequestDispatcher("WEB-INF/view/admin/userDetail.jsp").forward(request, response);
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

