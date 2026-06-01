/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.utils;

import com.swp.whmsystem.model.Product;

/**
 *
 * @author Admin
 */
public class ProductValidation {
    public static String isProductValid(Product p){
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
        else if(p.getCategory().getName().equals("RAM")){
            String error = "RAM must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getRam() == null) return error + "ram";
            if (p.getRom() != null) return "RAM product must not have ROM";
            if (p.getChip() != null) return "RAM product must not have Chip";
            if (p.getModel() != null) return "RAM product must not have Model";
        }
        else if(p.getCategory().getName().equals("ROM")){
            String error = "ROM must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getRom() == null) return error + "rom";
            if (p.getRam() != null) return "ROM product must not have RAM";
            if (p.getChip() != null) return "ROM product must not have Chip";
            if (p.getModel() != null) return "ROM product must not have Model";
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
    
    public static boolean isCategoryCheckRequired(Product p){
        if(p.getCategory() == null) return false;
        String name = p.getCategory().getName();
        return name.contains("Laptop") || name.equals("RAM") || name.equals("ROM");
    }
}
