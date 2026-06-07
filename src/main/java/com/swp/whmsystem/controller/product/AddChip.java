/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import java.io.IOException;

import com.swp.whmsystem.dal.ChipDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddChip", urlPatterns = {"/AddChip"})
public class AddChip extends HttpServlet {
    private ChipDAO chipDao;

    @Override
    public void init() {
        chipDao = new ChipDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/product/addChip.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String active = request.getParameter("active");
        String chipName = name == null ? "" : name.trim();

        if (chipName.isEmpty()) {
            request.setAttribute("message", "Chip name is required");
            request.getRequestDispatcher("WEB-INF/view/product/addChip.jsp").forward(request, response);
            return;
        }

        if (chipDao.getChipByName(chipName) != null) {
            request.setAttribute("message", "Chip name already exists");
            request.getRequestDispatcher("WEB-INF/view/product/addChip.jsp").forward(request, response);
            return;
        }

        chipDao.insertChip(chipName, active != null);
        response.sendRedirect("ChipList");
    }
}

