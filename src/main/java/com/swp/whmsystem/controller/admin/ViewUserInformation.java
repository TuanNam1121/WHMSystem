/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.model.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name="ViewUserInformation", urlPatterns={"/ViewUserInformation"})
public class ViewUserInformation extends HttpServlet {
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        try {
            int id = Integer.parseInt(id_raw);
            UserDAO userDao = new UserDAO();
            RoleDAO roleDao = new RoleDAO();
            List<Role> list = roleDao.getAllRoleToAssign();
            User user = userDao.getUserFromId(id);
            request.setAttribute("act", "view");
            if (user == null) {
                request.setAttribute("error", "User not found.");
                request.getRequestDispatcher("ViewUserList").forward(request, response);
                return;
            }
            request.setAttribute("u", user);
            request.setAttribute("roleDao", roleDao);
            request.setAttribute("rolelist", list);
            request.getRequestDispatcher("WEB-INF/view/admin/userDetail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            String message = ex.getMessage();
            request.setAttribute("error", message);
            request.getRequestDispatcher("WEB-INF/view/admin/viewUserList.jsp").forward(request, response);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
