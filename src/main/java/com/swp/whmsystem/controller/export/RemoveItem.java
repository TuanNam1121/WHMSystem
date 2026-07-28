package com.swp.whmsystem.controller.export;

import com.google.gson.JsonObject;
import com.swp.whmsystem.dto.ExportItemDTO;
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

@WebServlet("/removeItem")
public class RemoveItem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PROCESS_EXPORT,
                "You don't have permission to process exports!")) {
            return;
        }

        HttpSession session = request.getSession();
        String tempId = request.getParameter("tempId");
        boolean removed = false;
        double grandTotal = 0;

        if (tempId == null || tempId.trim().isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty("success", false);
            json.addProperty("message",
                    "Product was not found in the scanned list.");
            json.addProperty("grandTotal", grandTotal);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            return;
        }

        synchronized (session) {
            List<ExportItemDTO> scannedList =
                    (List<ExportItemDTO>) session.getAttribute("scannedList");

            if (scannedList != null) {
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

        String message = "";
        if (!removed) {
            message = "Product was not found in the scanned list.";
        }
        JsonObject json = new JsonObject();
        json.addProperty("success", removed);
        json.addProperty("message", message);
        json.addProperty("grandTotal", grandTotal);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
