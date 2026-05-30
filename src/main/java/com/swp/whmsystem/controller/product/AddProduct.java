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
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Category;
import com.swp.whmsystem.model.Chip;
import com.swp.whmsystem.model.Model;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.Ram;
import com.swp.whmsystem.model.Rom;
import com.swp.whmsystem.model.Unit;
import com.swp.whmsystem.utils.FileUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

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
        request.setAttribute("ramList", ramList);
        request.setAttribute("romList", romList);
        request.setAttribute("chipList", chipList);
        request.setAttribute("modelList", modelList);
        request.setAttribute("brandList", brandList);
        request.setAttribute("unitList", unitList);
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("addproduct.jsp").forward(request, response);
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
        // category
        // unit
        Part part = request.getPart("image");
        String uploadPath = getServletContext().getRealPath("/uploadImages");
        String imgUrl = FileUtils.saveFile(part, request);
        Ram ram = new Ram();
        ram.setId(Integer.parseInt(ramId));
        Rom rom = new Rom();
        rom.setId(Integer.parseInt(romId));
        Chip chip = new Chip();
        chip.setId(Integer.parseInt(chipId));
        Model model = new Model();
        model.setId(Integer.parseInt(modelId));
        Category category = new Category();
        category.setCategoryId(Integer.parseInt(categoryId));
        Unit unit = new Unit();
        unit.setId(Integer.parseInt(unitr));
        model.setId(Integer.parseInt(modelId));
        Brand brand = new Brand();
        brand.setId(Integer.parseInt(brandId));
        ProductDAO productDao = new ProductDAO();
        //int productId, String name, String description, String sku, String imgUrl, int totalQuantity, boolean isActive, Ram ram, Rom rom, Unit unit, Chip chip, Model model, Category category, Brand brand
        Product product = new Product(0, productName, description, "Generator" + UUID.randomUUID().toString(), imgUrl, 0, true, ram, rom, unit, chip, model, category, brand);
        if(productDao.addProduct(product)){
            response.sendRedirect("productlist.jsp");
        }
        else request.getRequestDispatcher("addproduct.jsp").forward(request, response);
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
