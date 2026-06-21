package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.UnitDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "UpdateUnitStatus", urlPatterns = {"/UpdateUnitStatus"})
public class UpdateUnitStatus extends HttpServlet {
    private UnitDAO unitDao;

    @Override
    public void init() {
        unitDao = new UnitDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean active = Boolean.parseBoolean(request.getParameter("active"));
        String status = request.getParameter("status");

        if (!active && unitDao.isUnitUsed(id)) {
            request.getSession().setAttribute("error", "Cannot disable this Unit because it is currently used by a product.");
        } else {
            unitDao.updateUnitStatus(id, active);
        }

        String target = request.getContextPath() + "/UnitList";
        if (status != null && !status.isEmpty()) {
            target += "?status=" + URLEncoder.encode(status, StandardCharsets.UTF_8);
        }
        response.sendRedirect(target);
    }
    
}