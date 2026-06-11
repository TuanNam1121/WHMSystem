package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.controller.purchaseRequest.PurchaseRequestList;
import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.UserDAO;
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
        GoodReceiptDAO goodReceiptDAO = new GoodReceiptDAO();
        List<GoodReceipt> importRequests = goodReceiptDAO.getAllGoodReceiptForProcessor(user.getId());
        
        request.setAttribute("importRequests", importRequests);
        request.getRequestDispatcher("WEB-INF/view/import/importRequestList.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}