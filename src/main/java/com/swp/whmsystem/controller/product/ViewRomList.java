package com.swp.whmsystem.controller.product;

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

    @Override
    public void init() {
        romDao = new RomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Rom> roms = romDao.getRomsByFilter(status);

        request.setAttribute("roms", roms);
        request.setAttribute("status", status);
        request.getRequestDispatcher("view/RomList.jsp").forward(request, response);
    }
}