package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "ManagerPurchaseRequestDetail", urlPatterns = {"/managerPurchaseRequestDetail"})
public class ManagerPurchaseRequestDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("managerPurchaseRequestList");
            return;
        }
        
        int id = Integer.parseInt(idStr);
        PurchaseRequestDAO prDao = new PurchaseRequestDAO();
        PurchaseItemDAO piDao = new PurchaseItemDAO();
        ProductDAO pDao = new ProductDAO();
        UserDAO userDao = new UserDAO();
        
        PurchaseRequest pr = prDao.getPurchaseRequestById(id);
        if (pr == null) {
            response.sendRedirect("managerPurchaseRequestList");
            return;
        }
        
        User salesman = userDao.getUserFromId(pr.getCreatedBy());
        List<PurchaseItem> items = piDao.getItemsByPurchaseRequestId(id);
        
        Map<Integer, Product> productMap = new HashMap<>();
        for (PurchaseItem item : items) {
            Product p = pDao.getProductFromId(item.getProductId());
            if (p != null) {
                productMap.put(item.getProductId(), p);
            }
        }
        
        // Fetch all warehouse staffs. Assuming Role 5 is WAREHOUSE_PROCESSOR as per RoleDAO
        List<User> warehouseStaffs = userDao.searchUser(null, "5", null);
        
        request.setAttribute("purchaseRequest", pr);
        request.setAttribute("salesman", salesman);
        request.setAttribute("purchaseItems", items);
        request.setAttribute("productMap", productMap);
        request.setAttribute("warehouseStaffs", warehouseStaffs);
        
        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/managerPurchaseRequestDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
