package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.dto.ExportItemDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/removeItem")
public class RemoveItem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tempId = request.getParameter("tempId");

        List<ExportItemDTO> scannedList = (List<ExportItemDTO>) session.getAttribute("scannedList");
        if (scannedList != null && tempId != null && !tempId.trim().isEmpty()) {
            ExportItemDTO itemToRemove = null;
            for (ExportItemDTO item : scannedList) {
                if (tempId.equals(item.getTempId())) {
                    itemToRemove = item;
                    break;
                }
            }
            if (itemToRemove != null) {
                scannedList.remove(itemToRemove);
            }
            session.setAttribute("scannedList", scannedList);
        }
        response.sendRedirect("exportProduct");
    }
}