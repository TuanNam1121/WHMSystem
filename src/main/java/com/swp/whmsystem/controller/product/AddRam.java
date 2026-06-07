/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import java.io.IOException;

import com.swp.whmsystem.dal.RamDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddRam", urlPatterns = {"/AddRam"})
public class AddRam extends HttpServlet {
    private RamDAO ramDao;

    @Override
    public void init() {
        ramDao = new RamDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/product/addRam.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String size = request.getParameter("size");
        String active = request.getParameter("active");
        String ramSize = size == null ? "" : size.trim();

        if (ramSize.isEmpty()) {
            request.setAttribute("message", "Ram size is required");
            request.getRequestDispatcher("WEB-INF/view/product/addRam.jsp").forward(request, response);
            return;
        }

        if (ramDao.getRamBySize(ramSize) != null) {
            request.setAttribute("message", "Ram size already exists");
            request.getRequestDispatcher("WEB-INF/view/product/addRam.jsp").forward(request, response);
            return;
        }

        ramDao.insertRam(ramSize, active != null);
        response.sendRedirect("RamList");
    }
}
