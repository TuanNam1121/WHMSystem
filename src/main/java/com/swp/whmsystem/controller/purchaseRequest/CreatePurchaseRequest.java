package com.swp.whmsystem.controller.purchaseRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.RequestItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreatePurchaseRequest", urlPatterns = {"/createPurchaseRequest"})
public class CreatePurchaseRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getProductList();
        request.setAttribute("productListForPurchase", productList);

        request.getRequestDispatcher("WEB-INF/view/purchaseRequest/createPurchaseRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Read JSON from request body
        StringBuilder jsonBuffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        String jsonString = jsonBuffer.toString();

        // 2. Parse JSON into List<RequestItemDTO> using Gson
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<RequestItemDTO>>(){}.getType();
        List<RequestItemDTO> items = gson.fromJson(jsonString, listType);

        // 3. Process the list (For now, print to server log)
        if (items != null && !items.isEmpty()) {
            System.out.println("Received " + items.size() + " items for Purchase Request:");
            for (RequestItemDTO item : items) {
                System.out.println("- ID/SKU: " + item.getId() + " | Qty: " + item.getReqQty());
            }
            
            // TODO: Call DAO to save PurchaseRequest and PurchaseRequestDetails
            
            // 4. Send success response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            // Send error response
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"No items selected\"}");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

