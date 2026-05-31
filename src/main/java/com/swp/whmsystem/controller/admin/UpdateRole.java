/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.utils.*;
import com.swp.whmsystem.dal.RolePermissionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "updateRole", urlPatterns = {"/updateRole"})
public class UpdateRole extends HttpServlet {

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
            out.println("<title>Servlet UpdateRole</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRole at " + request.getContextPath() + "</h1>");
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
        int roleId = Integer.parseInt(request.getParameter("id"));
        RoleDAO rd = new RoleDAO();
        Role r = rd.getRoleById(roleId);

        request.setAttribute("role", r);
        RolePermissionDAO rpd = new RolePermissionDAO();
        PermissionDAO pd = new PermissionDAO();

        request.setAttribute("permissions", pd.getAllPermission());
        request.setAttribute("includePermissions", rpd.getPermissionByRole(roleId));

        request.getRequestDispatcher("view/updateRole.jsp").forward(request, response);
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
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        String name = request.getParameter("roleName");
        String isActive = request.getParameter("isActive");

        String[] permissions = request.getParameterValues("permission");

        RoleDAO rd = new RoleDAO();
        
        //check if name empty
        if(name.equals(null) || name.trim().equals("")){
            request.setAttribute("error", "Name is required");
        Role r = rd.getRoleById(roleId);

        request.setAttribute("role", r);
        RolePermissionDAO rpd = new RolePermissionDAO();
        PermissionDAO pd = new PermissionDAO();

        request.setAttribute("permissions", pd.getAllPermission());
        request.setAttribute("includePermissions", rpd.getPermissionByRole(roleId));
            request.getRequestDispatcher("view/updateRole.jsp").forward(request, response);
        return;
        }
        
        Role r = new Role(roleId, name, isActive.equals("true"));
        rd.updateRole(r);
        //update Role field then disable role permission then remap
        RolePermissionDAO rpd = new RolePermissionDAO();
        rpd.deletePermissionRole(r);
        if (permissions != null) {
            for (String str : permissions) {
                int permissionId = Integer.parseInt(str);
                rpd.insertRolePermission(permissionId, roleId);
            }
        }
        response.sendRedirect("ViewRoleList");
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
