/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.brand;

import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "AddBrand", urlPatterns = {"/AddBrand"})
public class AddBrand extends HttpServlet {

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
            out.println("<title>Servlet AddBrand</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBrand at " + request.getContextPath() + "</h1>");
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
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_BRAND,
                    "You are not authorized to create brand.")) {
                return;
            }
        request.setAttribute("act", "new");
        request.getRequestDispatcher("WEB-INF/view/brand/addBrand.jsp").forward(request, response);
        return;
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
        
        BrandDAO bd = new BrandDAO();
        
        if(name == null || name.trim().isEmpty()){
            request.setAttribute("message", "name required");
            request.getRequestDispatcher("WEB-INF/view/brand/addBrand.jsp").forward(request, response);
            return;
        }
        
         Brand check = bd.getBrandByName(name);
         
         if(check != null){
             request.setAttribute("message", "Brand exist");
            request.getRequestDispatcher("WEB-INF/view/brand/addBrand.jsp").forward(request, response);
            return;
         }
         
         Brand b = new Brand();
         b.setId(0);
         b.setName(name);
         b.setDescription(description);
         b.setCreatedAt(new Timestamp(System.currentTimeMillis()));
         b.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
         bd.insertBrand(b);
         response.sendRedirect("brandList");
            return;
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

