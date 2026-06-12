/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.GoodReceiptItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ImportHistoryDTO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.model.GoodReceiptItem;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;
import java.io.IOException;
import java.io.PrintWriter;
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
        }
        catch(NumberFormatException ex){
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("ImportHistory").forward(request, response);
            return;
        }
        
        GoodReceiptDAO gr = new GoodReceiptDAO();
        GoodReceiptItemDAO gri = new GoodReceiptItemDAO();
        ProductItemDAO pi = new ProductItemDAO();
        
        ImportHistoryDTO importDetail = toImportHistory(gr.getGoodReceiptByGoodReceipId(receipId));
        List<ProductItem> productItemList = pi.getAllProductItemByGoodReceiptID(receipId);
        List<ProductItemRowDTO> returnedList = new ArrayList<>();
        for(ProductItem i : productItemList) returnedList.add(toProductItemRowDTO(i));
        
        request.setAttribute("list", returnedList);
        request.setAttribute("detail", importDetail);
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
        GoodReceiptItemDAO griDao = new GoodReceiptItemDAO();
        ProductItemDAO piDao = new ProductItemDAO();
                
        List<GoodReceiptItem> gri = griDao.getItemsByGoodReceiptId(gr.getId());
        int item = 0;
        int total = 0;
        
        for(GoodReceiptItem i : gri){
            item += i.getActualQuantity();
            List<ProductItem> pi = piDao.getAllProductItemByGoodReceiptID(i.getId());
            for(ProductItem p : pi) total += p.getImportPrice();
        }
        
        ImportHistoryDTO i = new ImportHistoryDTO();
        i.setReceiptId(gr.getId());
        i.setPurchaseRequestId(gr.getPurchaseRequestId());
        i.setSupplier(gr.getSupplierName());
        i.setStatus(gr.getStatus());
        i.setImportBy(userDao.getUserNameById(gr.getProcessedBy()));
        i.setCompletedAt(gr.getCreatedAt());
        i.setItems(item);
        i.setTotal(total);
        return i;
    }
    
    private ProductItemRowDTO toProductItemRowDTO(ProductItem pi){
        ProductDAO productDao = new ProductDAO();
        Product product = productDao.getProductFromId(pi.getId());
        
        ProductItemRowDTO i = new ProductItemRowDTO();
        i.setProductId(pi.getProductId());
        i.setProductName(product.getName());
        i.setSerial(pi.getSerial());
        i.setImportedPrice(pi.getImportPrice());
        i.setUnit(product.getUnit().getName());
        return i;
    }
}
