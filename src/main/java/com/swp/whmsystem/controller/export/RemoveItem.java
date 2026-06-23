package com.swp.whmsystem.controller.export;

import com.google.gson.JsonObject;
import com.swp.whmsystem.dto.ExportItemDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/removeItem")
public class RemoveItem extends HttpServlet {

    /*
     * CODE CŨ: Xoá sản phẩm trong session rồi reload lại toàn bộ trang.
     *
     * @Override
     * protected void doGet(HttpServletRequest request, HttpServletResponse response)
     *         throws ServletException, IOException {
     *     HttpSession session = request.getSession();
     *     String tempId = request.getParameter("tempId");
     *     String orderId = request.getParameter("orderId");
     *
     *     synchronized (session) {
     *         List<ExportItemDTO> scannedList =
     *                 (List<ExportItemDTO>) session.getAttribute("scannedList");
     *
     *         if (scannedList != null && tempId != null
     *                 && !tempId.trim().isEmpty()) {
     *             List<ExportItemDTO> updatedList =
     *                     new ArrayList<>(scannedList);
     *             updatedList.removeIf(
     *                     item -> tempId.equals(item.getTempId()));
     *             session.setAttribute("scannedList", updatedList);
     *             session.removeAttribute("error");
     *         }
     *     }
     *
     *     if (orderId == null || orderId.trim().isEmpty()) {
     *         response.sendRedirect("toExportList");
     *     } else {
     *         response.sendRedirect(
     *                 "exportProduct?orderId=" + orderId);
     *     }
     * }
     */

    // CODE MỚI: Xoá trong session và trả JSON, không reload trang.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tempId = request.getParameter("tempId");
        boolean removed = false;
        double grandTotal = 0;

        synchronized (session) {
            List<ExportItemDTO> scannedList =
                    (List<ExportItemDTO>) session.getAttribute("scannedList");

            if (scannedList != null && tempId != null && !tempId.trim().isEmpty()) {
                List<ExportItemDTO> updatedList = new ArrayList<>(scannedList);
                for (ExportItemDTO item : scannedList) {
                    if (tempId.equals(item.getTempId())) {
                        updatedList.remove(item);
                        removed = true;
                        break;
                    }
                }

                session.setAttribute("scannedList", updatedList);

                for (ExportItemDTO item : updatedList) {
                    grandTotal += item.getTotalCost();
                }
            }
        }

        JsonObject json = new JsonObject();
        json.addProperty("success", removed);
        json.addProperty("message",
                removed ? "" : "Product was not found in the scanned list.");
        json.addProperty("grandTotal", grandTotal);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
