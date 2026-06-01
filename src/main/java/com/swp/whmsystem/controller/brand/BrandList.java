package com.swp.whmsystem.controller.brand;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BrandList", urlPatterns = {"/brandList"})
public class BrandList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> brandList = new ArrayList<>();

        String brandName = request.getParameter("brandName");
        String brandDes = request.getParameter("brandDes");

        if (brandName == null && brandDes == null) {
            brandList = brandDAO.getAllBrand();
        } else {
            brandList = brandDAO.searchBrand(brandName, brandDes);
        }

        session.setAttribute("brandList", brandList);
        request.getRequestDispatcher("view/brandList.jsp").forward(request, response);
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
