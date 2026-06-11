package com.swp.whmsystem.controller.export;

import java.io.IOException;
import java.util.*;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ExportProduct", urlPatterns = {"/exportProduct"})
public class ExportProduct extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                int orderId = Integer.parseInt(orderIdStr.trim());
                OrderDAO orderDAO = new OrderDAO();
                Order order = orderDAO.getOrderById(orderId);
                if (order != null) {
                    session.setAttribute("order", order);
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        request.getRequestDispatcher("WEB-INF/view/export/exportProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        String sku = request.getParameter("sku");

        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        if (scannedList == null) {
            scannedList = new ArrayList<>();
        }

        if (sku != null && !sku.trim().isEmpty()) {
            sku = sku.trim();
            ExportItemDTO productFromDB = exportItemDAO.getItemBySKU(sku);

            if (productFromDB != null) {
                int currentScannedQty = 0;
                for (ExportItemDTO item : scannedList) {
                    if (item.getSku().equals(sku)) {
                        currentScannedQty++;
                    }
                }

                if (productFromDB.getStock() <= 0) {
                    session.setAttribute("error", "Product [" + productFromDB.getName() + "] is currently out of stock!");

                } else if (currentScannedQty >= productFromDB.getStock()) {
                    session.setAttribute("error", "Exceeds stock limit! Only " + productFromDB.getStock() + " item(s) left for SKU [" + sku + "].");

                } else {
                    ExportItemDTO newItem = new ExportItemDTO();

                    newItem.setSku(productFromDB.getSku());
                    newItem.setName(productFromDB.getName());
                    newItem.setImgUrl(productFromDB.getImgUrl());
                    newItem.setStock(productFromDB.getStock());
                    newItem.setPrice(productFromDB.getPrice());

                    scannedList.add(0, newItem);
                }
            } else {
                session.setAttribute("error", "Cannot find product with SKU: " + sku);
            }
        }
        session.setAttribute("scannedList", scannedList);
        response.sendRedirect("exportProduct");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

