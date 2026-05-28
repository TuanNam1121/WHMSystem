package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.UnitDAO;
import com.swp.whmsystem.model.Unit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewUnitList", urlPatterns = {"/ViewUnitList"})
public class ViewUnitList extends HttpServlet {
    private UnitDAO unitDao;
    private static final int PAGE_SIZE = 8;

    @Override
    public void init() {
        unitDao = new UnitDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        int totalUnits = unitDao.count();
        int totalPages = (int) Math.ceil((double) totalUnits / PAGE_SIZE);

        List<Unit> units = unitDao.getUnitsByPage(page, PAGE_SIZE);

        request.setAttribute("units", units);
        request.setAttribute("pageNo", page);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ViewUnitList.jsp").forward(request, response);
    }
}