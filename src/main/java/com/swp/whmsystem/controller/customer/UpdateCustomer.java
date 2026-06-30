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
@WebServlet(name="UpdateCustomer", urlPatterns={"/UpdateCustomer"})
public class UpdateCustomer extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateCustomer</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCustomer at " + request.getContextPath () + "</h1>");
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
        
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.UPDATE_CUSTOMER,
                    "You are not authorized to update customer.")) {
                return;
            }
        String customerIdStr = request.getParameter("id");
        int customerId = Integer.parseInt(customerIdStr);
        CustomerDAO cd = new CustomerDAO();
        request.setAttribute("customer", cd.getCustomerById(customerId));
        request.getRequestDispatcher("WEB-INF/view/customer/updateCustomer.jsp").forward(request, response);
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
        String customerIdStr = request.getParameter("customerId");
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        
        int id = Integer.parseInt(customerIdStr);
        
        try{
            int phone = Integer.parseInt(customerPhone);
        }catch(Exception e){
            request.setAttribute("error", "Phone must be degit");
            request.getRequestDispatcher("WEB-INF/view/customer/updateCustomer.jsp").forward(request, response);
            return;
        }
        
        CustomerDAO cd = new CustomerDAO();
        
        Customer check = cd.getCustomerByPhone(customerPhone);
        if(check != null && check.getId()!=id){
            request.setAttribute("error", "Existed phone number");
            request.setAttribute("customer", cd.getCustomerById(id));
            request.getRequestDispatcher("WEB-INF/view/customer/updateCustomer.jsp").forward(request, response);
            return;
        }
        
        
            Customer c = cd.getCustomerById(id);
            c.setName(customerName);
            c.setPhone(customerPhone);
            cd.updateCustomer(c);
            System.out.println(c.toString());
            
            request.setAttribute("message", "updated customer "+id+" successfully");
            request.setAttribute("customers", cd.getAllCustomer());
        request.getRequestDispatcher("WEB-INF/view/customer/customerList.jsp").forward(request, response);
        return;
        
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
