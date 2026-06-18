package com.swp.whmsystem.controller.supplier;

import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.model.Supplier;
import com.swp.whmsystem.utils.UserFormValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateSupplier", urlPatterns = {"/updateSupplier"})
public class updateSupplier extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("listSupplier");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            SupplierDAO supplierDAO = new SupplierDAO();
            Supplier supplier = supplierDAO.getSupplierById(id);
            if (supplier == null) {
                response.sendRedirect("listSupplier");
                return;
            }
            request.setAttribute("supplier", supplier);
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("listSupplier");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("supplierId");
        String supplierName = request.getParameter("supplierName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String isActiveStr = request.getParameter("isActive");
        
        int supplierId = 0;
        try {
            supplierId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("listSupplier");
            return;
        }

        SupplierDAO supplierDAO = new SupplierDAO();
        Supplier existingSupplierByName = supplierDAO.getSupplierByName(supplierName);
        if (existingSupplierByName != null && existingSupplierByName.getSupplierId() != supplierId) {
            request.setAttribute("error", "The supplier name has already existed! Please input another one!");
            // Set id parameter back so doGet works correctly
            request.setAttribute("id", String.valueOf(supplierId));
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
            return;
        }

        if (phone.length() > 10 || !UserFormValidation.isValidPhone(phone)) {
            request.setAttribute("error", "Invalid phone number format. It must be 10 digits.");
            request.setAttribute("id", String.valueOf(supplierId));
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
            return;
        }

        if (email.length() > 50 || !UserFormValidation.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format or email is too long.");
            request.setAttribute("id", String.valueOf(supplierId));
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierId);
        supplier.setSupplierName(supplierName);
        supplier.setPhone(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setActive("1".equals(isActiveStr) || "true".equalsIgnoreCase(isActiveStr));

        boolean isSuccess = false;
        try {
            isSuccess = supplierDAO.updateSupplier(supplier);
        } catch (Exception e) {
            request.setAttribute("error", "Database error occurred while updating supplier: " + e.getMessage());
            request.setAttribute("id", String.valueOf(supplierId));
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
            return;
        }

        if (isSuccess) {
            request.getSession().setAttribute("message", "Supplier updated successfully!");
            response.sendRedirect("listSupplier");
        } else {
            request.setAttribute("error", "Failed to update supplier. Please try again.");
            request.setAttribute("id", String.valueOf(supplierId));
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Update Supplier Servlet";
    }
}