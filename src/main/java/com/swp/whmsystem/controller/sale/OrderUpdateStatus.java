/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.sale;

import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dal.OrderItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.model.Order;
import com.swp.whmsystem.model.OrderItem;
import com.swp.whmsystem.model.ProductItem;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author LENOVO
 */
@WebServlet(name="OrderUpdateStatus", urlPatterns={"/OrderUpdateStatus"})
public class OrderUpdateStatus extends HttpServlet {
   
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
            out.println("<title>Servlet OrderUpdateStatus</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderUpdateStatus at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        String id = request.getParameter("orderid");
        int orderid = Integer.parseInt(id);
        String orderStatus = request.getParameter("orderStatus");
        
        OrderDAO od = new OrderDAO();
        if(orderStatus.equals("COMPLETED")){
            Order o = od.getOrderById(orderid);
            o.setStatus("COMPLETED");
            od.updateOrderStatus(o);
            response.sendRedirect("OrderList");
        }else{
            OrderItemDAO oid = new OrderItemDAO();
            List<OrderItem> list = oid.getOrderItemByOrderId(orderid);
            Order o = od.getOrderById(orderid);
            
            //set status of order
            o.setStatus("CANCELLED");
            od.updateOrderStatus(o);
            ProductDAO pd = new ProductDAO();
            
            //restore number of product
            for(OrderItem oi:list){
                pd.changeProductQuantity( pd.getProductQuantityById(oi.getProductId()) + oi.getQuantity(), oi.getProductId());
            }
            
            //restore status of evry product item
            ProductItemDAO pid = new ProductItemDAO();
            List<ProductItem> products = pid.getAllProductItemByOrderId(orderid);
            for(ProductItem pi:products){
                pi.setStatus("AVAILABLE");
                pid.updateProductItemStatus(pi);
            }
            response.sendRedirect("OrderList");
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
