package com.swp.whmsystem.controller.home;

import com.swp.whmsystem.dal.RamDAO;
import com.swp.whmsystem.model.Ram;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewRamList", urlPatterns = {"/ViewRamList"})
public class ViewRamList extends HttpServlet {
    private RamDAO ramDao;
    private static final int PAGE_SIZE = 5;

    @Override
    public void init() {
        ramDao = new RamDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        int totalRams = ramDao.count();
        int totalPages = (int) Math.ceil((double) totalRams / PAGE_SIZE);

        List<Ram> rams = ramDao.getRamsByPage(page, PAGE_SIZE);

        request.setAttribute("rams", rams);
        request.setAttribute("pageNo", page);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ViewRamList.jsp").forward(request, response);
    }
}