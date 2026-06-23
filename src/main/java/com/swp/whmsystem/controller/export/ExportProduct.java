package com.swp.whmsystem.controller.export;

import java.io.IOException;
import java.util.*;

import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.dto.OrderItemDetailDTO;
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

        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            response.sendRedirect("toExportList");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr.trim());
            OrderDAO orderDAO = new OrderDAO();
            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                response.sendRedirect("toExportList");
                return;
            }

            Order sessionOrder = (Order) session.getAttribute("order");
            if (sessionOrder == null || sessionOrder.getId() != orderId) {
                session.removeAttribute("scannedList");
            }

            List<OrderItemDetailDTO> pickingList = orderDAO.getOrderItemsByOrderId(orderId);
            session.setAttribute("order", order);
            session.setAttribute("pickingList", pickingList);
        } catch (NumberFormatException e) {
            response.sendRedirect("toExportList");
            return;
        }

        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        double grandTotal = 0.0;

        if (scannedList != null && !scannedList.isEmpty()) {
            for (ExportItemDTO item : scannedList) {
                grandTotal += item.getTotalCost();
            }
        }

        request.setAttribute("grandTotal", grandTotal);
        request.getRequestDispatcher("WEB-INF/view/export/exportProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        int orderId;
        String sku = request.getParameter("sku");
        String orderIdRaw = request.getParameter("orderId");

        if (orderIdRaw == null || orderIdRaw.trim().isEmpty()) {
            response.sendRedirect("toExportList");
            return;
        }

        try {
            orderId = Integer.parseInt(orderIdRaw.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("toExportList");
            return;
        }

        synchronized (session) {
            List<ExportItemDTO> scannedList =
                    (List<ExportItemDTO>) session.getAttribute("scannedList");
            if (scannedList == null) {
                scannedList = new ArrayList<>();
            } else {
                scannedList = new ArrayList<>(scannedList);
            }

            if (sku != null && !sku.trim().isEmpty()) {
                sku = sku.trim();
                ExportItemDTO productFromDB = exportItemDAO.getItemBySKU(sku, orderId);

                if (productFromDB != null) {
                    int currentScannedQty = 0;
                    for (ExportItemDTO item : scannedList) {
                        if (item.getSku().equals(sku)) {
                            currentScannedQty += item.getQty();
                        }
                    }

                    if (productFromDB.getStock() <= 0) {
                        session.setAttribute("error",
                                productFromDB.getName() + " is currently out of stock.");

                    } else if (currentScannedQty >= productFromDB.getStock()) {
                        session.setAttribute("error",
                                "Cannot add more " + productFromDB.getName()
                                        + ". Available stock: " + productFromDB.getStock() + ".");

                    } else {
                        ExportItemDTO newItem = new ExportItemDTO();

                        newItem.setSku(productFromDB.getSku());
                        newItem.setName(productFromDB.getName());
                        newItem.setImgUrl(productFromDB.getImgUrl());
                        newItem.setStock(productFromDB.getStock());
                        newItem.setPrice(productFromDB.getPrice());

                        scannedList.add(0, newItem);
                        session.removeAttribute("error");
                    }
                } else {
                    session.setAttribute("error",
                            "The product with SKU " + sku + " is not included in this order.");
                }
            }
            session.setAttribute("scannedList", scannedList);
        }
        response.sendRedirect("exportProduct?orderId=" + orderId);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
