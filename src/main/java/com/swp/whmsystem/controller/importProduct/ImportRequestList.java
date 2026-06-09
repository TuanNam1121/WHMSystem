package com.swp.whmsystem.controller.importProduct;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.dal.GoodReceiptDAO;

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}