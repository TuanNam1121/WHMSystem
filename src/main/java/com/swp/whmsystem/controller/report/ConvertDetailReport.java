/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.report;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Admin
 */
@WebServlet(name="ConvertDetailReport", urlPatterns={"/ConvertDetailReport"})
public class ConvertDetailReport extends HttpServlet {
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
        String productId = request.getParameter("productId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if (fromDate != null && !fromDate.isBlank()) {
            fromDate = LocalDate.parse(fromDate, input).format(output);
        }

        if (toDate != null && !toDate.isBlank()) {
            toDate = LocalDate.parse(toDate, input).format(output);
        }

        response.sendRedirect(
                request.getContextPath()
                + "/inventorySummaryDetail"
                + "?productId=" + productId
                + "&fromDate=" + fromDate
                + "&toDate=" + toDate
                + "&typeFilter=ALL");

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
