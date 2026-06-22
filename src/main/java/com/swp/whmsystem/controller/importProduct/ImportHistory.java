/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.GoodReceiptItemDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ImportHistoryDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.ProductItem;
import com.swp.whmsystem.model.PurchaseRequest;
import com.swp.whmsystem.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name="ImportHistory", urlPatterns={"/ImportHistory"})
public class ImportHistory extends HttpServlet {
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
        String rawReceiptId = request.getParameter("receiptid");
        String rawPurchaseId = request.getParameter("purchaseid");
        String rawSupplierId = request.getParameter("supplier_id");
        String rawProcessedBy = request.getParameter("processedby");
        String sortBy = request.getParameter("sortBy");
        
        int receiptId = (rawReceiptId == null || rawReceiptId.isBlank()) ? -1 : Integer.parseInt(rawReceiptId);
        int purchaseid = (rawPurchaseId == null ||rawPurchaseId.isBlank()) ? -1 : Integer.parseInt(rawPurchaseId);
        int processedBy = (rawProcessedBy == null || rawProcessedBy.isBlank()) ? -1 : Integer.parseInt(rawProcessedBy);
        int supplierId = (rawSupplierId == null || rawSupplierId.isBlank()) ? -1 : Integer.parseInt(rawSupplierId);
        
        GoodReceiptDAO gr = new GoodReceiptDAO();
        UserDAO user = new UserDAO();
        
        HttpSession session = request.getSession();
        List<User> userImporterList = user.getAllUsersHandleGoodReceipt();
        List<GoodReceipt> list = gr.searchProduct(receiptId, purchaseid, supplierId, processedBy, sortBy);
        List<ImportHistoryDTO> returnedList = new ArrayList<>();
        for(GoodReceipt i : list){
            returnedList.add(toImportHistory(i));
        }
        session.setAttribute("userList", userImporterList);
        request.setAttribute("list", returnedList);
        request.getRequestDispatcher("WEB-INF/view/import/importHistory.jsp").forward(request, response);
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
}
