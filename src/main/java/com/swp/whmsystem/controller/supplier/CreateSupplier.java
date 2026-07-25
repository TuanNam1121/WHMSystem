package com.swp.whmsystem.controller.supplier;

import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.model.Supplier;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import com.swp.whmsystem.utils.UserFormValidation;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CreateSupplier", urlPatterns = {"/createSupplier"})
public class CreateSupplier extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_SUPPLIER,
                "You are not authorized to Create suppliers.")) {
            return;
        }

        request.getRequestDispatcher("WEB-INF/view/supplier/createSupplier.jsp").forward(request, response);
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
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_SUPPLIER,
                "You are not authorized to Create suppliers.")) {
            return;
        }

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

        if (supplierDAO.getSupplierByPhone(phone) != null) {
            request.setAttribute("error", "The phone number has already existed! Please input another one!");
            doGet(request, response);
            return;
        }

        if (supplierDAO.getSupplierByEmail(email) != null) {
            request.setAttribute("error", "The email has already existed! Please input another one!");
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
            session.setAttribute("message", "Supplier added successfully!");
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