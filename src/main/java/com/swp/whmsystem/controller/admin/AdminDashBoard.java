/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.admin;

import com.swp.whmsystem.dal.RequestDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import com.swp.whmsystem.model.Request;

/**
 *
 * @author Admin
 */
@WebServlet(name="AdminDashBoard", urlPatterns={"/AdminDashBoard"})
public class AdminDashBoard extends HttpServlet {
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
        RequestDAO requestDao = new RequestDAO();
        List<Request> requestList = requestDao.getAllRequest();
        request.setAttribute("requestlist", requestList);
        request.setAttribute("num", 12);
        request.getRequestDispatcher("AdminDashBoard.jsp").forward(request, response);
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
        RequestDAO requestDao = new RequestDAO();
        List<Request> requestList = requestDao.getAllRequest();
        request.setAttribute("requestlist", requestList);
        request.setAttribute("num", 12);
        request.getRequestDispatcher("AdminDashBoard.jsp").forward(request, response);
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
