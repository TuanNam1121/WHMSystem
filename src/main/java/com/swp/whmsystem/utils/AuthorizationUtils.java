package com.swp.whmsystem.utils;

import com.swp.whmsystem.dal.DBContext;
import com.swp.whmsystem.dal.RolePermissionDAO;
import com.swp.whmsystem.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationUtils {

    public static boolean checkAccess(HttpServletRequest request, HttpServletResponse response, String permissionName, String errorMessage) throws java.io.IOException, jakarta.servlet.ServletException {
        RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        if (!rolePermissionDAO.havePermission(
                (User) session.getAttribute("user"),permissionName)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/view/error/error-403.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
