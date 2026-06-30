package com.swp.whmsystem.utils;

import com.swp.whmsystem.dal.RolePermissionDAO;
import com.swp.whmsystem.model.Permission;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizationUtils {
    public static void setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        RolePermissionDAO rpDAO = new RolePermissionDAO();
        List<Permission> permissionsList = rpDAO.getPermissionByRole(user.getRoleId());
        Set<String> permissions = permissionsList.stream().map(Permission::getPermissionName)
                .collect(Collectors.toSet());
        session.setAttribute("userPermissions", permissions);
    }

    public static boolean hasPermission(HttpServletRequest request, String permissionName) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Set<String> permissions = (Set<String>) session.getAttribute("userPermissions");
        return permissions.contains(permissionName);
    }

    public static boolean checkAccess(HttpServletRequest request, HttpServletResponse response, String permissionName,
            String errorMessage) throws IOException, ServletException {
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
