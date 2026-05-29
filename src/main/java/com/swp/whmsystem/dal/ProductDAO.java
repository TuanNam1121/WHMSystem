/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Chip;
import com.swp.whmsystem.model.Model;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.Ram;
import com.swp.whmsystem.model.Rom;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Admin
 */
public class ProductDAO {
   public boolean addProduct(Product p){
       String sql = "insert into products(name, description, img_url, isactive, ramid, romid, chipid, brandid, modelid, unitid, categoryid) values (?,?,?,?,?,?,?,?,?,?,?)";
       try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getImgUrl());
            ps.setBoolean(4, p.isIsActive());
            ps.setInt(5, p.getRam().getId());
            ps.setInt(6, p.getRom().getId());
            ps.setInt(7, p.getChip().getId());
            ps.setInt(8, p.getBrandId());
            ps.setInt(9, p.getModel().getId());
            // unit
            // category
            ps.setInt(10, 1);
            ps.setInt(11, 1);
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return false;
   }
   
    public static void main(String[] args) {
        Ram ram = new Ram();
        ram.setId(Integer.parseInt("1"));
        Rom rom = new Rom();
        rom.setId(Integer.parseInt("1"));
        Chip chip = new Chip();
        chip.setId(Integer.parseInt("1"));
        Model model = new Model();
        model.setId(Integer.parseInt("1"));
        Product product = new Product(0, "a", "a", "/a", 0, true, ram, rom, chip, model, 0, 1);
        ProductDAO productDao = new ProductDAO();
    }
}
