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
import com.swp.whmsystem.dal.StockMovementDAO;
import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.StockMovement;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.ProductItemValidation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
        UserDAO userDAO = new UserDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        User user = (User) session.getAttribute("user");

        String purchaseRequestIdraw = request.getParameter("prId"); // tí sửa thành prId
        int purchaseRequestId = -1;
        try {
            purchaseRequestId = Integer.parseInt(purchaseRequestIdraw);
            PurchaseRequest purchaseRequest = pr.getPurchaseRequestById(purchaseRequestId);

            Timestamp approvedAt = purchaseRequest.getCreatedAt();
            String creator = userDAO.getUserNameById(purchaseRequest.getCreatedBy());
            String supplierName = supplierDAO.getSupplierById(purchaseRequest.getSupplierId()).getSupplierName();
            int totalItem = 0;
            List<PurchaseItem> purchaseList = pi.getItemsByPurchaseRequestId(purchaseRequestId);
            List<List<ProductItemRowDTO>> nestedList = new ArrayList<>();
            String status = purchaseRequest.getStatus();
            if (status.equals("APPROVED")) {
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
            } else if ("PROCESSING".equals(status)) {
                GoodReceiptItemDAO griDao = new GoodReceiptItemDAO();
                Map<Integer, Integer> importedProductQuantity = griDao.getReceivedQuantityByPurchaseRequestId(purchaseRequest.getId());
                for (PurchaseItem i : purchaseList) {
                    int productId = i.getProductId();
                    int quantity = i.getRequiredQty() - importedProductQuantity.getOrDefault(productId, 0);
                    int price = i.getPrice();
                    List<ProductItemRowDTO> a = new ArrayList<>();
                    for (int j = 0; j < quantity; ++j) {
                        Product p = product.getProductFromId(productId);
                        ProductItemRowDTO dto = new ProductItemRowDTO(productId, p.getName(), "", p.getUnit().getName(),
                                price);
                        a.add(dto);
                    }
                    if (!a.isEmpty()) {
                        nestedList.add(a);
                    }
                    totalItem += quantity;
                }
            }
            // thêm xử lý nếu ko tồn tại purchase request đó hoặc chưa được approved và completed

            List<ProductItemRowDTO> list = new ArrayList<>();
            for (List<ProductItemRowDTO> i : nestedList) {
                list.addAll(i);
            }
            session.setAttribute("prCode", purchaseRequestId);
            session.setAttribute("approvedAt", approvedAt);
            session.setAttribute("handler", user.getFullName());
            session.setAttribute("supplierName", supplierName);
            session.setAttribute("creator", creator);
            session.setAttribute("totalItem", totalItem);
            session.setAttribute("list", list);

            List<List<ProductItemRowDTO>> importItems = nestedList;
            request.setAttribute("prCode", purchaseRequestId);
            request.setAttribute("importItems", importItems);
        } catch (Exception ex) {
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
        try {
            User user = (User) session.getAttribute("user");
            int handler = user.getId();
            String purchaseRequestId_raw = request.getParameter("purchaseRequestId");
            int purchaseRequestId = -1;
            try {
                purchaseRequestId = Integer.parseInt(purchaseRequestId_raw);
            } catch (NumberFormatException ex) {
                session.setAttribute("message", "Invalid Purchase Request ID." + purchaseRequestId_raw);
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/importRequestList");
                return;
            }

            // read arrays directly from form submission
            String invoiceNumber = request.getParameter("invoiceNumber");
            String[] productIds = request.getParameterValues("productId");
            String[] serials = request.getParameterValues("serial");
            String[] itemPrices = request.getParameterValues("itemPrice");

            if (productIds == null || serials == null || itemPrices == null || productIds.length == 0) {
                session.setAttribute("message", "No valid item data submitted.");
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            List<ProductItemRowDTO> importItems = (List<ProductItemRowDTO>) session.getAttribute("list");
            List<ProductItemRowDTO> filledList = filledSerial(productIds, serials, itemPrices);
            if (filledList.isEmpty()) {
                request.setAttribute("message", "Please enter at least one serial to import.");
                request.setAttribute("importItems", returnListDTO(importItems, serials));
                request.getRequestDispatcher("WEB-INF/view/import/importProduct.jsp").forward(request, response);
                return;
            }
            String valid = ProductItemValidation.validateProductItem(filledList);

            if (!"true".equals(valid)) {
                List<List<ProductItemRowDTO>> filledReturnedList = returnListDTO(importItems, serials);
                request.setAttribute("purchaseRequestId", purchaseRequestId);
                request.setAttribute("importItems", filledReturnedList);
                request.setAttribute("message", valid);
                request.getRequestDispatcher("WEB-INF/view/import/importProduct.jsp").forward(request, response);
                return;
            } else {
                handleImport(purchaseRequestId, handler, invoiceNumber, filledList);
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
            response.sendRedirect(request.getContextPath() + "/importRequestList");
        } catch (Exception ex) {
            session.setAttribute("message", "Error saving import: " + ex.getMessage());
            session.setAttribute("messageType", "danger");
            request.getRequestDispatcher("WEB-INF/view/import/importProduct.jsp").forward(request, response);
        }
    }

    private void handleImport(Integer prId, int handler, String invoiceNumber, List<ProductItemRowDTO> productItemList) throws SQLException {
        PurchaseRequestDAO pr = new PurchaseRequestDAO();
        PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
        GoodReceiptDAO gr = new GoodReceiptDAO();
        GoodReceiptItemDAO gri = new GoodReceiptItemDAO();
        ProductItemDAO pi = new ProductItemDAO();
        ProductDAO productDAO = new ProductDAO();
        StockMovementDAO stockMovementDAO = new StockMovementDAO();

        // tạo good receipt trước
        GoodReceipt goodReceipt = new GoodReceipt();
        goodReceipt.setPurchaseRequestId(prId);
        goodReceipt.setProcessedBy(handler);
        goodReceipt.setStatus("COMPLETED");
        goodReceipt.setInvoiceNumber(invoiceNumber);
        int goodReceiptId = gr.insertGoodReceiptAndGetId(goodReceipt);

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
            Product product = productDAO.getProductFromId(key);
            product.setTotalQuantity(product.getTotalQuantity() + val.size());
            productDAO.increaseQuantity(product);
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

            // insert stock_movement
            StockMovement stockMovement = new StockMovement();
            stockMovement.setProductId(product.getProductId());
            stockMovement.setQuantity(val.size());
            stockMovement.setType("INCREASED");
            stockMovement.setReference_type("IMPORT");
            // thêm reference link : 
            stockMovementDAO.insertStockMovement(stockMovement);
        }

        // update purchase request
        PurchaseRequest purchaseRequest = pr.getPurchaseRequestById(prId);
        String status = purchaseRequest.getStatus();

        List<PurchaseItem> purchaseItems = purchaseItemDAO.getItemsByPurchaseRequestId(prId);
        Map<Integer, Integer> goodReceiptItems = gri.getReceivedQuantityByPurchaseRequestId(prId);
        if (isCompleted(purchaseItems, goodReceiptItems)) {
            purchaseRequest.setStatus("COMPLETED");
        } else {
            purchaseRequest.setStatus("PROCESSING");
        }
        pr.updatePurchaseRequest(purchaseRequest);
    }

    private boolean isCompleted(List<PurchaseItem> purchaseItems, Map<Integer, Integer> goodReceiptItems) {
        for (PurchaseItem i : purchaseItems) {
            if (i.getRequiredQty() > goodReceiptItems.getOrDefault(i.getProductId(), 0)) {
                return false;
            }
        }
        return true;
    }

    private List<ProductItemRowDTO> filledSerial(String[] productIds, String[] serials, String[] itemPrices) {
        List<ProductItemRowDTO> list = new ArrayList<>();
        for (int i = 0; i < serials.length; ++i) {
            String serial = serials[i];
            String productId_raw = productIds[i];
            String itemPrice_raw = itemPrices[i];

            int productId = Integer.parseInt(productId_raw);
            int itemPrice = Integer.parseInt(itemPrice_raw);
            if (!serial.isBlank()) {
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
