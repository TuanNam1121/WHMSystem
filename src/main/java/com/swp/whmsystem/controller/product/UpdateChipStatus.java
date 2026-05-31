package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.ChipDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "UpdateChipStatus", urlPatterns = {"/UpdateChipStatus"})
public class UpdateChipStatus extends HttpServlet {
    private ChipDAO chipDao;

    @Override
    public void init() {
        chipDao = new ChipDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean active = Boolean.parseBoolean(request.getParameter("active"));
        String status = request.getParameter("status");

        chipDao.updateChipStatus(id, active);

        String target = request.getContextPath() + "/ChipList";
        if (status != null && !status.isEmpty()) {
            target += "?status=" + URLEncoder.encode(status, StandardCharsets.UTF_8);
        }
        response.sendRedirect(target);
    }
}