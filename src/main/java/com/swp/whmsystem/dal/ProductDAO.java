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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ProductDAO {
   public boolean addProduct(Product p){
       String sql = "insert into products(name, description, img_url, isactive, ramid, romid, chipid, brandid, modelid, unitid, categoryid, sku) values (?,?,?,?,?,?,?,?,?,?,?,?)";
       try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getImgUrl());
            ps.setBoolean(4, p.isIsActive());
            ps.setInt(5, p.getRam().getId());
            ps.setInt(6, p.getRom().getId());
            ps.setInt(7, p.getChip().getId());
            ps.setInt(8, p.getBrand().getId());
            ps.setInt(9, p.getModel().getId());
            ps.setInt(10, p.getUnit().getId());
            ps.setInt(11, p.getCategory().getCategoryId());
            ps.setString(12, p.getSku());
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return false;
   }
   
   public Product getProductFromId(int productid){
       String sql = "select * from products where productid = ?";
       return null;
   }
   
   public boolean updateProduct(Product p){
       String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, ramid = ?, romid = ?, chipid = ?, unitid = , categoryid = , brandid = , modelid = ?  WHERE productid = ?";
       try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getImgUrl());
            ps.setBoolean(4, p.isIsActive());
            ps.setInt(5, p.getRam().getId());
            ps.setInt(6, p.getRom().getId());
            ps.setInt(7, p.getChip().getId());
            ps.setInt(8, p.getUnit().getId());
            ps.setInt(8, p.getCategory().getCategoryId());
            ps.setInt(9, p.getBrand().getId());
            ps.setInt(10, p.getModel().getId());
            ps.setInt(11, p.getProductId());
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return false;
   }
   
   private Product mapFromResultSetToProduct(ResultSet rs) throws SQLException{ 
//    private int totalQuantity;
//    private boolean isActive;
//    private Ram ram;
//    private Rom rom;
//    private Chip chip;
//    private Model model;
//    private Unit unit;
//    private Category category;
//    private Brand brand;
       RamDAO ram = new RamDAO();
       RomDAO rom = new RomDAO();
       ChipDAO chip = new ChipDAO();
       ModelDAO model = new ModelDAO();
       UnitDAO unit = new UnitDAO();
       
       Product product = new Product();
       product.setName(rs.getString("name"));
       product.setProductId(rs.getInt("productid"));
       product.setDescription(rs.getString("description"));
       product.setImgUrl(rs.getString("img_url"));
       product.setSku(rs.getString("sku"));
       product.setTotalQuantity(rs.getInt("totalquantity"));
       product.setIsActive(rs.getBoolean("isactive"));
       return product;
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
        ProductDAO productDao = new ProductDAO();
    }
}
