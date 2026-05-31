/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.brand;

import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.utils.FileUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.Timestamp;

/**
 *
 * @author LENOVO
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class BrandDetail extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewBrandList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewBrandList at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String act = request.getParameter("act");
        if (act == null || !act.equals("new")) {
            int id = Integer.parseInt(request.getParameter("id"));
            BrandDAO bd = new BrandDAO();
            Brand b = bd.getBrandById(id);

            request.setAttribute("act", "update");
            request.setAttribute("brand", b);
            request.getRequestDispatcher("view/addBrand.jsp").forward(request, response);
            return;
        } else {
            request.setAttribute("act", "new");
            request.getRequestDispatcher("view/addBrand.jsp").forward(request, response);
            return;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        String act = request.getParameter("act");
        BrandDAO bd = new BrandDAO();

        if (name == null || name.trim().equals("")) {
            if (act == null || !act.equals("new")) {
                String id = request.getParameter("id");
                request.setAttribute("act", "update");
                Brand brandById = bd.getBrandById(Integer.parseInt(id));
                request.setAttribute("brand", brandById);
            } else {
                request.setAttribute("act", "new");
            }
            request.setAttribute("message", "name required");
            request.getRequestDispatcher("view/addBrand.jsp").forward(request, response);

            return;

        }

        Brand check = bd.getBrandByName(name);

        if (check != null) {

            if (act == null || !act.equals("new")) {
                String id = request.getParameter("id");
                request.setAttribute("act", "update");
                Brand brandById = bd.getBrandById(Integer.parseInt(id));
                request.setAttribute("brand", brandById);
                if (brandById.getId() != check.getId()) {
                    request.setAttribute("message", "name exist");
                    request.getRequestDispatcher("view/addBrand.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("act", "new");
                request.setAttribute("message", "name exist");
                request.getRequestDispatcher("view/addBrand.jsp").forward(request, response);

                return;
            }
        }

        if (act == null || !act.equals("new")) {
            String id = request.getParameter("id");
            Brand oldBrand = bd.getBrandById(Integer.parseInt(id));
            Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
            Part part = request.getPart("image");
            if (part != null && part.getSize() > 0) {
                String imgUrl = FileUtils.saveFileBrand(part, request);
                oldBrand.setImg(imgUrl);
            }

            oldBrand.setName(name);
            oldBrand.setDescription(description);
            oldBrand.setUpdatedAt(updatedAt);

            bd.updateBrand(oldBrand);
            response.sendRedirect("ViewBrandList");
            return;
        } else {
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
            Brand b = new Brand(0, name, null, description, createdAt, updatedAt);

            bd.insertBrand(b);
            response.sendRedirect("ViewBrandList");
            return;
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
