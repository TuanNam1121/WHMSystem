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
        if(p.getName() == null || p.getName().isBlank()) return "Product must have SKU";
        if(p.getName() == null || p.getName().isBlank()) return "Product Name can't be empty";
        if(p.getCategory() == null) return "Product must be have category";
        if(p.getCategory().getName().contains("Laptop")){
            String error = "Product must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getModel() == null) return error + "model";
            if(p.getRam() == null) return error + "ram";
            if(p.getRom() == null) return error + "rom";
            if(p.getChip() == null) return error + "chip";
        }
        else if(p.getCategory().getName().equals("RAM")){
            String error = "Product must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getRam() == null) return error + "ram";
        }
        else if(p.getCategory().getName().equals("ROM")){
            String error = "Product must be have ";
            if(p.getBrand() == null) return error + "brand";
            if(p.getUnit() == null) return error + "unit";
            if(p.getRom() == null) return error + "rom";
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
}
