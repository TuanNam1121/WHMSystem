package com.swp.whmsystem.controller.purchaseRequest;

import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "ManagerPurchaseRequestDetail", urlPatterns = { "/managerPurchaseRequestDetail" })
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

        List<User> warehouseStaffs = userDao.searchUser(null, "3", null);

        if (!"NEW".equalsIgnoreCase(pr.getStatus())) {
            GoodReceiptDAO grDao = new GoodReceiptDAO();
            GoodReceipt gr = grDao.getGoodReceiptByPurchaseRequestId(id);
            if (gr != null) {
                User assignedStaff = userDao.getUserFromId(gr.getProcessedBy());
                request.setAttribute("assignedStaff", assignedStaff);
            }
        }

        request.setAttribute("purchaseRequest", pr);
        request.setAttribute("salesman", salesman);
        request.setAttribute("purchaseItems", items);
        request.setAttribute("productMap", productMap);
        request.setAttribute("warehouseStaffs", warehouseStaffs);

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/managerPurchaseRequestDetail.jsp").forward(request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String button = request.getParameter("buttonSubmit");
        int purReqId = Integer.parseInt(request.getParameter("purchaseRequestId"));
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        PurchaseRequest purchaseRequest = purchaseRequestDAO.getPurchaseRequestById(purReqId);

        if (button.equalsIgnoreCase("Reject")) {
            purchaseRequest.setStatus("REJECTED");
            purchaseRequestDAO.updatePurchaseRequest(purchaseRequest);
            response.sendRedirect("managerPurchaseRequestList");
        } else {
            String warehouseStaffIdStr = request.getParameter("staffId");
            if (warehouseStaffIdStr == null || warehouseStaffIdStr.isBlank()) {
                warehouseStaffIdStr = request.getParameter("warehouseStaffId");
            }
            int warehouseStaffId = Integer.parseInt(warehouseStaffIdStr);
            String managerNote = request.getParameter("managerNote");

            purchaseRequest.setStatus("APPROVED");
            purchaseRequestDAO.updatePurchaseRequest(purchaseRequest);

            GoodReceipt g = new GoodReceipt();
            g.setPurchaseRequestId(purchaseRequest.getId());
            g.setProcessedBy(warehouseStaffId);
            g.setNote(managerNote);
            g.setStatus("NEW");

            GoodReceiptDAO grDao = new GoodReceiptDAO();
            grDao.insertGoodReceipt(g);

            response.sendRedirect("managerPurchaseRequestList");
        }
    }
}
