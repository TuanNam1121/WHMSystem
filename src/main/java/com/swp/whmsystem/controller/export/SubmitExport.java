package com.swp.whmsystem.controller.export; // Check lại package cho đúng

import com.swp.whmsystem.dal.ExportItemDAO;
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

        // 1. Lấy dữ liệu từ Form (Nút Select và các ô Input)
        String status = request.getParameter("status");
        String[] tempIds = request.getParameterValues("tempIds");
        String[] serialNumbers = request.getParameterValues("sn");

        // 2. Lấy dữ liệu từ Session ra
        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        Order currentOrder = (Order) session.getAttribute("order");

        // 3. Validate cơ bản: Đảm bảo dữ liệu không bị null và độ dài 2 mảng bằng nhau
        if (scannedList != null && currentOrder != null &&
                tempIds != null && serialNumbers != null && tempIds.length == serialNumbers.length) {

            // 4. Lắp S/N người dùng nhập vào đúng sản phẩm trong danh sách
            for (int i = 0; i < tempIds.length; i++) {
                String idToFind = tempIds[i];
                String snToSet = serialNumbers[i];

                // Quét danh sách tìm sản phẩm khớp id thì nhét S/N vào
                for (ExportItemDTO item : scannedList) {
                    if (idToFind.equals(item.getTempId())) {
                        item.setSerial(snToSet);
                        break;
                    }
                }
            }

            // --- KHU VỰC IN RA CONSOLE ĐỂ BRO TEST TRƯỚC KHI LÀM DATABASE ---
            System.out.println("=== CHỐT ĐƠN XUẤT KHO ===");
            System.out.println("Order ID: " + currentOrder.getId());
            System.out.println("Trạng thái: " + status);
            for (ExportItemDTO item : scannedList) {
                System.out.println("- SKU: " + item.getSku() + " | S/N: " + item.getSerial());
            }
            System.out.println("=========================");

            // 5. GỌI DAO ĐỂ LƯU VÀO DATABASE BẰNG TRANSACTION TẠI ĐÂY
            ExportItemDAO orderDAO = new ExportItemDAO();
            boolean isSuccess = orderDAO.processExportTransaction(currentOrder.getId(), scannedList, status);

            if (isSuccess) {
                // 6. Dọn dẹp sạch sẽ Session sau khi thành công
                session.removeAttribute("scannedList");
                session.removeAttribute("order"); // Xóa luôn order nếu không cần giữ lại

                // (Tùy chọn) Có thể set 1 cái session successMessage để JSP hiện thông báo xanh lá
                session.setAttribute("successMessage", "Xuất kho thành công!");

                // Chuyển về trang danh sách đơn hàng hoặc trang nào bro muốn
                response.sendRedirect("exportProduct");
            } else {
                session.setAttribute("error", "Lỗi lưu Database. Vui lòng thử lại!");
                response.sendRedirect("exportProduct");
            }

        } else {
            session.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại!");
            response.sendRedirect("exportProduct");
        }
    }
}