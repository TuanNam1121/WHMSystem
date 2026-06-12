package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DetailPurchaseRequest", urlPatterns = {"/detailPurchaseRequest"})
public class DetailPurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("purchaseRequestList");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            PurchaseRequestDAO prDAO = new PurchaseRequestDAO();
            PurchaseRequest pr = prDAO.getPurchaseRequestById(id);

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
            
            request.getRequestDispatcher("WEB-INF/view/purchaseRequest/detailPurchaseRequest.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("purchaseRequestList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Detail Purchase Request Controller";
    }
}
