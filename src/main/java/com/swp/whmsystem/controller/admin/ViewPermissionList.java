/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
public class ViewPermissionList extends HttpServlet {

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
            out.println("<title>Servlet ViewPermissionList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewPermissionList at " + request.getContextPath() + "</h1>");
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
        PermissionDAO pd = new PermissionDAO();
        RoleDAO rd = new RoleDAO();
        request.setAttribute("roles", rd.getAllRole());

        String keyword = request.getParameter("keyword");
        String roleid = request.getParameter("roleid");
        int role;
        try {
            role = Integer.parseInt(roleid);
        } catch (Exception e) {
            role = 0;
        }

        if (keyword != null && !keyword.trim().equals("")) {
            if (role != 0) {
                request.setAttribute("permissions", pd.searchPermissionByName(keyword, role));
            } else {
                request.setAttribute("permissions", pd.searchPermissionByName(keyword, 0));
            }
            request.setAttribute("roleid", role);
            request.getRequestDispatcher("view/viewPermissionList.jsp").forward(request, response);
            return;
        } else if (keyword == null || keyword.trim().equals("")) {
            if (role != 0) {
                request.setAttribute("roleid", role);
                request.setAttribute("permissions", pd.searchPermissionByName("", role));
                request.getRequestDispatcher("view/viewPermissionList.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("roleid", role);
        request.setAttribute("permissions", pd.getAllPermission());
        request.getRequestDispatcher("view/viewPermissionList.jsp").forward(request, response);
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
        processRequest(request, response);
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
