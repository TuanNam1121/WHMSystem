/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.utils;

import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.model.Product;
import jakarta.servlet.http.Part;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class ProductValidation {
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/jpg", "image/pjpeg", "image/png", "image/gif", "image/webp"
    );

    public static String isProductValid(Product p, Part filePart){
        if(p.getSku()== null || p.getSku().isBlank()) return "Product must have SKU";
        if(p.getName() == null || p.getName().isBlank()) return "Product Name can't be empty";
        if(p.getCategory() == null) return "Product must be have category";
        
        if(p.getCategory().getName().contains("Laptop")){
            String error = "Laptop must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getModel() == null) return error + "model";
            if(p.getRam() == null) return error + "ram";
            if(p.getRom() == null) return error + "rom";
            if(p.getChip() == null) return error + "chip";
        }
        else if(p.getCategory().getCategoryId() == 2){
            String error = "RAM must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getRam() == null) return error + "ram";
            if (p.getRom() != null) return "RAM product must not have ROM";
            if (p.getChip() != null) return "RAM product must not have Chip";
            if (p.getModel() != null) return "RAM product must not have Model";
        }
        else if(p.getCategory().getCategoryId() == 3){
            String error = "ROM must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getRom() == null) return error + "rom";
            if (p.getRam() != null) return "Storage product must not have RAM";
            if (p.getChip() != null) return "Storage product must not have Chip";
            if (p.getModel() != null) return "Storage product must not have Model";
        }
        
        String imageValid = validateImage(filePart);
        if(imageValid != null){
            return imageValid;
        }
        return "true";
    }
    
    public static Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static String validateImage(Part filePart){
        if(filePart == null || filePart.getSize() == 0) return null;
        String ct = lower(filePart.getContentType());
        if (!ALLOWED_CONTENT_TYPES.contains(ct)) {
            return "Only JPG/PNG/GIF/WEBP are allowed";
        }
        return null;
    }
    
    private static String lower(String s) {
        return s == null ? "" : s.toLowerCase();
    }
    
    public static boolean existTransaction(int productId){
        ProductItemDAO dao = new ProductItemDAO();
        return !dao.getAllProductItemByProductId(productId).isEmpty();
    }
    
    public static void main(String[] args) {
        System.out.println(existTransaction(8));
    }
}
