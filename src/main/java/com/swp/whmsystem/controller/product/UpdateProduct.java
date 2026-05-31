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
import com.swp.whmsystem.utils.FileUtils;
import com.swp.whmsystem.utils.ProductValidation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateProduct", urlPatterns = {"/UpdateProduct"})
public class UpdateProduct extends HttpServlet {

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
        RamDAO ram = new RamDAO();
        RomDAO rom = new RomDAO();
        ChipDAO chip = new ChipDAO();
        ModelDAO model = new ModelDAO();
        BrandDAO brand = new BrandDAO();
        UnitDAO unit = new UnitDAO();
        CategoryDAO category = new CategoryDAO();

        List<Ram> ramList = ram.getAllRam();
        List<Rom> romList = rom.getAllRom();
        List<Chip> chipList = chip.getAllChip();
        List<Model> modelList = model.getAll();
        List<Brand> brandList = brand.getAllBrand();
        List<Unit> unitList = unit.getAllUnit();
        List<Category> categoryList = category.getAllCategory();

        String productIdr = request.getParameter("productid");
        ProductDAO productDao = new ProductDAO();
        try {
            Product product = productDao.getProductFromId(Integer.parseInt(productIdr));
            if (product.getTotalQuantity() != 0) {
                request.setAttribute("transactionExist", "v");
            }
            request.setAttribute("product", product);
            request.setAttribute("ramList", ramList);
            request.setAttribute("romList", romList);
            request.setAttribute("chipList", chipList);
            request.setAttribute("modelList", modelList);
            request.setAttribute("brandList", brandList);
            request.setAttribute("unitList", unitList);
            request.setAttribute("categoryList", categoryList);
        } catch (NumberFormatException ex) {
            String error = ex.getMessage();
        }
        request.getRequestDispatcher("view/productDetail.jsp").forward(request, response);
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
        String productid = request.getParameter("productId");
        String sku = request.getParameter("sku");
        String productName = request.getParameter("productName");
        String categoryId = request.getParameter("category");
        String brandId = request.getParameter("brand");
        String unitr = request.getParameter("unit");
        String modelId = request.getParameter("model");
        String ramId = request.getParameter("ram");
        String romId = request.getParameter("rom");
        String chipId = request.getParameter("chip");
        String price = request.getParameter("price");
        String isActive = request.getParameter("isActive");
        String description = request.getParameter("description");
        Part part = request.getPart("image");
        String imgUrl = "";
        if (part != null) {
            imgUrl = FileUtils.saveFile(part, request);
        }

        String cautioned = request.getParameter("cautioned");

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
        
        List<Ram> ramList = ramDao.getAllRam();
        List<Rom> romList = romDao.getAllRom();
        List<Chip> chipList = chipDao.getAllChip();
        List<Model> modelList = modelDao.getAll();
        List<Brand> brandList = brandDao.getAllBrand();
        List<Unit> unitList = unitDao.getAllUnit();
        List<Category> categoryList = categoryDao.getAllCategory();
        request.setAttribute("ramList", ramList);
        request.setAttribute("romList", romList);
        request.setAttribute("chipList", chipList);
        request.setAttribute("modelList", modelList);
        request.setAttribute("brandList", brandList);
        request.setAttribute("unitList", unitList);
        request.setAttribute("categoryList", categoryList);

        ProductDAO productDao = new ProductDAO();
        Product product = productDao.getProductFromId(Integer.parseInt(productid));
        Product newProduct = new Product(0, productName, description, sku.toUpperCase(), imgUrl == null ? product.getImgUrl() : imgUrl, 0, Integer.parseInt(isActive) == 1, ram, rom, unit, chip, model, category, brand);
        if (productDao.getProductFromSKU(sku).getProductId() != product.getProductId()) {
            String message = "SKU was existed in other product !";
            request.setAttribute("product", newProduct);
            request.setAttribute("message", message);
            request.getRequestDispatcher("view/productDetail.jsp").forward(request, response);
        } else if (productDao.getProductWithSpecification(newProduct).getProductId() != product.getProductId()) {
            if (cautioned == null) {
                String message = "This product have a same product having same specification. Do you want to add this product ?";
                cautioned = "yes";
                request.setAttribute("product", product);
                request.setAttribute("cautioned", cautioned);
                request.setAttribute("message", message);
                request.getRequestDispatcher("view/productDetail.jsp").forward(request, response);
            }
        } else {
            String error = ProductValidation.isProductValid(product);
            if (error == "true" && productDao.updateProduct(product)) {
                response.sendRedirect("view/productList.jsp");
            } else {
                request.setAttribute("message", error);
                request.getRequestDispatcher("view/productDetail.jsp").forward(request, response);
            }
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
