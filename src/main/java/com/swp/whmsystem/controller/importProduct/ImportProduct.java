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
import com.swp.whmsystem.utils.ProductItemValidation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArrayDeque;

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
            List<List<ProductItemRowDTO>> nestedList = new ArrayList<>();
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
                nestedList.add(a);
                totalItem += quantity;
            }
            
            List<ProductItemRowDTO> list = new ArrayList<>();
            for(List<ProductItemRowDTO> i : nestedList) list.addAll(i);
            session.setAttribute("prCode", prCode);
            session.setAttribute("approvedAt", approvedAt);
            session.setAttribute("handler", handler);
            session.setAttribute("creator", creator);
            session.setAttribute("totalItem", totalItem);
            session.setAttribute("list", list);
            
            List<List<ProductItemRowDTO>> importItems = nestedList;
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
            response.sendRedirect(request.getContextPath() + "/importRequestList");
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
            List<ProductItemRowDTO> importItems = (List<ProductItemRowDTO>) session.getAttribute("list");
            List<ProductItemRowDTO> filledList = filledSerial(productIds, serials, itemPrices);
            String valid = ProductItemValidation.validateProductItem(filledList);

            if (!"true".equals(valid)) {
                List<List<ProductItemRowDTO>> filledReturnedList = returnListDTO(importItems, serials);
                request.setAttribute("goodReceiptId", goodReceiptId);
                request.setAttribute("importItems", filledReturnedList);
                request.setAttribute("message", valid);
                request.getRequestDispatcher("WEB-INF/view/import/importProduct.jsp").forward(request, response);
                return;
            } else {
                handleImport(goodReceiptId, supplierName, invoiceNumber, filledList);
            }
            // clear
            session.removeAttribute("list");
            session.removeAttribute("prCode");
            session.removeAttribute("approvedAt");
            session.removeAttribute("handler");
            session.removeAttribute("creator");
            session.removeAttribute("totalItem");

            session.setAttribute("message", "Import saved successfully! Inventory has been updated.");
            session.setAttribute("messageType", "success");
            request.getRequestDispatcher("WEB-INF/view/import/importProduct.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("message", "Error saving import: " + ex.getMessage());
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/ImportProduct?id=" + goodReceiptId);
        }
    }

    private void handleImport(int goodReceiptId, String supplierName, String invoiceNumber, List<ProductItemRowDTO> productItemList) {
        PurchaseRequestDAO pr = new PurchaseRequestDAO();
        PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
        GoodReceiptDAO gr = new GoodReceiptDAO();
        GoodReceiptItemDAO gri = new GoodReceiptItemDAO();
        ProductItemDAO pi = new ProductItemDAO();

        Map<Integer, List<ProductItemRowDTO>> a = new HashMap<>();
        // set thành từng list product serial để insert vào cùng good_receipt_item
        for (ProductItemRowDTO i : productItemList) {
            int productId = i.getProductId();
            if (a.containsKey(productId)) {
                a.get(productId).add(i);
            } else {
                a.put(productId, new ArrayList());
                a.get(productId).add(i);
            }
        }

        for (Map.Entry<Integer, List<ProductItemRowDTO>> entry : a.entrySet()) {
            Integer key = entry.getKey();
            List<ProductItemRowDTO> val = entry.getValue();
            GoodReceiptItem goodReceiptItem = new GoodReceiptItem();
            goodReceiptItem.setGoodReceiptId(goodReceiptId);
            goodReceiptItem.setProductId(key);
            goodReceiptItem.setActualQuantity(val.size());
            // insert good_receipt_item
            int goodReceiptItemId = gri.insertGoodReceiptItemAndGetId(goodReceiptItem);

            // insert product_item
            for (ProductItemRowDTO i : val) {
                ProductItem productItem = new ProductItem();
                productItem.setSerial(i.getSerial());
                productItem.setProductId(i.getProductId());
                productItem.setImportPrice(i.getImportedPrice());
                productItem.setGoodReceiptItemId(goodReceiptItemId);
                productItem.setStatus("AVAILABLE");
                pi.insertProductItem(productItem);
            }
        }

        // update good_receipt
        GoodReceipt goodReceipt = gr.getGoodReceiptByGoodReceipId(goodReceiptId);
        goodReceipt.setSupplierName(supplierName);
        goodReceipt.setInvoiceNumber(invoiceNumber);
        String status = goodReceipt.getStatus();

        PurchaseRequest purchaseRequest = pr.getPurchaseRequestById(goodReceipt.getPurchaseRequestId());
        List<PurchaseItem> purchaseItems = purchaseItemDAO.getItemsByPurchaseRequestId(purchaseRequest.getId());
        List<GoodReceiptItem> goodReceiptItems = gri.getItemsByGoodReceiptId(goodReceiptId);
        if (status.equals("NEW")) {
            if (isCompleted(purchaseItems, goodReceiptItems)) goodReceipt.setStatus("COMPLETED");
            else goodReceipt.setStatus("INCOMPLETED");
        } else{
            if (isCompleted(purchaseItems, goodReceiptItems)) goodReceipt.setStatus("COMPLETED");
        }
        gr.updateGoodReceipt(goodReceipt);
    }

    private boolean isCompleted(List<PurchaseItem> purchaseItems, List<GoodReceiptItem> goodReceiptItems) {
        int purchaseQuantity = 0;
        int goodReceiptQuantity = 0;

        for (PurchaseItem i : purchaseItems) {
            purchaseQuantity += i.getRequiredQty();
        }
        for (GoodReceiptItem i : goodReceiptItems) {
            goodReceiptQuantity += i.getActualQuantity();
        }
        return purchaseQuantity == goodReceiptQuantity;
    }

    private List<ProductItemRowDTO> filledSerial(String[] productIds, String[] serials, String[] itemPrices) {
        List<ProductItemRowDTO> list = new ArrayList<>();
        for (int i = 0; i < serials.length; ++i) {
            String serial = serials[i];
            String productId_raw = productIds[i];
            String itemPrice_raw = itemPrices[i];

            int productId = Integer.parseInt(productId_raw);
            int itemPrice = Integer.parseInt(itemPrice_raw);
            if (!"".equals(serial)) {
                ProductItemRowDTO dto = new ProductItemRowDTO();
                dto.setProductId(productId);
                dto.setSerial(serial.trim());
                dto.setImportedPrice(itemPrice);
                list.add(dto);
            }
        }
        return list;
    }

    private List<List<ProductItemRowDTO>> returnListDTO(List<ProductItemRowDTO> importList, String[] serials) {
        for (int i = 0; i < serials.length; ++i) {
            if (!serials[i].equals("")) {
                importList.get(i).setSerial(serials[i]);
            }
        }
                
         Map<Integer, List<ProductItemRowDTO>> a = new HashMap<>();
        // set thành từng list product serial để insert vào cùng good_receipt_item
        for (ProductItemRowDTO i : importList) {
            int productId = i.getProductId();
            if (a.containsKey(productId)) {
                a.get(productId).add(i);
            } else {
                a.put(productId, new ArrayList());
                a.get(productId).add(i);
            }
        }
        
        List<List<ProductItemRowDTO>> list = new ArrayList<>();
        
        for (Map.Entry<Integer, List<ProductItemRowDTO>> entry : a.entrySet()) {
            list.add(entry.getValue());
        }
        
        return list;
    }

}
