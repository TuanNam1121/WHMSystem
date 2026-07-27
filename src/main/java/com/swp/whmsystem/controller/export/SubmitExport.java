package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dal.ExportItemDAO;
import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.dto.OrderItemDetailDTO;
import com.swp.whmsystem.model.Order;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/submitExport")
public class SubmitExport extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PROCESS_EXPORT,
                "You don't have permission to process exports!")) {
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String orderIdRaw = request.getParameter("orderId");
        String[] tempIds = request.getParameterValues("tempIds");
        String[] serialNumbers = request.getParameterValues("sn");
        String submitAction = request.getParameter("submitAction");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        if (orderIdRaw == null || orderIdRaw.trim().isEmpty()) {
            response.sendRedirect("toExportList");
            return;
        }

        if (!"COMPLETE".equalsIgnoreCase(submitAction)) {
            session.setAttribute("error", "Invalid submit action.");
            response.sendRedirect("toExportList");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdRaw.trim());
            if (orderId <= 0) {
                response.sendRedirect("toExportList");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("toExportList");
            return;
        }

        @SuppressWarnings("unchecked")
        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        OrderDAO orderDAO = new OrderDAO();
        Order currentOrder = orderDAO.getOrderById(orderId);
        Order sessionOrder = (Order) session.getAttribute("order");

        if (currentOrder == null || !"NEW".equalsIgnoreCase(currentOrder.getStatus())) {
            session.setAttribute("error", "This order is not available for export.");
            response.sendRedirect("toExportList");
            return;
        }

        if (sessionOrder == null || sessionOrder.getId() != orderId) {
            session.setAttribute("error",
                    "The selected order does not match the current export session.");
            response.sendRedirect("toExportList");
            return;
        }

        if (scannedList == null) {
            session.setAttribute("error", "Invalid data submitted. Please check again!");
            response.sendRedirect("exportProduct?orderId=" + orderId);
            return;
        }

        if (!scannedList.isEmpty()) {
            if (tempIds == null || serialNumbers == null
                    || tempIds.length != serialNumbers.length
                    || tempIds.length != scannedList.size()) {
                session.setAttribute("error",
                        "Invalid data submitted. Please check again!");
                response.sendRedirect("exportProduct?orderId=" + orderId);
                return;
            }

            List<String> submittedTempIds = new ArrayList<>();
            for (int i = 0; i < tempIds.length; i++) {
                if (tempIds[i] == null || submittedTempIds.contains(tempIds[i])) {
                    session.setAttribute("error",
                            "Invalid product data submitted. Please scan the products again.");
                    response.sendRedirect("exportProduct?orderId=" + orderId);
                    return;
                }
                submittedTempIds.add(tempIds[i]);

                ExportItemDTO matchedItem = null;
                for (ExportItemDTO item : scannedList) {
                    if (tempIds[i].equals(item.getTempId())) {
                        matchedItem = item;
                        break;
                    }
                }
                if (matchedItem == null) {
                    session.setAttribute("error",
                            "Invalid product data submitted. Please scan the products again.");
                    response.sendRedirect("exportProduct?orderId=" + orderId);
                    return;
                }

                String serial = "";
                if (serialNumbers[i] != null) {
                    serial = serialNumbers[i].trim();
                }
                matchedItem.setSerial(serial);
            }
        }

        List<OrderItemDetailDTO> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
        String validationError = validateExport(scannedList, orderItems);
        if (validationError != null) {
            session.setAttribute("error", validationError);
            response.sendRedirect("exportProduct?orderId=" + orderId);
            return;
        }

        ExportItemDAO exportItemDAO = new ExportItemDAO();
        String result = exportItemDAO.processExportTransaction(
                orderId, user.getId(), scannedList);

        if ("SUCCESS".equals(result)) {
            session.removeAttribute("scannedList");
            session.setAttribute("successMessage", "Export successful!");
            response.sendRedirect("exportDetail?orderId=" + orderId);
            return;
        }

        session.setAttribute("error", result);
        response.sendRedirect("exportProduct?orderId=" + orderId);
    }

    private String validateExport(List<ExportItemDTO> scannedList,
                                  List<OrderItemDetailDTO> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return "This order does not contain any products.";
        }

        int requiredTotal = 0;
        for (OrderItemDetailDTO orderItem : orderItems) {
            if (orderItem.getSku() == null
                    || orderItem.getSku().trim().isEmpty()) {
                return "This order contains an invalid product.";
            }
            requiredTotal += orderItem.getQuantity();
        }

        if (scannedList.size() != requiredTotal) {
            return "The order requires " + requiredTotal
                    + " items, but you entered " + scannedList.size() + ".";
        }

        for (int i = 0; i < scannedList.size(); i++) {
            ExportItemDTO scannedItem = scannedList.get(i);
            if (scannedItem.getSku() == null
                    || scannedItem.getSku().trim().isEmpty()) {
                return scannedItem.getName() + " is not included in this order.";
            }

            boolean productInOrder = false;
            for (OrderItemDetailDTO orderItem : orderItems) {
                if (scannedItem.getSku().equalsIgnoreCase(orderItem.getSku())) {
                    productInOrder = true;
                    break;
                }
            }
            if (!productInOrder) {
                return scannedItem.getName() + " is not included in this order.";
            }

            String serial = scannedItem.getSerial();
            if (serial == null || serial.trim().isEmpty()) {
                return "Please enter all serial numbers.";
            }
            if (serial.trim().length() > 100) {
                return "Serial number must not exceed 100 characters.";
            }

            for (int j = 0; j < i; j++) {
                String previousSerial = scannedList.get(j).getSerial();
                if (previousSerial != null
                        && serial.trim().equalsIgnoreCase(previousSerial.trim())) {
                    return "Serial number " + serial
                            + " was entered more than once.";
                }
            }
        }

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItemDetailDTO orderItem = orderItems.get(i);
            boolean alreadyChecked = false;
            for (int j = 0; j < i; j++) {
                if (orderItem.getSku().equalsIgnoreCase(
                        orderItems.get(j).getSku())) {
                    alreadyChecked = true;
                    break;
                }
            }
            if (alreadyChecked) {
                continue;
            }

            int requiredQuantity = 0;
            for (OrderItemDetailDTO item : orderItems) {
                if (orderItem.getSku().equalsIgnoreCase(item.getSku())) {
                    requiredQuantity += item.getQuantity();
                }
            }

            int scannedQuantity = 0;
            for (ExportItemDTO item : scannedList) {
                if (orderItem.getSku().equalsIgnoreCase(item.getSku())) {
                    scannedQuantity++;
                }
            }

            if (scannedQuantity != requiredQuantity) {
                return orderItem.getName() + " requires " + requiredQuantity
                        + " items, but you entered " + scannedQuantity + ".";
            }
        }

        return null;
    }
}
