package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.ModelDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "UpdateModelStatus", urlPatterns = {"/UpdateModelStatus"})
public class UpdateModelStatus extends HttpServlet {
    private ModelDAO modelDao;

    @Override
    public void init() {
        modelDao = new ModelDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean active = Boolean.parseBoolean(request.getParameter("active"));
        String status = request.getParameter("status");
        String brandId = request.getParameter("brandId");

        modelDao.updateModelStatus(id, active);

        String target = request.getContextPath() + "/ModelList";
        boolean hasQuery = false;
        if (brandId != null && !brandId.isEmpty()) {
            target += "?brandId=" + URLEncoder.encode(brandId, StandardCharsets.UTF_8);
            hasQuery = true;
        }
        if (status != null && !status.isEmpty()) {
            target += (hasQuery ? "&" : "?") + "status=" + URLEncoder.encode(status, StandardCharsets.UTF_8);
        }
        response.sendRedirect(target);
    }
}