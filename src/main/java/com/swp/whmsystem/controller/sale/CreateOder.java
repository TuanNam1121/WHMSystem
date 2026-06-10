/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.sale;

import com.swp.whmsystem.dal.CustomerDAO;
import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dal.OrderItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.model.Customer;
import com.swp.whmsystem.model.Order;
import com.swp.whmsystem.model.OrderItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CreateOder", urlPatterns = {"/CreateOder"})
public class CreateOder extends HttpServlet {

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
            out.println("<title>Servlet CreateOder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateOder at " + request.getContextPath() + "</h1>");
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
        ProductDAO pd = new ProductDAO();
        request.setAttribute("products", pd.getProductList());
        request.getRequestDispatcher("WEB-INF/view/sale/createOrder.jsp").forward(request, response);
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
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        String note = request.getParameter("note");

        CustomerDAO cd = new CustomerDAO();
        Customer customer = cd.getCustomerByPhone(customerPhone);

        if (customer == null) {
            customer = new Customer(0, customerName, customerPhone);
            cd.insertCustomer(customer);
            customer.setId(cd.getCustomerByPhone(customerPhone).getId());
        }

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("noPermission");
            return;
        }
        User user = (User) session.getAttribute("user");

        OrderDAO od = new OrderDAO();
        OrderItemDAO oid = new OrderItemDAO();
        Order order = new Order();
        order.setStatus("NEW");
        order.setNote(note);
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        order.setCreatedBy(user.getId());
        order.setCustomerId(customer.getId());

        Order createdOrder = od.insertOrder(order);

        String[] productIds = request.getParameterValues("productId");
        ProductDAO pd = new ProductDAO();

        for (String pid : productIds) {

            int productId = Integer.parseInt(pid);

            String quantityStr = request.getParameter("quantity_" + productId);

            String priceStr = request.getParameter("price_" + productId);

            if (quantityStr != null && !quantityStr.isBlank() && priceStr != null && !priceStr.isBlank()) {

                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                OrderItem item = new OrderItem();

                item.setOrderId(createdOrder.getId());
                item.setProductId(productId);
                item.setQuantity(quantity);
                item.setPrice(price);

                oid.insertOrderItem(item);
                Product p = pd.getProductFromId(productId);
                pd.changeProductQuantity(p.getTotalQuantity() - quantity,productId);
            }
        }
        response.sendRedirect("OrderList");
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
