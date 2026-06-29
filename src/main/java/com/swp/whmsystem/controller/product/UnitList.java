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

@WebServlet(name = "UnitList", urlPatterns = {"/UnitList"})
public class UnitList extends HttpServlet {
    private UnitDAO unitDao;

    @Override
    public void init() {
        unitDao = new UnitDAO();
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
        
        List<Unit> units = unitDao.getUnitsByFilter(status, search, offset, pageSize);
        int totalRecords = unitDao.countUnitsByFilter(status, search);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("units", units);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("WEB-INF/view/product/unitList.jsp").forward(request, response);
    }
}
