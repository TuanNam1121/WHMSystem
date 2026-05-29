package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.ModelDAO;
import com.swp.whmsystem.model.Model;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewModelList", urlPatterns = {"/ViewModelList"})
public class ViewModelList extends HttpServlet {
    private ModelDAO modelDao;
    private static final int PAGE_SIZE = 8;

    @Override
    public void init() {
        modelDao = new ModelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        int totalModels = modelDao.count();
        int totalPages = (int) Math.ceil((double) totalModels / PAGE_SIZE);

        List<Model> models = modelDao.getModelsByPage(page, PAGE_SIZE);
        request.setAttribute("models", models);
        request.setAttribute("pageNo", page);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ViewModelList.jsp").forward(request, response);
    }
}