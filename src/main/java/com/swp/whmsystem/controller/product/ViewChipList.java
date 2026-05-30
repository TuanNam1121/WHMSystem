/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import java.io.IOException;
import java.util.List;

import com.swp.whmsystem.dal.ChipDAO;
import com.swp.whmsystem.model.Chip;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewChipList", urlPatterns = {"/ViewChipList"})
public class ViewChipList extends HttpServlet {
    private ChipDAO chipDao;

    @Override
    public void init() {
        chipDao = new ChipDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Chip> chips = chipDao.getChipsByFilter(status);

        request.setAttribute("chips", chips);
        request.setAttribute("status", status);
        request.getRequestDispatcher("view/viewChipList.jsp").forward(request, response);
    }
}
