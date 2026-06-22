/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.GoodReceiptItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ImportRequestDetailDTO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.PurchaseRequest;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name="ImportRequestDetail", urlPatterns={"/ImportRequestDetail"})
public class ImportRequestDetail extends HttpServlet {
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
        HttpSession session = request.getSession();
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("importRequestList");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            PurchaseRequestDAO prDAO = new PurchaseRequestDAO();
            PurchaseRequest pr = prDAO.getPurchaseRequestById(id);
            GoodReceiptItemDAO griDao = new GoodReceiptItemDAO();
            PurchaseItemDAO piDAO = new PurchaseItemDAO();
            UserDAO userDAO = new UserDAO();
            SupplierDAO supplierDao = new SupplierDAO();
        
            List<PurchaseItem> items = piDAO.getItemsByPurchaseRequestId(id);
            ProductDAO productDAO = new ProductDAO();
            List<Product> productList = productDAO.getProductList();
            
            Map<Integer, Product> productMap = new HashMap<>();
            for (Product p : productList) {
                productMap.put(p.getProductId(), p);
            }
        
            Map<Integer, Integer> importedMap = griDao.getReceivedQuantityByPurchaseRequestId(id);

            ImportRequestDetailDTO importRequestDTO = new ImportRequestDetailDTO(pr.getId(), pr.getCreatedAt(),
                    userDAO.getUserFromId(pr.getCreatedBy()).getFullName(), supplierDao.getSupplierById(pr.getSupplierId()).getSupplierName(),
                    pr.getSupplierId());
            
            request.setAttribute("importDTO", importRequestDTO);
            request.setAttribute("pr", pr);
            request.setAttribute("purchaseItems", items);
            request.setAttribute("productListForPurchase", productList);
            request.setAttribute("productMap", productMap);
            request.setAttribute("importedMap", importedMap);
            request.getRequestDispatcher("WEB-INF/view/import/importRequestDetail.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("message", ex.getMessage());
            response.sendRedirect("importRequestList");
        }
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
