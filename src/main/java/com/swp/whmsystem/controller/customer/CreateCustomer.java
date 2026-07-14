/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.customer;

import com.swp.whmsystem.dal.CustomerDAO;
import com.swp.whmsystem.model.Customer;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
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
@WebServlet(name="CreateCustomer", urlPatterns={"/CreateCustomer"})
public class CreateCustomer extends HttpServlet {
   
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
            out.println("<title>Servlet CreateCustomer</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCustomer at " + request.getContextPath () + "</h1>");
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
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_CUSTOMER,
                    "You are not authorized to create customer.")) {
                return;
            }
        request.getRequestDispatcher("WEB-INF/view/customer/createCustomer.jsp").forward(request, response);
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
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        
        try{
            int phone = Integer.parseInt(customerPhone);
        }catch(Exception e){
            request.setAttribute("error", "Phone must be digit");
            request.getRequestDispatcher("WEB-INF/view/customer/createCustomer.jsp").forward(request, response);
            return;
        }
        
        CustomerDAO cd = new CustomerDAO();
        if(cd.getCustomerByPhone(customerPhone)!=null){
            request.setAttribute("error", "Existed customer");
            request.getRequestDispatcher("WEB-INF/view/customer/createCustomer.jsp").forward(request, response);
            return;
        }else{
            Customer c = new Customer(0, customerName, customerPhone);
            cd.insertCustomer(c);
            System.out.println(c.toString());
            
            request.setAttribute("message", "added successfully");
            request.setAttribute("customers", cd.getAllCustomer());
        request.getRequestDispatcher("WEB-INF/view/customer/customerList.jsp").forward(request, response);
        return;
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
