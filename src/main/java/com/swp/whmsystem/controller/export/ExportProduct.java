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

        // TRƯỜNG HỢP 1: CÓ ORDER ID TRÊN URL (Mới bấm từ trang danh sách sang)
        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                int orderId = Integer.parseInt(orderIdStr.trim());
                OrderDAO orderDAO = new OrderDAO();
                Order order = orderDAO.getOrderById(orderId);

                if (order != null) {
                    // 1. Lưu Order mới vào session
                    session.setAttribute("order", order);

                    // 2. Dọn sạch giỏ hàng cũ (cực kỳ quan trọng để không bị lẫn lộn đơn)
                    session.removeAttribute("scannedList");

                    System.out.println(">>> [doGet] Đã lưu Order " + orderId + " vào Session!");
                } else {
                    System.out.println(">>> [doGet] Không tìm thấy Order trong Database!");
                }
            } catch (NumberFormatException e) {
                System.out.println(">>> [doGet] Lỗi định dạng ID: " + e.getMessage());
            }
        }
        // TRƯỜNG HỢP 2: KHÔNG CÓ ORDER ID (Sau khi quét mã form tự reload)
        else {
            Order sessionOrder = (Order) session.getAttribute("order");
            if (sessionOrder != null) {
                System.out.println(">>> [doGet] URL không có ID, nhưng đã lấy thành công Order từ Session!");
            } else {
                System.out.println(">>> [doGet] BÁO ĐỘNG: Đã mất Session Order! Cần check lại hàm doPost!");
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

