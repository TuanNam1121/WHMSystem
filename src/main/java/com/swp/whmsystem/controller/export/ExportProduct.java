package com.swp.whmsystem.controller.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.JsonObject;
import com.swp.whmsystem.dal.*;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.dto.OrderItemDetailDTO;
import com.swp.whmsystem.model.Order;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
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

        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PROCESS_EXPORT,
                "You don't have permission to process export products!")) {
            return;
        }

        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        ExportItemDAO exportItemDAO = new ExportItemDAO();

        int orderId;
        try {
            orderId = Integer.parseInt(request.getParameter("orderId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("toExportList");
            return;
        }
        if (orderId <= 0) {
            response.sendRedirect("toExportList");
            return;
        }

        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            response.sendRedirect("toExportList");
            return;
        }

        String receiptStatus =
                exportItemDAO.getExportReceiptStatusByOrderId(orderId);
        if ("COMPLETED".equalsIgnoreCase(receiptStatus)) {
            response.sendRedirect("exportDetail?orderId=" + orderId);
            return;
        }
        if (receiptStatus != null || !"NEW".equalsIgnoreCase(order.getStatus())) {
            response.sendRedirect("toExportList");
            return;
        }

        Order sessionOrder = (Order) session.getAttribute("order");
        if (sessionOrder == null || sessionOrder.getId() != orderId) {
            session.removeAttribute("scannedList");
        }

        List<OrderItemDetailDTO> pickingList =
                orderDAO.getOrderItemsByOrderId(orderId);
        session.setAttribute("order", order);
        session.setAttribute("pickingList", pickingList);

        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        double grandTotal = 0;
        if (scannedList != null) {
            for (ExportItemDTO item : scannedList) {
                grandTotal += item.getTotalCost();
            }
        }

        request.setAttribute("grandTotal", grandTotal);
        request.getRequestDispatcher("WEB-INF/view/export/exportProduct.jsp").forward(request, response);
    }

    /*
     * Backend reload version kept for reference.
     * Comment the active JSON doPost method before enabling this version.
     *
     * @Override
     * protected void doPost(HttpServletRequest request, HttpServletResponse response)
     *         throws ServletException, IOException {
     *     if (!AuthorizationUtils.checkAccess(
     *             request,
     *             response,
     *             PermissionConstants.PROCESS_EXPORT,
     *             "You don't have permission to process export products!")) {
     *         return;
     *     }
     *
     *     HttpSession session = request.getSession();
     *     String orderIdRaw = request.getParameter("orderId");
     *     String serial = request.getParameter("serial");
     *
     *     int orderId;
     *     try {
     *         orderId = Integer.parseInt(orderIdRaw);
     *     } catch (NumberFormatException e) {
     *         response.sendRedirect("toExportList");
     *         return;
     *     }
     *     if (orderId <= 0) {
     *         response.sendRedirect("toExportList");
     *         return;
     *     }
     *
     *     if (serial == null || serial.trim().isEmpty()) {
     *         session.setAttribute("error", "Serial number is required.");
     *         response.sendRedirect("exportProduct?orderId=" + orderId);
     *         return;
     *     }
     *     serial = serial.trim();
     *
     *     OrderDAO orderDAO = new OrderDAO();
     *     List<OrderItemDetailDTO> orderItems =
     *             orderDAO.getOrderItemsByOrderId(orderId);
     *     Map<String, Integer> requiredQuantities = new HashMap<>();
     *     for (OrderItemDetailDTO orderItem : orderItems) {
     *         if (orderItem.getSku() != null) {
     *             String sku = orderItem.getSku().toUpperCase(Locale.ROOT);
     *             int quantity = orderItem.getQuantity();
     *             if (requiredQuantities.get(sku) != null) {
     *                 quantity += requiredQuantities.get(sku);
     *             }
     *             requiredQuantities.put(sku, quantity);
     *         }
     *     }
     *     ExportItemDAO exportItemDAO = new ExportItemDAO();
     *
     *     synchronized (session) {
     *         List<ExportItemDTO> oldList =
     *                 (List<ExportItemDTO>) session.getAttribute("scannedList");
     *         List<ExportItemDTO> scannedList = new ArrayList<>();
     *         if (oldList != null) {
     *             scannedList.addAll(oldList);
     *         }
     *         ExportItemDTO product = exportItemDAO.getItemBySerial(serial, orderId);
     *         String errorMessage = validateScan(
     *                 product, serial, requiredQuantities, scannedList);
     *
     *         if (errorMessage == null) {
     *             ExportItemDTO addedItem = new ExportItemDTO();
     *             addedItem.setSku(product.getSku());
     *             addedItem.setName(product.getName());
     *             addedItem.setImgUrl(product.getImgUrl());
     *             addedItem.setStock(product.getStock());
     *             addedItem.setPrice(product.getPrice());
     *             addedItem.setSerial(product.getSerial());
     *             scannedList.add(0, addedItem);
     *             session.removeAttribute("error");
     *         } else {
     *             session.setAttribute("error", errorMessage);
     *         }
     *         session.setAttribute("scannedList", scannedList);
     *     }
     *
     *     response.sendRedirect("exportProduct?orderId=" + orderId);
     * }
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PROCESS_EXPORT,
                "You don't have permission to process export products!")) {
            return;
        }

        HttpSession session = request.getSession();
        ExportItemDAO exportItemDAO = new ExportItemDAO();
        OrderDAO orderDAO = new OrderDAO();
        String serial = request.getParameter("serial");
        String orderIdRaw = request.getParameter("orderId");

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdRaw);
        } catch (NumberFormatException e) {
            sendAjaxResponse(response, false, "Invalid order ID.", null, 0);
            return;
        }
        if (orderId <= 0) {
            sendAjaxResponse(response, false, "Invalid order ID.", null, 0);
            return;
        }

        Order order = orderDAO.getOrderById(orderId);
        String exportReceiptStatus = exportItemDAO.getExportReceiptStatusByOrderId(orderId);
        if (order == null) {
            sendAjaxResponse(response, false, "Order was not found.", null, 0);
            return;
        }
        if (exportReceiptStatus != null) {
            sendAjaxResponse(response, false,
                    "This order has already been exported.", null, 0);
            return;
        }
        if (!"NEW".equalsIgnoreCase(order.getStatus())) {
            sendAjaxResponse(response, false,
                    "This order is not available for export.", null, 0);
            return;
        }

        Order sessionOrder = (Order) session.getAttribute("order");
        if (sessionOrder == null || sessionOrder.getId() != orderId) {
            sendAjaxResponse(response, false,
                    "The selected order does not match the current export session.", null, 0);
            return;
        }

        if (serial == null || serial.trim().isEmpty()) {
            sendAjaxResponse(response, false, "Serial number is required.", null, 0);
            return;
        }
        serial = serial.trim();
        if (serial.length() > 100) {
            sendAjaxResponse(response, false,
                    "Serial number must not exceed 100 characters.", null, 0);
            return;
        }

        List<OrderItemDetailDTO> orderItems =
                orderDAO.getOrderItemsByOrderId(orderId);
        Map<String, Integer> requiredQuantities = new HashMap<>();
        if (orderItems != null) {
            for (OrderItemDetailDTO orderItem : orderItems) {
                if (orderItem.getSku() != null) {
                    String sku = orderItem.getSku().toUpperCase(Locale.ROOT);
                    int quantity = orderItem.getQuantity();
                    if (requiredQuantities.get(sku) != null) {
                        quantity += requiredQuantities.get(sku);
                    }
                    requiredQuantities.put(sku, quantity);
                }
            }
        }

        ExportItemDTO addedItem = null;
        String errorMessage;
        double grandTotal;

        synchronized (session) {
            List<ExportItemDTO> oldList =
                    (List<ExportItemDTO>) session.getAttribute("scannedList");
            List<ExportItemDTO> scannedList = new ArrayList<>();
            if (oldList != null) {
                scannedList.addAll(oldList);
            }

            int requiredTotalQuantity = 0;
            for (Integer quantity : requiredQuantities.values()) {
                requiredTotalQuantity += quantity;
            }
            int currentTotalScannedQty = 0;
            for (ExportItemDTO item : scannedList) {
                currentTotalScannedQty += item.getQty();
            }

            if (requiredTotalQuantity > 0 && currentTotalScannedQty >= requiredTotalQuantity) {
                errorMessage = "This order already has enough items to export.";
            } else {
                ExportItemDTO product = exportItemDAO.getItemBySerial(serial, orderId);
                errorMessage = validateScan(
                        product, serial, requiredQuantities, scannedList);

                if (errorMessage == null) {
                    addedItem = new ExportItemDTO();
                    addedItem.setSku(product.getSku());
                    addedItem.setName(product.getName());
                    addedItem.setImgUrl(product.getImgUrl());
                    addedItem.setStock(product.getStock());
                    addedItem.setPrice(product.getPrice());
                    addedItem.setSerial(product.getSerial());
                    scannedList.add(0, addedItem);
                }
            }

            session.setAttribute("scannedList", scannedList);
            grandTotal = 0;
            for (ExportItemDTO item : scannedList) {
                grandTotal += item.getTotalCost();
            }
        }

        boolean success = errorMessage == null;
        sendAjaxResponse(response, success, errorMessage, addedItem, grandTotal);
    }

    private String validateScan(ExportItemDTO product, String serial,
                                Map<String, Integer> requiredQuantities,
                                List<ExportItemDTO> scannedList) {
        if (product == null) {
            return "Serial number " + serial
                    + " is not available or is not included in this order.";
        }

        for (ExportItemDTO item : scannedList) {
            if (serial.equalsIgnoreCase(item.getSerial())) {
                return "Serial number " + serial + " was scanned already.";
            }
        }

        if (product.getSku() == null) {
            return product.getName() + " is not included in this order.";
        }
        String sku = product.getSku().toUpperCase(Locale.ROOT);
        Integer requiredQuantity = requiredQuantities.get(sku);
        if (requiredQuantity == null || requiredQuantity <= 0) {
            return product.getName() + " is not included in this order.";
        }

        int scannedQuantity = 0;
        for (ExportItemDTO item : scannedList) {
            if (product.getSku().equalsIgnoreCase(item.getSku())) {
                scannedQuantity += item.getQty();
            }
        }
        if (scannedQuantity >= requiredQuantity) {
            return "Cannot scan more " + product.getName()
                    + ". Required quantity: " + requiredQuantity + ".";
        }
        if (product.getStock() <= 0) {
            return product.getName() + " is currently out of stock.";
        }
        if (scannedQuantity >= product.getStock()) {
            return "Cannot add more " + product.getName()
                    + ". Available stock: " + product.getStock() + ".";
        }

        return null;
    }

    private void sendAjaxResponse(HttpServletResponse response, boolean success,
                                  String message, ExportItemDTO item,
                                  double grandTotal) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("success", success);
        if (message == null) {
            message = "";
        }
        json.addProperty("message", message);
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
            itemJson.addProperty("serial", item.getSerial());
            json.add("item", itemJson);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

}
