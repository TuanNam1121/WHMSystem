package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UpdatePurchaseRequest", urlPatterns = {"/updatePurchaseRequest"})
public class UpdatePurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.UPDATE_PURCHASE_ORDER,
                "You don't have permission to update a purchase request!")) {
            return;
        }

        String idStr = request.getParameter("requestId");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("purchaseRequestList");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            PurchaseRequestDAO prDAO = new PurchaseRequestDAO();
            PurchaseRequest pr = prDAO.getPurchaseRequestById(id);
            
            if (pr == null || !pr.getStatus().equalsIgnoreCase("NEW")) {
                response.sendRedirect("purchaseRequestList");
                return;
            }

            PurchaseItemDAO piDAO = new PurchaseItemDAO();
            List<PurchaseItem> items = piDAO.getItemsByPurchaseRequestId(id);

//            String productSearch = request.getParameter("productSearch");
            
            ProductDAO productDAO = new ProductDAO();
            List<Product> productList;
//            if (productSearch == null || productSearch.isEmpty()) {
//                productList = productDAO.getActiveProductList();
//            } else {
//                productList = productDAO.searchActiveProductByName(productSearch);
//            }
            productList = productDAO.getActiveProductList();
            
            Map<Integer, Product> productMap = new HashMap<>();
            for (Product p : productList) {
                productMap.put(p.getProductId(), p);
            }
            
            request.setAttribute("purchaseRequest", pr);
            request.setAttribute("purchaseItems", items);
            request.setAttribute("productListForPurchase", productList);
            
            com.swp.whmsystem.dal.SupplierDAO supplierDAO = new com.swp.whmsystem.dal.SupplierDAO();
            request.setAttribute("supplierList", supplierDAO.getActiveSuppliers());

            request.setAttribute("productMap", productMap);

            request.getRequestDispatcher("WEB-INF/view/purchaseRequest/updatePurchaseRequest.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("purchaseRequestList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.UPDATE_PURCHASE_ORDER,
                "You don't have permission to update a purchase request!")) {
            return;
        }
        try {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String note = request.getParameter("note");

            PurchaseRequestDAO prDAO = new PurchaseRequestDAO();
            PurchaseRequest pr = prDAO.getPurchaseRequestById(requestId);
            
            if (pr != null && pr.getStatus().equalsIgnoreCase("NEW")) {
                String supplierIdStr = request.getParameter("supplierId");
                if (supplierIdStr != null && !supplierIdStr.trim().isEmpty()) {
                    pr.setSupplierId(Integer.parseInt(supplierIdStr));
                }
                pr.setNote(note);
                prDAO.updatePurchaseRequest(pr);

                // Validate
                int i = 0;
                boolean hasItems = false;
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
                        request.setAttribute("error", "Invalid number format!");
                        doGet(request, response);
                        return;
                    }
                    hasItems = true;
                    i++;
                }

                // Delete existing items and insert new ones
                PurchaseItemDAO piDAO = new PurchaseItemDAO();
                piDAO.deletePurchaseItemByRequestId(requestId);

                i = 0;
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
                        purchaseItem.setPurchaseRequestId(pr.getId());
                        purchaseItem.setProductId(productId);
                        purchaseItem.setRequiredQty(quantity);
                        purchaseItem.setPrice(price);
                        piDAO.insertPurchaseItem(purchaseItem);
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    i++;
                }
                request.getSession().setAttribute("message", "Purchase request updated successfully!");
            } else {
                request.getSession().setAttribute("error", "Cannot update this purchase request.");
                doGet(request, response);
                return;
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error updating purchase request.");
            doGet(request, response);
            return;
        }
        response.sendRedirect("purchaseRequestList");
    }

    @Override
    public String getServletInfo() {
        return "Update Purchase Request Controller";
    }
}