package com.swp.whmsystem.controller.export;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tempId = request.getParameter("tempId");
        String orderId = request.getParameter("orderId");

        synchronized (session) {
            List<ExportItemDTO> scannedList =
                    (List<ExportItemDTO>) session.getAttribute("scannedList");

            if (scannedList != null && tempId != null && !tempId.trim().isEmpty()) {
                List<ExportItemDTO> updatedList = new ArrayList<>(scannedList);
                updatedList.removeIf(item -> tempId.equals(item.getTempId()));
                session.setAttribute("scannedList", updatedList);
                session.removeAttribute("error");
            }
        }
        if (orderId == null || orderId.trim().isEmpty()) {
            response.sendRedirect("toExportList");
        } else {
            response.sendRedirect("exportProduct?orderId=" + orderId);
        }
    }
}
