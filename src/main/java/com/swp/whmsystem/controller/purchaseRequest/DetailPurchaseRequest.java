package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DetailPurchaseRequest", urlPatterns = {"/detailPurchaseRequest"})
public class DetailPurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_PURCHASE_ORDER,
                "You don't have permission to view your purchase requests!")) {
            return;
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

            PurchaseItemDAO piDAO = new PurchaseItemDAO();
            List<PurchaseItem> items = piDAO.getItemsByPurchaseRequestId(id);

            ProductDAO productDAO = new ProductDAO();

            List<Product> orderedProducts = new ArrayList<>();
            long totalAmount = 0;
            for (PurchaseItem item : items) {
                Product p = productDAO.getProductFromId(item.getProductId());
                orderedProducts.add(p);
                totalAmount += (long) item.getPrice() * item.getRequiredQty();
            }

            request.setAttribute("purchaseRequest", pr);
            request.setAttribute("purchaseItems", items);
            request.setAttribute("orderedProducts", orderedProducts);
            request.setAttribute("totalAmount", totalAmount);

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
