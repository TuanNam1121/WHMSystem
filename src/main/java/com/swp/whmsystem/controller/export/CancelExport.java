package com.swp.whmsystem.controller.export;

import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cancelExport")
public class CancelExport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PROCESS_EXPORT,
                "You don't have permission to process exports!")) {
            return;
        }

        HttpSession session = request.getSession();
        session.removeAttribute("scannedList");
        session.removeAttribute("error");
        response.sendRedirect("toExportList");
    }
}
