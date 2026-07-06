/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.GoodReceiptItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ImportHistoryDTO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;
import com.swp.whmsystem.model.PurchaseRequest;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name="ImportHistoryDetail", urlPatterns={"/ImportHistoryDetail"})
public class ImportHistoryDetail extends HttpServlet {
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String raw_receiptId = request.getParameter("receiptId");
        int receipId = 0;
        try{
            receipId = Integer.parseInt(raw_receiptId);
       
        GoodReceiptDAO gr = new GoodReceiptDAO();
        ProductItemDAO pi = new ProductItemDAO();
        
        ImportHistoryDTO importDetail = toImportHistory(gr.getGoodReceiptByGoodReceipId(receipId));
        
        // Search and pagination parameters
        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int pageSize = 10;
        int page = 1;

        if (pageSizeRaw != null && !pageSizeRaw.trim().isEmpty()) {
            try {
                int parsedPageSize = Integer.parseInt(pageSizeRaw.trim());
                if (parsedPageSize > 0 && parsedPageSize <= 100) {
                    pageSize = parsedPageSize;
                }
            } catch (NumberFormatException ignored) {
                pageSize = 10;
            }
        }

        if (pageRaw != null && !pageRaw.trim().isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageRaw.trim()));
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        // public int countProductItemsByReceiptId(int receiptId, String keyword)
        // public List<ProductItem> searchProductItemsByReceiptId(int receiptId, String keyword, String sortBy, int pageSize, int page)
        
        int totalItems = pi.countProductItemsByReceiptId(receipId, keyword);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / pageSize));
        page = Math.min(page, totalPages);

        List<ProductItem> productItemList = pi.searchProductItemsByReceiptId(receipId, keyword, sortBy, pageSize, page);
        
        List<ProductItemRowDTO> returnedList = new ArrayList<>();
        for(ProductItem i : productItemList) returnedList.add(toProductItemRowDTO(i));
        
        request.setAttribute("list", returnedList);
        request.setAttribute("detail", importDetail);
        
        // Set pagination attributes
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortBy", sortBy);
        
        }
        catch(Exception ex){
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/view/import/ImportHistoryDetail.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("WEB-INF/view/import/ImportHistoryDetail.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    private ImportHistoryDTO toImportHistory(GoodReceipt gr){
        UserDAO userDao = new UserDAO();
        PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();
        GoodReceiptItemDAO griDao = new GoodReceiptItemDAO();
        ProductItemDAO piDao = new ProductItemDAO();
        SupplierDAO supplierDao = new SupplierDAO();
        
        PurchaseRequest purchaseRequest = purchaseRequestDAO.getPurchaseRequestById(gr.getPurchaseRequestId());
        List<GoodReceiptItem> gri = griDao.getItemsByGoodReceiptId(gr.getId());
        int item = 0;
        int total = 0;
        
        for(GoodReceiptItem i : gri){
            item += i.getActualQuantity();
            List<ProductItem> pi = piDao.getAllProductItemByGoodReceiptItemId(i.getId());
            for(ProductItem p : pi) total += p.getImportPrice();
        }
        
        ImportHistoryDTO i = new ImportHistoryDTO();
        i.setReceiptId(gr.getId());
        i.setPurchaseRequestId(gr.getPurchaseRequestId());
        i.setSupplier(supplierDao.getSupplierById(purchaseRequest.getSupplierId()).getSupplierName());
        i.setStatus(gr.getStatus());
        i.setImportBy(userDao.getUserNameById(gr.getProcessedBy()));
        i.setCompletedAt(gr.getCreatedAt());
        i.setItems(item);
        i.setTotal(total);
        return i;
    }
    
    private ProductItemRowDTO toProductItemRowDTO(ProductItem pi){
        ProductDAO productDao = new ProductDAO();
        Product product = productDao.getProductFromId(pi.getProductId());
        
        ProductItemRowDTO i = new ProductItemRowDTO();
        i.setProductId(pi.getProductId());
        i.setProductName(product.getName());
        i.setSerial(pi.getSerial());
        i.setImportedPrice(pi.getImportPrice());
        i.setUnit(product.getUnit().getName());
        return i;
    }
}
