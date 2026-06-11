package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.controller.purchaseRequest.PurchaseRequestList;
import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.dto.ImportRequestDTO;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.dal.GoodReceiptDAO;
import java.util.ArrayList;

@WebServlet(name = "ImportRequestList", urlPatterns = {"/importRequestList"})
public class ImportRequestList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
//        if (user == null || user.getRoleId() != 3) {
//            response.sendRedirect("login");
//            return;
//        }

        GoodReceiptDAO goodReceiptDAO = new GoodReceiptDAO();
        List<GoodReceipt> importRequests = goodReceiptDAO.getAllGoodReceiptForProcessor(user.getId());
        
        request.setAttribute("importRequests", importRequests);
        request.getRequestDispatcher("WEB-INF/view/import/importRequestList.jsp").forward(request,response);
        
//         HttpSession session = request.getSession();
        
//         GoodReceiptDAO goodReceiptDao = new GoodReceiptDAO();
        
//         User user = (User) session.getAttribute("user");        
//         List<GoodReceipt> receiptList = goodReceiptDao.getAllGoodReceiptForProcessor(user.getId());
//         List<ImportRequestDTO> list = new ArrayList<>();
//         for(GoodReceipt i : receiptList){
//             list.add(toImportRequestDTO(i));
//         }
        
//         request.setAttribute("list", list);
//         request.getRequestDispatcher("WEB-INF/view/import/ImportRequestList.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    private ImportRequestDTO toImportRequestDTO(GoodReceipt g){
        ImportRequestDTO a = new ImportRequestDTO();
        PurchaseRequestDAO purchaseRequestDao = new PurchaseRequestDAO();
        PurchaseItemDAO purchaseItemDao = new PurchaseItemDAO();
        List<PurchaseItem> purchaseItemList = purchaseItemDao.getAllPurchaseItems();
        UserDAO userDao = new UserDAO();
        int totalItem = 0;
        for(PurchaseItem i : purchaseItemList) totalItem += i.getRequiredQty();
        
        PurchaseRequest purchaseRequest = purchaseRequestDao.getPurchaseRequestById(g.getId());
        a.setGoodReceiptId(g.getId());
        a.setPurchaseRequestId(g.getPurchaseRequestId());
        a.setCreatedBy(userDao.getUserFromId(purchaseRequest.getCreatedBy()).getFullName());
        a.setCreatedAt(purchaseRequest.getCreatedAt());
        a.setTotalItem(totalItem);
        // cần hiển thị Purchase Request Id, status = New thì hiển thị nút Solve
        // created_by, approved_by, note
        // GoodReceiptID | PurchaseRequestID | CreatedBy | Created At | Total Item | Action
        return a;
    }
}