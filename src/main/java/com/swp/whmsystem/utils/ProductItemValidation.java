/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.utils;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.ProductItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class ProductItemValidation {
    public static String validateProductItem(List<ProductItemRowDTO> list) {
        Set<String> seenSerials = new HashSet<>();
        for (ProductItemRowDTO i : list) {
            // int productId = i.getProductId();
            String serial = i.getSerial().trim();
            if (seenSerials.contains(serial)) {
                return "Serial: " + serial + " is dupplicated";
            }
            seenSerials.add(serial);
        }

        ProductDAO p = new ProductDAO();
        ProductItemDAO pi = new ProductItemDAO();
        for (ProductItemRowDTO i : list) {
            ProductItem existed = pi.existedSerial(i.getSerial());
            if (existed != null)
                return "Product " + p.getProductNameById(existed.getProductId()) + " Serial : " + existed.getSerial()
                        + " is existed in System";
        }
        return "true";
    }
}
