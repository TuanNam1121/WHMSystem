/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import java.io.IOException;

import com.swp.whmsystem.dal.ModelDAO;
import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddModel", urlPatterns = {"/AddModel"})
public class AddModel extends HttpServlet {
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
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_SPECIFICATION,
                "You are not authorized to create specifications.")) {
            return;
        }
        request.setAttribute("brands", brandDao.getAllBrand());
        request.getRequestDispatcher("WEB-INF/view/product/addModel.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String brandId = request.getParameter("brandId");
        String active = request.getParameter("active");
        String modelName = name == null ? "" : name.trim();

        if (modelName.isEmpty()) {
            request.setAttribute("message", "Model name is required");
            request.setAttribute("brands", brandDao.getAllBrand());
            request.getRequestDispatcher("WEB-INF/view/product/addModel.jsp").forward(request, response);
            return;
        }

        if (modelDao.getModelByName(modelName) != null) {
            request.setAttribute("message", "Model name already exists");
            request.setAttribute("brands", brandDao.getAllBrand());
            request.getRequestDispatcher("WEB-INF/view/product/addModel.jsp").forward(request, response);
            return;
        }

        modelDao.insertModel(modelName, Integer.parseInt(brandId), active != null);
        response.sendRedirect("ModelList");
    }
}
