

package com.swp.whmsystem.controller.export;

import java.io.IOException;
import java.util.*;

import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "ExportProduct", urlPatterns = {"/exportProduct"})
public class ExportProduct extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/view/export/toExportList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

