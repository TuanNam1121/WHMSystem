package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dal.ExportItemDAO;
import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dto.ExportItemDTO;
import com.swp.whmsystem.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/submitExport")
public class SubmitExport extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 1. ÉP CỨNG STATUS LUÔN LÀ DOING
        String status = "DOING";

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
        Order currentOrder = new OrderDAO().getOrderById(orderId);

        if (scannedList != null && currentOrder != null &&
                tempIds != null && serialNumbers != null && tempIds.length == serialNumbers.length) {

            for (int i = 0; i < tempIds.length; i++) {
                String idToFind = tempIds[i];
                String snToSet = serialNumbers[i];

                for (ExportItemDTO item : scannedList) {
                    if (idToFind.equals(item.getTempId())) {
                        item.setSerial(snToSet);
                        break;
                    }
                }
            }

            ExportItemDAO exportItemDAO = new ExportItemDAO();
            // 2. GỌI DAO VÀ HỨNG KẾT QUẢ TRẢ VỀ LÀ DẠNG STRING
            String result = exportItemDAO.processExportTransaction(orderId, scannedList, status);

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
}
