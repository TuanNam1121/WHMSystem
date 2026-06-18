package com.swp.whmsystem.controller.supplier;

import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.model.Supplier;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.swp.whmsystem.utils.UserFormValidation;

@WebServlet(name = "CreateSupplier", urlPatterns = {"/createSupplier"})
public class createSupplier extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/supplier/createSupplier.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String supplierName = request.getParameter("supplierName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        SupplierDAO supplierDAO = new SupplierDAO();
        if (supplierDAO.getSupplierByName(supplierName) != null) {
            request.setAttribute("error", "The supplier name has already existed! Please input another one!");
            doGet(request, response);
            return;
        }

        if (phone.length() > 10 || !UserFormValidation.isValidPhone(phone)) {
            request.setAttribute("error", "Invalid phone number format. It must be 10 digits.");
            doGet(request, response);
            return;
        }

        if (email.length() > 50 || !UserFormValidation.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            doGet(request, response);
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierName);
        supplier.setPhone(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);

        SupplierDAO dao = new SupplierDAO();
        boolean isSuccess = dao.insertSupplier(supplier);

        if (isSuccess) {
            request.getSession().setAttribute("message", "Supplier added successfully!");
            response.sendRedirect("listSupplier");
        } else {
            request.setAttribute("error", "Failed to add supplier. Please try again.");
            request.getRequestDispatcher("WEB-INF/view/supplier/createSupplier.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Create Supplier Servlet";
    }
}