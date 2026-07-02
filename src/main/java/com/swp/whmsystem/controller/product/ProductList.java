

package com.swp.whmsystem.controller.product;

import java.io.IOException;
import java.util.*;

import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "ProductList", urlPatterns = {"/productlist"})
public class ProductList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_PRODUCT,
                "You are not authorized to View products.")) {
            return;
        }
        ProductDAO productDAO = new ProductDAO();
        BrandDAO brandDAO = new BrandDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Product> productList = new ArrayList<>();
        String productName = request.getParameter("productName");
        String categoryIdRaw = request.getParameter("categoryId");
        String brandIdRaw = request.getParameter("brandId");
        String isActiveRaw = request.getParameter("isActive");
        String sortBy = request.getParameter("sortBy");
        String pageSizeRaw = request.getParameter("pageSize");
        String pageRaw = request.getParameter("page");

        int categoryId = -1;
        int brandId = -1;
        int isActive = -1;
        int pageSize = 10;
        int page = 1;

        if (categoryIdRaw != null && !categoryIdRaw.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdRaw.trim());
            } catch (NumberFormatException e) {
                categoryId = -1;
            }
        }

        if (brandIdRaw != null && !brandIdRaw.trim().isEmpty()) {
            try {
                brandId = Integer.parseInt(brandIdRaw.trim());
            } catch (NumberFormatException e) {
                brandId = -1;
            }
        }

        if (isActiveRaw != null && !isActiveRaw.trim().isEmpty()) {
            try {
                int parsedStatus = Integer.parseInt(isActiveRaw.trim());
                if (parsedStatus == 0 || parsedStatus == 1) {
                    isActive = parsedStatus;
                }
            } catch (NumberFormatException e) {
                isActive = -1;
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

        int totalProducts = productDAO.countProducts(productName, categoryId, brandId, isActive);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalProducts / pageSize));
        page = Math.min(page, totalPages);

        productList = productDAO.searchProduct(
                productName, categoryId, brandId, isActive, sortBy, pageSize, page
        );

        List<Category> categoryList = categoryDAO.getAllCategory();
        List<Brand> brandList = brandDAO.getAllBrand();

        session.setAttribute("productList", productList);
        session.setAttribute("categoryList", categoryList);
        session.setAttribute("brandList", brandList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("WEB-INF/view/product/productList.jsp").forward(request, response);

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
