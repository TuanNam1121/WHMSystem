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

@WebServlet(name = "ChipList", urlPatterns = {"/ChipList"})
public class ChipList extends HttpServlet {
    private ChipDAO chipDao;

    @Override
    public void init() {
        chipDao = new ChipDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        
        int page = 1;
        int pageSize = 10;
        
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        int offset = (page - 1) * pageSize;
        
        List<Chip> chips = chipDao.getChipsByFilter(status, search, offset, pageSize);
        int totalRecords = chipDao.countChipsByFilter(status, search);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("chips", chips);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("WEB-INF/view/product/chipList.jsp").forward(request, response);
    }
}

