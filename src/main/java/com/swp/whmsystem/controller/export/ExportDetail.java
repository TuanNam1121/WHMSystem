package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dal.ExportItemDAO;
import com.swp.whmsystem.dal.OrderDAO;
import com.swp.whmsystem.dto.ExportDetailItemDTO;
import com.swp.whmsystem.dto.ExportReceiptInfoDTO;
import com.swp.whmsystem.model.Order;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/exportDetail")
public class ExportDetail extends HttpServlet {

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

                ExportItemDAO exportItemDAO = new ExportItemDAO();
                List<ExportDetailItemDTO> detailList = exportItemDAO.getExportedItemsByOrderId(orderId);
                String exportReceiptStatus = exportItemDAO.getExportReceiptStatusByOrderId(orderId);
                ExportReceiptInfoDTO exportReceiptInfo = exportItemDAO.getExportReceiptInfoByOrderId(orderId);

                if (order != null && exportReceiptStatus != null) {
                    order.setStatus(exportReceiptStatus);
                    double grandTotal = 0.0;
                    for (ExportDetailItemDTO item : detailList) {
                        grandTotal += item.getPrice();
                    }
                    session.setAttribute("order", order);
                    session.setAttribute("itemList", detailList);
                    request.setAttribute("grandTotal", grandTotal);
                    request.setAttribute("exportReceiptInfo", exportReceiptInfo);
                    request.getRequestDispatcher("WEB-INF/view/export/exportDetail.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi parse ID xem chi tiết: " + e.getMessage());
            }
        }

        response.sendRedirect("exportHistory");
    }
}
