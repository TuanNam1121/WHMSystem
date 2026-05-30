/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import java.io.IOException;

import com.swp.whmsystem.dal.RomDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddRom", urlPatterns = {"/AddRom"})
public class AddRom extends HttpServlet {
    private RomDAO romDao;

    @Override
    public void init() {
        romDao = new RomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/addRom.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String size = request.getParameter("size");
        String active = request.getParameter("active");

        romDao.insertRom(size, active != null);
        response.sendRedirect("ViewRomList");
    }
}