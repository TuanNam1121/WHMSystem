package com.swp.whmsystem.utils;

import com.swp.whmsystem.dal.RolePermissionDAO;
import com.swp.whmsystem.model.Permission;
import com.swp.whmsystem.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorizationUtils {

    public static boolean hasPermission(HttpServletRequest request, String permissionName) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        Set<String> permissions = (Set<String>) session.getAttribute("userPermissions");
        if (permissions == null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                RolePermissionDAO rpDAO = new RolePermissionDAO();
                List<Permission> permissionsList = rpDAO.getPermissionByRole(user.getRoleId());
                permissions = new HashSet<>();
                if (permissionsList != null) {
                    for (Permission p : permissionsList) {
                        if (p.getPermissionName() != null) {
                            permissions.add(p.getPermissionName());
                        }
                    }
                }
                session.setAttribute("userPermissions", permissions);
            }
        }

        return permissions != null && permissions.contains(permissionName);
    }

    public static boolean checkAccess(HttpServletRequest request, HttpServletResponse response, String permissionName, String errorMessage) throws java.io.IOException, jakarta.servlet.ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (!hasPermission(request, permissionName)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/view/error/error-403.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
