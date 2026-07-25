package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreatePurchaseRequest", urlPatterns = { "/createPurchaseRequest" })
public class CreatePurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_PURCHASE_ORDER,
                "You don't have permission to create a purchase request!")) {
            return;
        }

        //from supplier list
        SupplierDAO supplierDAO = new SupplierDAO();
        String supplierIdParam = request.getParameter("supplierId");
        if (supplierIdParam != null && !supplierIdParam.trim().isEmpty()) {
            try {
                int supplierId = Integer.parseInt(supplierIdParam);
                Supplier supplier = supplierDAO.getSupplierById(supplierId);
                if (supplier == null || !supplier.isActive()) {
                    session.setAttribute("error", "The selected supplier is inactive or does not exist! Can not create purchase request with this supplier!");
                    response.sendRedirect("ListSupplier");
                    return;
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Invalid supplier ID!");
                response.sendRedirect("ListSupplier");
                return;
            }
        }

        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getActiveProductList();

        request.setAttribute("productListForPurchase", productList);
        request.setAttribute("supplierList", supplierDAO.getActiveSuppliers());

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/createPurchaseRequest.jsp").forward(request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_PURCHASE_ORDER,
                "You don't have permission to create a purchase request!")) {
            return;
        }

        int salesmanId = Integer.parseInt(request.getParameter("salesmanId"));
        String note = request.getParameter("note");

        int supplierId = 0;
        try {
            supplierId = Integer.parseInt(request.getParameter("supplierId"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please select a supplier!");
            doGet(request, response);
            return;
        }

        int i = 0;
        while (true) {
            String productIdStr = request.getParameter("selectedId" + i);
            String qtyStr = request.getParameter("selectedQty" + i);
            String priceStr = request.getParameter("selectedPrice" + i);
            if (productIdStr == null || qtyStr == null || priceStr == null) {
                if (i == 0) {
                    request.setAttribute("error", "Please select at least one product!");
                    doGet(request, response);
                    return;
                }
                break;
            }
            try {
                int quantity = Integer.parseInt(qtyStr);
                int price = Integer.parseInt(priceStr);
                if (quantity < 1) {
                    request.setAttribute("error", "Quantity for product must be at least 1!");
                    doGet(request, response);
                    return;
                }
                if (price < 1000) {
                    request.setAttribute("error", "Price for product must be at least 1000 VND!");
                    doGet(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Error occured!");
                request.getRequestDispatcher("WEB-INF/view/purchaseRequest/createPurchaseRequest.jsp").forward(request,
                        response);
                return;
            }
            i++;
        }

        PurchaseRequest p = new PurchaseRequest();
        p.setCreatedBy(salesmanId);
        p.setNote(note);
        p.setSupplierId(supplierId);

        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        purchaseRequestDAO.insertPurchaseRequest(p);
        p = purchaseRequestDAO.getLatestPurchaseRequestBySalemanId(salesmanId);

        i = 0;
        PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
        while (true) {
            String productIdStr = request.getParameter("selectedId" + i);
            String qtyStr = request.getParameter("selectedQty" + i);
            String priceStr = request.getParameter("selectedPrice" + i);
            if (productIdStr == null || qtyStr == null || priceStr == null) {
                break;
            }
            try {
                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(qtyStr);
                int price = Integer.parseInt(priceStr);

                PurchaseItem purchaseItem = new PurchaseItem();
                purchaseItem.setPurchaseRequestId(p.getId());
                purchaseItem.setProductId(productId);
                purchaseItem.setRequiredQty(quantity);
                purchaseItem.setPrice(price);

                purchaseItemDAO.insertPurchaseItem(purchaseItem);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
            i++;
        }

        request.getSession().setAttribute("message",
                "Create a purchase request successfully! Wait for the confirmation by manager!");
        response.sendRedirect("purchaseRequestList");
    }

}
