package com.swp.whmsystem.filter;

import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.AuthorizationUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = { "/*" })
public class SidebarNotificationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (shouldLoadSidebarNotifications(request)) {
            OrderDAO orderDAO = new OrderDAO();
            PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
            request.setAttribute("pendingImportRequestCount",
                    purchaseRequestDAO.countApprovedAndIncompletedPurchaseRequest());
            request.setAttribute("newSaleOrderCount", orderDAO.countNewSaleOrders());

            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                User currentUser = (User) session.getAttribute("user");
                UserDAO userDAO = new UserDAO();
                User refreshedUser = userDAO.getUserFromId(currentUser.getId());
                if (refreshedUser != null) {
                    if (refreshedUser.isIsActive()) {
                        session.setAttribute("user", refreshedUser);
                        AuthorizationUtils.setSession(request);
                    } else {
                        session.invalidate();
                        response.sendRedirect(request.getContextPath() + "/login");
                        return;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean shouldLoadSidebarNotifications(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return false;
        }

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (!contextPath.isEmpty() && uri.startsWith(contextPath)) {
            uri = uri.substring(contextPath.length());
        }

        return !uri.startsWith("/assets/")
                && !uri.startsWith("/assests/")
                && !uri.startsWith("/images/")
                && !uri.endsWith(".css")
                && !uri.endsWith(".js")
                && !uri.endsWith(".png")
                && !uri.endsWith(".jpg")
                && !uri.endsWith(".jpeg")
                && !uri.endsWith(".svg")
                && !uri.endsWith(".woff")
                && !uri.endsWith(".woff2")
                && !uri.endsWith(".ttf");
    }
}
