package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dal.ExportItemDAO;
import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.dto.OrderItemDetailDTO;
import com.swp.whmsystem.model.Order;

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

        HttpSession session = request.getSession();

        String orderIdRaw = request.getParameter("orderId");
        String[] tempIds = request.getParameterValues("tempIds");
        String[] serialNumbers = request.getParameterValues("sn");

        if (orderIdRaw == null || orderIdRaw.trim().isEmpty()) {
            response.sendRedirect("toExportList");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdRaw.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("toExportList");
            return;
        }

        @SuppressWarnings("unchecked")
        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        OrderDAO orderDAO = new OrderDAO();
        Order currentOrder = orderDAO.getOrderById(orderId);

        if (scannedList != null && currentOrder != null &&
                tempIds != null && serialNumbers != null && tempIds.length == serialNumbers.length) {

            for (int i = 0; i < tempIds.length; i++) {
                String idToFind = tempIds[i];
                String snToSet = serialNumbers[i];

                for (ExportItemDTO item : scannedList) {
                    if (idToFind.equals(item.getTempId())) {
                        item.setSerial(snToSet == null ? "" : snToSet.trim());
                        break;
                    }
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
            // 2. GỌI DAO VÀ HỨNG KẾT QUẢ TRẢ VỀ LÀ DẠNG STRING
            String result = exportItemDAO.processExportTransaction(orderId, scannedList);

            if ("SUCCESS".equals(result)) {
                session.removeAttribute("scannedList");
                // (Tùy chọn) Xóa session order nếu muốn quay về danh sách trống
                // session.removeAttribute("order");

                session.setAttribute("successMessage", "Export successful!");
                response.sendRedirect("exportProduct?orderId=" + orderId);
            } else {
                // NẾU CÓ LỖI (S/N KHÔNG AVAILABLE), NÉM CHÍNH XÁC LỖI ĐÓ LÊN MÀN HÌNH
                session.setAttribute("error", result);
                response.sendRedirect("exportProduct?orderId=" + orderId);
            }

        } else {
            session.setAttribute("error", "Invalid data submitted. Please check again!");
            response.sendRedirect("exportProduct?orderId=" + orderId);
        }
    }

    private String validateExport(List<ExportItemDTO> scannedList,
                                  List<OrderItemDetailDTO> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return "This order does not contain any products.";
        }

        List<String> orderSkus = new ArrayList<>();
        List<String> orderProductNames = new ArrayList<>();
        List<Integer> orderQuantities = new ArrayList<>();

        for (OrderItemDetailDTO orderItem : orderItems) {
            int index = orderSkus.indexOf(orderItem.getSku());

            if (index == -1) {
                orderSkus.add(orderItem.getSku());
                orderProductNames.add(orderItem.getName());
                orderQuantities.add(orderItem.getQuantity());
            } else {
                orderQuantities.set(index,
                        orderQuantities.get(index) + orderItem.getQuantity());
            }
        }

        int requiredQuantity = 0;
        for (int quantity : orderQuantities) {
            requiredQuantity += quantity;
        }

        if (scannedList.size() != requiredQuantity) {
            return "The order requires " + requiredQuantity
                    + " items, but you entered " + scannedList.size() + ".";
        }

        for (int i = 0; i < orderSkus.size(); i++) {
            int scannedQuantity = 0;

            for (ExportItemDTO scannedItem : scannedList) {
                if (orderSkus.get(i).equalsIgnoreCase(scannedItem.getSku())) {
                    scannedQuantity++;
                }
            }

            if (scannedQuantity != orderQuantities.get(i)) {
                return orderProductNames.get(i) + " requires " + orderQuantities.get(i)
                        + " items, but you entered " + scannedQuantity + ".";
            }
        }

        List<String> usedSerials = new ArrayList<>();

        for (ExportItemDTO scannedItem : scannedList) {
            if (!orderSkus.contains(scannedItem.getSku())) {
                return scannedItem.getName() + " is not included in this order.";
            }

            String serial = scannedItem.getSerial();
            if (serial == null || serial.trim().isEmpty()) {
                return "Please enter all serial numbers.";
            }

            String normalizedSerial = serial.trim().toUpperCase();
            if (usedSerials.contains(normalizedSerial)) {
                return "Serial number " + serial + " was entered more than once.";
            }
            usedSerials.add(normalizedSerial);
        }

        return null;
    }
}
