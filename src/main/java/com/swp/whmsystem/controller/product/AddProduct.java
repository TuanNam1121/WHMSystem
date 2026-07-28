/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.swp.whmsystem.controller.product;

import com.swp.whmsystem.dal.BrandDAO;
import com.swp.whmsystem.dal.CategoryDAO;
import com.swp.whmsystem.dal.ChipDAO;
import com.swp.whmsystem.dal.ModelDAO;
import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.RamDAO;
import com.swp.whmsystem.dal.RomDAO;
import com.swp.whmsystem.dal.UnitDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.model.Chip;
import com.swp.whmsystem.model.Model;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.Ram;
import com.swp.whmsystem.model.Rom;
import com.swp.whmsystem.model.Unit;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.FileUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import com.swp.whmsystem.utils.ProductValidation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author Admin
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
@WebServlet(name = "AddProduct", urlPatterns = {"/AddProduct"})
public class AddProduct extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.CREATE_PRODUCT,
                "You are not authorized to handle import product")) {
            return;
        }
        HttpSession session = request.getSession();
        RamDAO ram = new RamDAO();
        RomDAO rom = new RomDAO();
        ChipDAO chip = new ChipDAO();
        ModelDAO model = new ModelDAO();
        BrandDAO brand = new BrandDAO();
        UnitDAO unit = new UnitDAO();
        CategoryDAO category = new CategoryDAO();

        List<Ram> ramList = ram.getAllRamToAssign();
        List<Rom> romList = rom.getAllRomToAssign();
        List<Chip> chipList = chip.getAllChipToAssign();
        List<Model> modelList = model.getAllModelToAssign();
        List<Brand> brandList = brand.getAllBrand();
        List<Unit> unitList = unit.getAllUnitToAssign();
        List<Category> categoryList = category.getAllCategoryToAssign();
        session.setAttribute("ramList", ramList);
        session.setAttribute("romList", romList);
        session.setAttribute("chipList", chipList);
        session.setAttribute("modelList", modelList);
        session.setAttribute("brandList", brandList);
        session.setAttribute("unitList", unitList);
        session.setAttribute("categoryList", categoryList);
        request.setAttribute("mode", "add");
        request.getRequestDispatcher("WEB-INF/view/product/EditProduct.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String sku = request.getParameter("sku") != null ? request.getParameter("sku").trim() : "";
            String productName = request.getParameter("productName") != null ? request.getParameter("productName").trim() : "";
            String categoryId = request.getParameter("category");
            String brandId = request.getParameter("brand");
            String unitr = request.getParameter("unit");
            String modelId = request.getParameter("model");
            String ramId = request.getParameter("ram");
            String romId = request.getParameter("rom");
            String chipId = request.getParameter("chip");
            String description = request.getParameter("description");
            Part part = request.getPart("image");

            RamDAO ramDao = new RamDAO();
            RomDAO romDao = new RomDAO();
            ChipDAO chipDao = new ChipDAO();
            ModelDAO modelDao = new ModelDAO();
            BrandDAO brandDao = new BrandDAO();
            UnitDAO unitDao = new UnitDAO();
            CategoryDAO categoryDao = new CategoryDAO();

            Integer ramIdInt = ProductValidation.parseInteger(ramId);
            Integer romIdInt = ProductValidation.parseInteger(romId);
            Integer chipIdInt = ProductValidation.parseInteger(chipId);
            Integer modelIdInt = ProductValidation.parseInteger(modelId);
            Integer categoryIdInt = ProductValidation.parseInteger(categoryId);
            Integer unitIdInt = ProductValidation.parseInteger(unitr);
            Integer brandIdInt = ProductValidation.parseInteger(brandId);

            Ram ram = ramIdInt != null ? ramDao.getRamById(ramIdInt) : null;
            Rom rom = romIdInt != null ? romDao.getRomById(romIdInt) : null;
            Chip chip = chipIdInt != null ? chipDao.getChipById(chipIdInt) : null;
            Model model = modelIdInt != null ? modelDao.getModelById(modelIdInt) : null;
            Category category = categoryIdInt != null ? categoryDao.getCategoryById(categoryIdInt) : null;
            Unit unit = unitIdInt != null ? unitDao.getUnitById(unitIdInt) : null;
            Brand brand = brandIdInt != null ? brandDao.getBrandById(brandIdInt) : null;
            String imgUrl = "";

            ProductDAO productDao = new ProductDAO();
            Product product = new Product(0, productName, description, sku.toUpperCase(), imgUrl, 0, true, ram, rom, unit, chip, model, category, brand);
            request.setAttribute("mode", "add");
            request.setAttribute("p", product);

            if (productDao.getProductFromSKU(sku) != null) {
                request.setAttribute("p", product);
                request.setAttribute("message", "SKU was existed !");
                request.getRequestDispatcher("WEB-INF/view/product/EditProduct.jsp").forward(request, response);
                return;
            }

            String error = ProductValidation.isProductValid(product, part);
            if (!"true".equals(error)) {
                request.setAttribute("message", error);
                request.getRequestDispatcher("WEB-INF/view/product/EditProduct.jsp").forward(request, response);
                return;
            }

            if (part != null && part.getSize() > 0) {
                imgUrl = FileUtils.saveFile(part, request);
                product.setImgUrl(imgUrl);
            }

            if (productDao.addProduct(product)) {
                session.removeAttribute("ramList");
                session.removeAttribute("chipList");
                session.removeAttribute("modelList");
                session.removeAttribute("brandList");
                session.removeAttribute("unitList");
                session.removeAttribute("categoryList");
                response.sendRedirect("productlist");
            } else {
                request.setAttribute("p", product);
                request.setAttribute("message", "Error in Transaction");
                request.getRequestDispatcher("WEB-INF/view/product/EditProduct.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/view/product/EditProduct.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
