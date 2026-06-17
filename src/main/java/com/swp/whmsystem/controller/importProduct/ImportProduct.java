/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.GoodReceiptItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ImportProduct", urlPatterns = {"/ImportProduct"})
public class ImportProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        GoodReceiptDAO gr = new GoodReceiptDAO();
        PurchaseRequestDAO pr = new PurchaseRequestDAO();
        PurchaseItemDAO pi = new PurchaseItemDAO();
        ProductDAO product = new ProductDAO();
        UserDAO user = new UserDAO();

        String goodReceiptId_raw = request.getParameter("id");
        int goodReceiptId = -1;
        try {
            goodReceiptId = Integer.parseInt(goodReceiptId_raw);
            GoodReceipt goodReceipt = gr.getGoodReceiptByGoodReceipId(goodReceiptId);
            PurchaseRequest purchaseRequest = pr.getPurchaseRequestById(goodReceipt.getPurchaseRequestId());
            int prCode = purchaseRequest.getId();
            Timestamp approvedAt = purchaseRequest.getCreatedAt();
            String handler = user.getUserNameById(goodReceipt.getProcessedBy());
            String creator = user.getUserNameById(purchaseRequest.getCreatedBy());
            int totalItem = 0;
            List<PurchaseItem> purchaseList = pi.getItemsByPurchaseRequestId(prCode);
            List<List<ProductItemRowDTO>> list = new ArrayList<>();
            for (PurchaseItem i : purchaseList) {
                int productId = i.getProductId();
                int quantity = i.getRequiredQty();
                int price = i.getPrice();
                List<ProductItemRowDTO> a = new ArrayList<>();
                for (int j = 0; j < quantity; ++j) {
                    Product p = product.getProductFromId(productId);
                    ProductItemRowDTO dto = new ProductItemRowDTO(productId, p.getName(), "", p.getUnit().getName(),
                            price);
                    a.add(dto);
                }
                totalItem += quantity;
                list.add(a);
            }

            session.setAttribute("prCode", prCode);
            session.setAttribute("approvedAt", approvedAt);
            session.setAttribute("handler", handler);
            session.setAttribute("creator", creator);
            session.setAttribute("totalItem", totalItem);
            session.setAttribute("list", list);

            List<List<ProductItemRowDTO>> importItems = (List<List<ProductItemRowDTO>>) session.getAttribute("importItems");
            request.setAttribute("goodReceiptId", goodReceiptId);
            request.setAttribute("importItems", importItems);
        } catch (NumberFormatException ex) {
            String message = ex.getMessage();
            request.setAttribute("message", message);
        }
        request.getRequestDispatcher("WEB-INF/view/import/importProduct.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        String goodReceiptIdRaw = request.getParameter("goodReceiptId");
        int goodReceiptId = -1;
        try {
            goodReceiptId = Integer.parseInt(goodReceiptIdRaw);
        } catch (NumberFormatException ex) {
            session.setAttribute("message", "Invalid Good Receipt ID.");
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/ImportProduct");
            return;
        }

        // read arrays directly from form submission
        String supplierName = request.getParameter("supplierName");
        String invoiceNumber = request.getParameter("invoiceNumber");
        String[] productIds = request.getParameterValues("productId");
        String[] serials = request.getParameterValues("serial");
        String[] itemPrices = request.getParameterValues("itemPrice");

        if (productIds == null || serials == null || itemPrices == null || productIds.length == 0) {
            session.setAttribute("message", "No valid item data submitted.");
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/ImportProduct?id=" + goodReceiptId);
            return;
        }
        try {
            GoodReceiptDAO grDAO = new GoodReceiptDAO();
            GoodReceiptItemDAO griDAO = new GoodReceiptItemDAO();
            ProductItemDAO productItemDAO = new ProductItemDAO();

            List<ProductItemRowDTO> filledList = toItemListDTO(productIds, serials, itemPrices);
            if (productIds.length != serials.length) {
                // xử lý nhập thiếu

            }
            else{
                
            }

            // 4. Clear session import data
            session.removeAttribute("list");
            session.removeAttribute("importItems");
            session.removeAttribute("prCode");
            session.removeAttribute("approvedAt");
            session.removeAttribute("handler");
            session.removeAttribute("creator");
            session.removeAttribute("totalItem");

            session.setAttribute("message", "Import saved successfully! Inventory has been updated.");
            session.setAttribute("messageType", "success");
            response.sendRedirect(request.getContextPath() + "/good-receipt-list");

        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("message", "Error saving import: " + ex.getMessage());
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/ImportProduct?id=" + goodReceiptId);
        }
    }
    
    private List<ProductItemRowDTO> toItemListDTO(String[] productIds, String[] serials, String[] itemPrices){
        List<ProductItemRowDTO> list = new ArrayList<>();
        
        
        return list;
    }
}
