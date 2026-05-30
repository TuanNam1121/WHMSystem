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

    @Override
    public void init() {
        unitDao = new UnitDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Unit> units = unitDao.getUnitsByFilter(status);

        request.setAttribute("units", units);
        request.getRequestDispatcher("view/viewUnitList.jsp").forward(request, response);
    }
}