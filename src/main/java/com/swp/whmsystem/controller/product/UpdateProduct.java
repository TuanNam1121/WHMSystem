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
import jakarta.servlet.annotation.MultipartConfig;
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
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
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
            request.setAttribute("mode", "update");
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
        String price_raw = request.getParameter("price");
        String isActiveraw = request.getParameter("isActive");
        String description = request.getParameter("description");
        Part part = request.getPart("image");
        String imgUrl = "";
        if (part != null && part.getSize() > 0) {
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
        
        Integer price = ProductValidation.parseInteger(price_raw);
        Integer ramIdInt = ProductValidation.parseInteger(ramId);
        Integer romIdInt = ProductValidation.parseInteger(romId);
        Integer chipIdInt = ProductValidation.parseInteger(chipId);
        Integer modelIdInt = ProductValidation.parseInteger(modelId);
        Integer categoryIdInt = ProductValidation.parseInteger(categoryId);
        Integer unitIdInt = ProductValidation.parseInteger(unitr);
        Integer brandIdInt = ProductValidation.parseInteger(brandId);
        boolean isActive = ProductValidation.parseInteger(isActiveraw) == 1;

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
        
        if(!productName.equals(product.getName())) product.setName(productName);
        if(!description.equals(product.getDescription())) product.setDescription(description);
        if(!sku.equals(product.getSku())) product.setSku(sku);
        if(product.isIsActive() != isActive) product.setIsActive(isActive);
        if(!imgUrl.equals("")) product.setImgUrl(imgUrl);
        if(price != null && price != product.getPrice()) product.setPrice(price);
        if(ram != null) product.setRam(ram);
        if(rom != null) product.setRom(rom);
        if(chip != null) product.setChip(chip);
        if(model != null) product.setModel(model);
        if(category != null) product.setCategory(category);
        if(unit != null) product.setUnit(unit);
        if(brand != null) product.setBrand(brand);
        
        request.setAttribute("mode", "update");
        request.setAttribute("product", product);
        
        Product SKUexistedProduct = productDao.getProductFromSKU(sku);
        Product sameSpecProduct = productDao.getProductWithSpecification(product);
        if (SKUexistedProduct != null && SKUexistedProduct.getProductId() != product.getProductId()) {
            String message = "SKU was existed in other product !";
            request.setAttribute("message", message);
            request.getRequestDispatcher("view/productDetail.jsp").forward(request, response);
        } else if (sameSpecProduct != null && sameSpecProduct.getProductId() != product.getProductId()) {
            if (cautioned == null) {
                String message = "This product have a same product having same specification. Do you want to add this product ?";
                cautioned = "yes";
                request.setAttribute("product", product);
                request.setAttribute("cautioned", cautioned);
                request.setAttribute("message", message);
                request.getRequestDispatcher("view/productDetail.jsp").forward(request, response);
            }else {
                String error = ProductValidation.isProductValid(product);
                if ("true".equals(error) && productDao.updateProduct(product)) {
                    response.sendRedirect("productlist");
                }
            }
        } else {
            String error = ProductValidation.isProductValid(product);
            if ("true".equals(error) && productDao.updateProduct(product)) {
                response.sendRedirect("productlist");
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
