package com.swp.whmsystem.controller.supplier;

import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.model.Supplier;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.UserFormValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UpdateSupplier", urlPatterns = {"/updateSupplier"})
public class UpdateSupplier extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.UPDATE_SUPPLIER,
                "You are not authorized to Update suppliers.")) {
            return;
        }

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.UPDATE_SUPPLIER,
                "You are not authorized to Update suppliers.")) {
            return;
        }

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
            request.setAttribute("id", String.valueOf(supplierId));
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
            return;
        }

        Supplier existingSupplierByPhone = supplierDAO.getSupplierByPhone(phone);
        if (existingSupplierByPhone != null && existingSupplierByPhone.getSupplierId() != supplierId) {
            request.setAttribute("error", "The phone number has already existed! Please input another one!");
            request.setAttribute("id", supplierId);
            request.getRequestDispatcher("/WEB-INF/view/supplier/updateSupplier.jsp").forward(request, response);
            return;
        }

        Supplier existingSupplierByEmail = supplierDAO.getSupplierByEmail(email);
        if (existingSupplierByEmail != null && existingSupplierByEmail.getSupplierId() != supplierId) {
            request.setAttribute("error", "The email has already existed! Please input another one!");
            request.setAttribute("id", supplierId);
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
            request.setAttribute("error", "Invalid email format.");
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
        supplier.setActive("true".equalsIgnoreCase(isActiveStr));

        boolean isSuccess = supplierDAO.updateSupplier(supplier);

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