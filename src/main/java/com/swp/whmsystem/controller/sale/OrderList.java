/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.sale;

import com.swp.whmsystem.dal.CustomerDAO;
import com.swp.whmsystem.dal.OrderDAO;
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
@WebServlet(name="OrderList", urlPatterns={"/OrderList"})
public class OrderList extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet OrderList</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderList at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        OrderDAO od = new OrderDAO();
        CustomerDAO cd = new CustomerDAO();
        request.setAttribute("customers", cd.getAllCustomer());
        request.setAttribute("orders", od.getAllOrder());
        request.getRequestDispatcher("WEB-INF/view/sale/orderList.jsp").forward(request, response);
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
        String searchName = request.getParameter("searchName");
        String searchStatus = request.getParameter("searchStatus");
        
        OrderDAO od = new OrderDAO();
        CustomerDAO cd = new CustomerDAO();
        
        if((searchName == null || searchName.isBlank()) && (searchStatus.equals("ALL"))){
            request.setAttribute("customers", cd.getAllCustomer());
            request.setAttribute("orders", od.getAllOrder());
        request.getRequestDispatcher("WEB-INF/view/sale/orderList.jsp").forward(request, response);
        return;
        }else{
            request.setAttribute("searchName", searchName);
            request.setAttribute("searchStatus", searchStatus);
            request.setAttribute("orders", od.searchOrder(searchName, searchStatus));
            request.setAttribute("customers", cd.getAllCustomer());
        request.getRequestDispatcher("WEB-INF/view/sale/orderList.jsp").forward(request, response);
        }
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
