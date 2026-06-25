package com.swp.whmsystem.controller.export;

import java.io.IOException;
import java.util.*;

import com.google.gson.JsonObject;
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

            ExportItemDAO exportItemDAO = new ExportItemDAO();
            String exportReceiptStatus = exportItemDAO.getExportReceiptStatusByOrderId(orderId);
            if ("COMPLETED".equalsIgnoreCase(exportReceiptStatus)) {
                response.sendRedirect("exportDetail?orderId=" + orderId);
                return;
            }

            Order sessionOrder = (Order) session.getAttribute("order");
            if (sessionOrder == null || sessionOrder.getId() != orderId) {
                session.removeAttribute("scannedList");
            }

            if ("DRAFT".equalsIgnoreCase(exportReceiptStatus)
                    && session.getAttribute("scannedList") == null) {
                session.setAttribute("scannedList",
                        exportItemDAO.getDraftItemsByOrderId(orderId));
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

    /*
     * CODE CŨ: Xử lý bằng backend và reload lại trang sau mỗi lần quét SKU.
     *
     * @Override
     * protected void doPost(HttpServletRequest request, HttpServletResponse response)
     *         throws ServletException, IOException {
     *     HttpSession session = request.getSession();
     *     ExportItemDAO exportItemDAO = new ExportItemDAO();
     *     int orderId;
     *     String sku = request.getParameter("sku");
     *     String orderIdRaw = request.getParameter("orderId");
     *
     *     if (orderIdRaw == null || orderIdRaw.trim().isEmpty()) {
     *         response.sendRedirect("toExportList");
     *         return;
     *     }
     *
     *     try {
     *         orderId = Integer.parseInt(orderIdRaw.trim());
     *     } catch (NumberFormatException e) {
     *         response.sendRedirect("toExportList");
     *         return;
     *     }
     *
     *     synchronized (session) {
     *         List<ExportItemDTO> scannedList =
     *                 (List<ExportItemDTO>) session.getAttribute("scannedList");
     *         if (scannedList == null) {
     *             scannedList = new ArrayList<>();
     *         } else {
     *             scannedList = new ArrayList<>(scannedList);
     *         }
     *
     *         if (sku != null && !sku.trim().isEmpty()) {
     *             sku = sku.trim();
     *             ExportItemDTO productFromDB =
     *                     exportItemDAO.getItemBySKU(sku, orderId);
     *
     *             if (productFromDB != null) {
     *                 int currentScannedQty = 0;
     *                 for (ExportItemDTO item : scannedList) {
     *                     if (item.getSku().equalsIgnoreCase(sku)) {
     *                         currentScannedQty += item.getQty();
     *                     }
     *                 }
     *
     *                 if (productFromDB.getStock() <= 0) {
     *                     session.setAttribute("error",
     *                             productFromDB.getName()
     *                                     + " is currently out of stock.");
     *                 } else if (currentScannedQty >= productFromDB.getStock()) {
     *                     session.setAttribute("error",
     *                             "Cannot add more " + productFromDB.getName()
     *                                     + ". Available stock: "
     *                                     + productFromDB.getStock() + ".");
     *                 } else {
     *                     ExportItemDTO newItem = new ExportItemDTO();
     *                     newItem.setSku(productFromDB.getSku());
     *                     newItem.setName(productFromDB.getName());
     *                     newItem.setImgUrl(productFromDB.getImgUrl());
     *                     newItem.setStock(productFromDB.getStock());
     *                     newItem.setPrice(productFromDB.getPrice());
     *
     *                     scannedList.add(0, newItem);
     *                     session.removeAttribute("error");
     *                 }
     *             } else {
     *                 session.setAttribute("error",
     *                         "The product with SKU " + sku
     *                                 + " is not included in this order.");
     *             }
     *         }
     *
     *         session.setAttribute("scannedList", scannedList);
     *     }
     *
     *     response.sendRedirect("exportProduct?orderId=" + orderId);
     * }
     */

    // CODE MỚI: Trả JSON để JavaScript thêm sản phẩm mà không reload trang.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        String sku = request.getParameter("sku");
        String orderIdRaw = request.getParameter("orderId");

        if (orderIdRaw == null || orderIdRaw.trim().isEmpty()) {
            sendAjaxResponse(response, false, "Order ID is missing.", null, 0);
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdRaw.trim());
        } catch (NumberFormatException e) {
            sendAjaxResponse(response, false, "Invalid order ID.", null, 0);
            return;
        }

        if ("COMPLETED".equalsIgnoreCase(exportItemDAO.getExportReceiptStatusByOrderId(orderId))) {
            sendAjaxResponse(response, false,
                    "This order has already been exported.", null, 0);
            return;
        }

        ExportItemDTO addedItem = null;
        String errorMessage = null;
        double grandTotal = 0;

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
                ExportItemDTO productFromDB =
                        exportItemDAO.getItemBySKU(sku, orderId);

                if (productFromDB == null) {
                    errorMessage = "The product with SKU " + sku
                            + " is not included in this order.";
                } else {
                    int currentScannedQty = 0;
                    for (ExportItemDTO item : scannedList) {
                        if (item.getSku().equalsIgnoreCase(sku)) {
                            currentScannedQty += item.getQty();
                        }
                    }

                    if (productFromDB.getStock() <= 0) {
                        errorMessage = productFromDB.getName()
                                + " is currently out of stock.";
                    } else if (currentScannedQty >= productFromDB.getStock()) {
                        errorMessage = "Cannot add more " + productFromDB.getName()
                                + ". Available stock: "
                                + productFromDB.getStock() + ".";
                    } else {
                        addedItem = new ExportItemDTO();
                        addedItem.setSku(productFromDB.getSku());
                        addedItem.setName(productFromDB.getName());
                        addedItem.setImgUrl(productFromDB.getImgUrl());
                        addedItem.setStock(productFromDB.getStock());
                        addedItem.setPrice(productFromDB.getPrice());
                        scannedList.add(0, addedItem);
                    }
                }
            }

            session.setAttribute("scannedList", scannedList);

            for (ExportItemDTO item : scannedList) {
                grandTotal += item.getTotalCost();
            }
        }

        sendAjaxResponse(response, errorMessage == null,
                errorMessage, addedItem, grandTotal);
    }

    private void sendAjaxResponse(HttpServletResponse response, boolean success,
                                  String message, ExportItemDTO item,
                                  double grandTotal) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("success", success);
        json.addProperty("message", message == null ? "" : message);
        json.addProperty("grandTotal", grandTotal);

        if (item != null) {
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("tempId", item.getTempId());
            itemJson.addProperty("sku", item.getSku());
            itemJson.addProperty("name", item.getName());
            itemJson.addProperty("imgUrl", item.getImgUrl());
            itemJson.addProperty("qty", item.getQty());
            itemJson.addProperty("price", item.getPrice());
            itemJson.addProperty("stock", item.getStock());
            json.add("item", itemJson);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
