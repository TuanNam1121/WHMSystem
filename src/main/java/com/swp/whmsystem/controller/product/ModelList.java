package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.ModelDAO;
import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Model;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ModelList", urlPatterns = {"/ModelList"})
public class ModelList extends HttpServlet {
    private ModelDAO modelDao;
    private BrandDAO brandDao;

    @Override
    public void init() {
        modelDao = new ModelDAO();
        brandDao = new BrandDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        Integer brandId = null;
        try {
            String brandIdParam = request.getParameter("brandId");
            if (brandIdParam != null && !brandIdParam.trim().isEmpty()) {
                brandId = Integer.valueOf(brandIdParam.trim());
            }
        } catch (NumberFormatException ignored) {
        }

        List<Model> models = modelDao.getModelsByFilter(brandId, status);
        List<Brand> brands = brandDao.getAllBrand();

        request.setAttribute("models", models);
        request.setAttribute("brands", brands);
        request.setAttribute("status", status);
        request.setAttribute("selectedBrandId", brandId);
        request.getRequestDispatcher("view/modelList.jsp").forward(request, response);
    }
}