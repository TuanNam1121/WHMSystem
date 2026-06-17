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
        if (user.getRoleId() != 4) {
            response.sendRedirect("home");
        }

        String idStr = request.getParameter("id");
        if (idStr == null) {
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

            ProductDAO productDAO = new ProductDAO();
            List<Product> productList = productDAO.getProductList();
            
            Map<Integer, Product> productMap = new HashMap<>();
            for (Product p : productList) {
                productMap.put(p.getProductId(), p);
            }

            request.setAttribute("purchaseRequest", pr);
            request.setAttribute("purchaseItems", items);
            request.setAttribute("productListForPurchase", productList);
            request.setAttribute("productMap", productMap);
            
            request.getRequestDispatcher("WEB-INF/view/purchaseRequest/updatePurchaseRequest.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("purchaseRequestList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String note = request.getParameter("note");

            PurchaseRequestDAO prDAO = new PurchaseRequestDAO();
            PurchaseRequest pr = prDAO.getPurchaseRequestById(requestId);
            
            if (pr != null && pr.getStatus().equalsIgnoreCase("NEW")) {
                pr.setNote(note);
                prDAO.updatePurchaseRequest(pr);

                PurchaseItemDAO piDAO = new PurchaseItemDAO();
                List<PurchaseItem> existingItems = piDAO.getItemsByPurchaseRequestId(requestId);
                for (PurchaseItem pi : existingItems) {
                    piDAO.deletePurchaseItem(pi.getId());
                }

                int i = 0;
                while (true) {
                    String productIdStr = request.getParameter("selectedId" + i);
                    String qtyStr = request.getParameter("selectedQty" + i);
                    if (productIdStr == null || qtyStr == null) {
                        break;
                    }
                    try {
                        int productId = Integer.parseInt(productIdStr);
                        int quantity = Integer.parseInt(qtyStr);

                        PurchaseItem purchaseItem = new PurchaseItem();
                        purchaseItem.setPurchaseRequestId(pr.getId());
                        purchaseItem.setProductId(productId);
                        purchaseItem.setRequiredQty(quantity);
                        piDAO.insertPurchaseItem(purchaseItem);
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    i++;
                }
                request.getSession().setAttribute("message", "Purchase request updated successfully!");
            } else {
                request.getSession().setAttribute("error", "Cannot update this purchase request.");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error updating purchase request.");
        }
        response.sendRedirect("purchaseRequestList");
    }

    @Override
    public String getServletInfo() {
        return "Update Purchase Request Controller";
    }
}