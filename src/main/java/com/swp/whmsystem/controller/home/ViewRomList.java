package com.swp.whmsystem.controller.home;

import com.swp.whmsystem.dal.RomDAO;
import com.swp.whmsystem.model.Rom;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewRomList", urlPatterns = {"/ViewRomList"})
public class ViewRomList extends HttpServlet {
    private RomDAO romDao;
    private static final int PAGE_SIZE = 8;

    @Override
    public void init() {
        romDao = new RomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        int totalRoms = romDao.count();
        int totalPages = (int) Math.ceil((double) totalRoms / PAGE_SIZE);

        List<Rom> roms = romDao.getRomsByPage(page, PAGE_SIZE);

        request.setAttribute("roms", roms);
        request.setAttribute("pageNo", page);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ViewRomList.jsp").forward(request, response);
    }
}