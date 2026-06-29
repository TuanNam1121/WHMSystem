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

@WebServlet(name = "RamList", urlPatterns = {"/RamList"})
public class RamList extends HttpServlet {
    private RamDAO ramDao;

    @Override
    public void init() {
        ramDao = new RamDAO();
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
        
        List<Ram> rams = ramDao.getRamsByFilter(status, search, offset, pageSize);
        int totalRecords = ramDao.countRamsByFilter(status, search);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("rams", rams);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("WEB-INF/view/product/ramList.jsp").forward(request, response);
    }
}
