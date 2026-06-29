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

@WebServlet(name = "StorageList", urlPatterns = {"/StorageList"})
public class RomList extends HttpServlet {
    private RomDAO romDao;

    @Override
    public void init() {
        romDao = new RomDAO();
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
        
        List<Rom> roms = romDao.getRomsByFilter(status, search, offset, pageSize);
        int totalRecords = romDao.countRomsByFilter(status, search);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("roms", roms);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("WEB-INF/view/product/romList.jsp").forward(request, response);
    }
}
