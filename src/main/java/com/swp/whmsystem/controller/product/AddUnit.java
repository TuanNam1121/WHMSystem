/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import java.io.IOException;

import com.swp.whmsystem.dal.UnitDAO;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddUnit", urlPatterns = {"/AddUnit"})
public class AddUnit extends HttpServlet {
    private UnitDAO unitDao;

    @Override
    public void init() {
        unitDao = new UnitDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_SPECIFICATION,
                "You are not authorized to create specifications.")) {
            return;
        }
        request.getRequestDispatcher("WEB-INF/view/product/addUnit.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String active = request.getParameter("active");
        String unitName = name == null ? "" : name.trim();

        if (unitName.isEmpty()) {
            request.setAttribute("message", "Unit name is required");
            request.getRequestDispatcher("WEB-INF/view/product/addUnit.jsp").forward(request, response);
            return;
        }

        if (unitDao.getUnitByName(unitName) != null) {
            request.setAttribute("message", "Unit name already exists");
            request.getRequestDispatcher("WEB-INF/view/product/addUnit.jsp").forward(request, response);
            return;
        }

        unitDao.insertUnit(unitName, active != null);
        response.sendRedirect("UnitList");
    }
}
