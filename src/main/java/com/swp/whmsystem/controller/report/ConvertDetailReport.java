/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.report;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        String source = request.getParameter("source");
        String backDate = request.getParameter("backDate");
        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        String sortDir = request.getParameter("sortDir");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");

        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if (fromDate != null && !fromDate.isBlank()) {
            fromDate = LocalDate.parse(fromDate, input).format(output);
        }

        if (toDate != null && !toDate.isBlank()) {
            toDate = LocalDate.parse(toDate, input).format(output);
        }

        StringBuilder redirectUrl = new StringBuilder(request.getContextPath())
                .append("/inventorySummaryDetail")
                .append("?productId=").append(urlEncode(productId))
                .append("&fromDate=").append(urlEncode(fromDate))
                .append("&toDate=").append(urlEncode(toDate))
                .append("&typeFilter=ALL");

        appendParam(redirectUrl, "source", source);
        appendParam(redirectUrl, "backDate", backDate);
        appendParam(redirectUrl, "keyword", keyword);
        appendParam(redirectUrl, "sortBy", sortBy);
        appendParam(redirectUrl, "sortDir", sortDir);
        appendParam(redirectUrl, "page", page);
        appendParam(redirectUrl, "pageSize", pageSize);

        response.sendRedirect(redirectUrl.toString());

    } 

    private void appendParam(StringBuilder url, String name, String value) {
        if (value != null && !value.isBlank()) {
            url.append("&").append(name).append("=").append(urlEncode(value));
        }
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
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
