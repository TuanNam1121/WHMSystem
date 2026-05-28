/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.home;

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
    private static final int PAGE_SIZE = 8;

    @Override
    public void init() {
        chipDao = new ChipDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        int totalChips = chipDao.count();
        int totalPages = (int) Math.ceil((double) totalChips / PAGE_SIZE);

        List<Chip> chips = chipDao.getChipsByPage(page, PAGE_SIZE);

        request.setAttribute("chips", chips);
        request.setAttribute("pageNo", page);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ViewChipList.jsp").forward(request, response);
    }
}
