/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.*;
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
public class AddNewPermission extends HttpServlet {

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
            out.println("<title>Servlet AddNewPermission</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewPermission at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String name = request.getParameter("permissionName");
        String description = request.getParameter("permissionDescription");
        RolePermissionDAO rpd = new RolePermissionDAO();
        String[] roleIds = request.getParameterValues("role");
        RoleDAO rd = new RoleDAO();

//        String description = request.getParameter("permissionDescription");
        if (name.equals(null) || name.trim().equals("")) {
            request.setAttribute("act", "new");
            request.setAttribute("error", "Permission Name is required");
            request.setAttribute("rolelist", rd.getAllRole());
            request.getRequestDispatcher("WEB-INF/view/admin/permissionDetail.jsp").forward(request, response);
            return;
        }else if(description.equals(null) || description.trim().equals("")){
            request.setAttribute("permissionName", name);
            request.setAttribute("act", "new");
            request.setAttribute("error", "Permission description is required");
            request.setAttribute("rolelist", rd.getAllRole());
            request.getRequestDispatcher("WEB-INF/view/admin/permissionDetail.jsp").forward(request, response);
            return;
        }

        PermissionDAO pd = new PermissionDAO();
        Permission p = new Permission(0, name, description);
        pd.insertPermission(p);

        if (roleIds != null) {

            for (String id : roleIds) {

                int roleId = Integer.parseInt(id);

                rpd.insertRolePermission(pd.getPermissionByName(name).getPermissionId(), roleId);
            }
        }

        response.sendRedirect("ViewPermissionList");
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

