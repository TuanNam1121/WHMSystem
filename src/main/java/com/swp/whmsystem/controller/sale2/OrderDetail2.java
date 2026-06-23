/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.sale2;

import com.swp.whmsystem.dal.CustomerDAO;
import com.swp.whmsystem.dal.ExportItemDAO;
import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dal.OrderItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.dto.ExportDetailItemDTO;
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
import java.util.List;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "OrderDetail2", urlPatterns = {"/OrderDetail2"})
public class OrderDetail2 extends HttpServlet {

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
            out.println("<title>Servlet OrderDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetail at " + request.getContextPath() + "</h1>");
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

//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            response.sendRedirect("login");
//            return;
//        }
//        RolePermissionDAO rpd = new RolePermissionDAO();
//        if(!rpd.havePermission(user, "CreateOder")){
//            response.sendRedirect("NoPermission");
//            return;
//        }
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        request.setAttribute("action", action);

        OrderDAO od = new OrderDAO();
        int orderId = Integer.parseInt(id);
        Order order = od.getOrderById(orderId);
        request.setAttribute("order", order);
        CustomerDAO cd = new CustomerDAO();
        request.setAttribute("customers", cd.getAllCustomer());

        //VIEW
        if (action.equals("view")) {
            if (order.getStatus().equals("NEW")) {
                OrderItemDAO oid = new OrderItemDAO();

                ProductDAO pd = new ProductDAO();
                request.setAttribute("orderItems", oid.getOrderItemByOrderId(orderId));
                request.getRequestDispatcher("WEB-INF/view/sale2/viewOrder.jsp").forward(request, response);
                return;
            } else {

                ExportItemDAO exportItemDAO = new ExportItemDAO();
                List<ExportDetailItemDTO> detailList = exportItemDAO.getExportedItemsByOrderId(orderId);
                request.setAttribute("itemList", detailList);
                request.getRequestDispatcher("WEB-INF/view/sale2/viewOrder.jsp").forward(request, response);
                return;
            }

        }

        //UPDATE
        if (order.getStatus().equals("NEW")) {
            ProductDAO pd = new ProductDAO();
            request.setAttribute("products", pd.getProductList());
            OrderItemDAO oid = new OrderItemDAO();
            
            
            HttpSession session = request.getSession();
            session.setAttribute("orderItems", oid.getOrderItemByOrderId(orderId));

            request.getRequestDispatcher("WEB-INF/view/sale2/orderDetail.jsp").forward(request, response);
            return;
        } else {
            response.sendRedirect("NoPermission");
            return;
        }

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
        String note = request.getParameter("note");
        String orderidStr = request.getParameter("orderid");
        int orderid = Integer.parseInt(orderidStr);

        OrderDAO od = new OrderDAO();
        Order order = od.getOrderById(orderid);
        order.setNote(note);

        String[] productIds = request.getParameterValues("productId");
        ProductDAO pd = new ProductDAO();

        OrderItemDAO oid = new OrderItemDAO();
        oid.deleteOrderItem(orderid);
        double total = 0;

        for (String pid : productIds) {

            int productId = Integer.parseInt(pid);

            String quantityStr = request.getParameter("quantity_" + productId);

            String priceStr = request.getParameter("price_" + productId);

            if (quantityStr != null && !quantityStr.isBlank() && priceStr != null && !priceStr.isBlank()) {

                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);
                if (quantity > 0 && price > 0) {
                    total += (price * quantity);
                    OrderItem item = new OrderItem();

                    item.setOrderId(orderid);
                    item.setProductId(productId);
                    item.setQuantity(quantity);
                    item.setPrice(price);
                    oid.insertOrderItem(item);
                }
            }
        }

        order.setTotalPrice(total);
        od.updateOrderPrice(order);
        od.updateOrderNote(order);
        response.sendRedirect("OrderList2");
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
