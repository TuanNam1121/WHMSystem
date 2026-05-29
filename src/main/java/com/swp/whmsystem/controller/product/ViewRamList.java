package com.swp.whmsystem.controller.product;

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

    @Override
    public void init() {
        ramDao = new RamDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");
        List<Ram> rams = ramDao.getRamsByFilter(keyword, status);

        request.setAttribute("rams", rams);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);
        request.getRequestDispatcher("ViewRamList.jsp").forward(request, response);
    }
}