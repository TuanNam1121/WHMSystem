package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.SupplierDAO;
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

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreatePurchaseRequest", urlPatterns = {"/createPurchaseRequest"})
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
        if (user.getRoleId() != 4) {
            response.sendRedirect("home");
        }

        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getProductList();
        request.setAttribute("productListForPurchase", productList);

        SupplierDAO supplierDAO = new SupplierDAO();
        request.setAttribute("supplierList", supplierDAO.getActiveSuppliers());

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/createPurchaseRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        PurchaseRequest p = new PurchaseRequest();
        p.setCreatedBy(salesmanId);
        p.setNote(note);
        p.setSupplierId(supplierId);

        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        purchaseRequestDAO.insertPurchaseRequest(p);
        p = purchaseRequestDAO.getLatestPurchaseRequestBySalemanId(salesmanId);

        int i = 0;
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

                PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
                purchaseItemDAO.insertPurchaseItem(purchaseItem);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Quantity error!");
                request.getRequestDispatcher("createPurchaseRequest").forward(request, response);
                return;
            }
            i++;
        }

        request.getSession().setAttribute("message", "Create a purchase request successfully! Wait for the confirmation by manager!");
        response.sendRedirect("purchaseRequestList");
    }

}

