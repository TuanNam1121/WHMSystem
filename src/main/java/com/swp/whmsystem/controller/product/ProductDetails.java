package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.OrderItemDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductDetails", urlPatterns = {"/productDetails"})
public class ProductDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ProductDAO productDAO = new ProductDAO();
        List<ProductItem> productItemList = new ArrayList<>();
        String productIdRaw = request.getParameter("productId");
        String serial = request.getParameter("serial");
        String date = request.getParameter("date");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sortBy");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int productId = Integer.parseInt(productIdRaw);
        int pageSize = 10;
        int page = 1;

        if (status != null && !status.trim().isEmpty()) {
            if (!status.equals("AVAILABLE") && !status.equals("UNAVAILABLE") && !status.equals("SOLD")) {
                status = null;
            }
        }

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

        int totalProductItems = productDAO.countProductItems(
                productId, serial, date, status
        );
        int totalPages = Math.max(1, (int) Math.ceil((double) totalProductItems / pageSize));
        page = Math.min(page, totalPages);

        productItemList = productDAO.searchProductItems(
                productId, serial, date, status, sortBy, pageSize, page
        );

//        ProductItemDAO piDao = new ProductItemDAO();
//        for(ProductItem i : productItemList){
//            if("SOLD".equals(i.getStatus())){
//                int exportPrice = piDao.getExportPriceWithSoldStatus(i.getId());
//                i.setExportPrice(exportPrice);
//            }
//        }
        
        Product product = productDAO.getProductFromId(productId);

        session.setAttribute("product", product);
        session.setAttribute("productItemList", productItemList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("focusTable",
                serial != null || date != null || status != null
                        || sortBy != null || pageSizeRaw != null || pageRaw != null);
        request.getRequestDispatcher("WEB-INF/view/product/productDetails.jsp").forward(request, response);
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
