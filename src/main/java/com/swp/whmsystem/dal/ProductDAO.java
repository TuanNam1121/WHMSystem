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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductDAO {
    public boolean addProduct(Product p) {
        if (p.getCategory().getName().contains("Laptop")) {
            String sql = "insert into products(name, description, img_url, isactive, ramid, romid, chipid, brandid, modelid, unitid, categoryId, sku, price) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                ps.setInt(13, p.getPrice());
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (p.getCategory().getName().equals("RAM")) {
            String sql = "insert into products(name, description, img_url, isactive, ramid, brandid, unitid, categoryId, sku, price) values (?,?,?,?,?,?,?,?,?,?)";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRam().getId());
                ps.setInt(6, p.getBrand().getId());
                ps.setInt(7, p.getUnit().getId());
                ps.setInt(8, p.getCategory().getCategoryId());
                ps.setString(9, p.getSku());
                ps.setInt(10, p.getPrice());
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (p.getCategory().getName().equals("ROM")) {
            String sql = "insert into products(name, description, img_url, isactive, romid, brandid, unitid, categoryId, sku, price) values (?,?,?,?,?,?,?,?,?,?)";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRom().getId());
                ps.setInt(6, p.getBrand().getId());
                ps.setInt(7, p.getUnit().getId());
                ps.setInt(8, p.getCategory().getCategoryId());
                ps.setString(9, p.getSku());
                ps.setInt(10, p.getPrice());
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public Product getProductWithSpecification(Product p) {
        String sql = "select * from products where 1 = 1 and ";
        if (p.getCategory().getName().contains("Laptop")) {
            sql += "brandid = " + p.getBrand().getId() + " and ";
            sql += "modelid = " + p.getModel().getId() + " and ";
            sql += "chipid = " + p.getChip().getId() + " and ";
            sql += "ramid = " + p.getRam().getId() + " and ";
            sql += "romid = " + p.getRom().getId();
        } else if (p.getCategory().getName().equals("RAM")) {
            sql += "name like '" + p.getName() + "' and ";
            sql += "brandid = " + p.getBrand().getId() + " and ";
            sql += "ramid = " + p.getRam().getId();

        } else if (p.getCategory().getName().equals("ROM")) {
            sql += "name like '" + p.getName() + "' and ";
            sql += "brandid = " + p.getBrand().getId() + " and ";
            sql += "romid = " + p.getRom().getId();
        }
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapFromResultSetToProduct(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public Product getProductFromId(int productid) {
        String sql = "select * from products where productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, productid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapFromResultSetToProduct(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from products order by name";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productList.add(mapFromResultSetToProduct(rs));
            }
            return productList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public Product getProductFromSKU(String sku) {
        String sql = "select * from products where sku = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, sku);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapFromResultSetToProduct(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean changeProductStatus(Product p) {
        String sql = "UPDATE products SET isactive = ? WHERE productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setBoolean(1, p.isIsActive());
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProduct(Product p) {
        if (p.getCategory().getName().contains("Laptop")) {
            String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, ramid = ?, romid = ?, chipid = ?, unitid = ? , categoryId = ? , brandid = ?, modelid = ?, price = ? WHERE productid = ?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRam().getId());
                ps.setInt(6, p.getRom().getId());
                ps.setInt(7, p.getChip().getId());
                ps.setInt(8, p.getUnit().getId());
                ps.setInt(9, p.getCategory().getCategoryId());
                ps.setInt(10, p.getBrand().getId());
                ps.setInt(11, p.getModel().getId());
                ps.setInt(12, p.getPrice());
                ps.setInt(13, p.getProductId());
                System.out.println(sql);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(p.getCategory().getName().equals("RAM")){
            String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, ramid = ?, unitid = ? , categoryid = ? , brandid = ?, price = ? WHERE productid = ?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRam().getId());
                ps.setInt(6, p.getUnit().getId());
                ps.setInt(7, p.getCategory().getCategoryId());
                ps.setInt(8, p.getBrand().getId());
                ps.setInt(9, p.getPrice());
                ps.setInt(10, p.getProductId());
                System.out.println(sql);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (p.getCategory().getName().equals("ROM")) {
            String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, romid = ?, unitid = ? , categoryId = ? , brandid = ?, price = ? WHERE productid = ?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRam().getId());
                ps.setInt(6, p.getUnit().getId());
                ps.setInt(7, p.getCategory().getCategoryId());
                ps.setInt(8, p.getBrand().getId());
                ps.setInt(9, p.getPrice());
                ps.setInt(10, p.getProductId());
                System.out.println(sql);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Product mapFromResultSetToProduct(ResultSet rs) throws SQLException {
        RamDAO ram = new RamDAO();
        RomDAO rom = new RomDAO();
        ChipDAO chip = new ChipDAO();
        ModelDAO model = new ModelDAO();
        UnitDAO unit = new UnitDAO();
        CategoryDAO category = new CategoryDAO();
        BrandDAO brand = new BrandDAO();

        Product product = new Product();
        product.setName(rs.getString("name"));
        product.setProductId(rs.getInt("productid"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getInt("price"));
        product.setImgUrl(rs.getString("img_url"));
        product.setSku(rs.getString("sku"));
        product.setTotalQuantity(rs.getInt("total_quantity"));
        product.setIsActive(rs.getBoolean("isactive"));
        product.setRam(ram.getRamById(rs.getInt("ramid")));
        product.setRom(rom.getRomById(rs.getInt("romid")));
        product.setChip(chip.getChipById(rs.getInt("chipid")));
        product.setUnit(unit.getUnitById(rs.getInt("unitid")));
        product.setModel(model.getModelById(rs.getInt("modelid")));
        product.setCategory(category.getCategoryById(rs.getInt("categoryId")));
        product.setBrand(brand.getBrandById(rs.getInt("brandid")));
        return product;
    }

    public List<Product> searchProduct(String name, String sku, int categoryId, int brandId) {
        List<Product> productList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from products where ");
        List<String> parameter = new ArrayList<>();
        if (name != null && !name.trim().isEmpty()) {
            sql.append("name like ? and ");
            parameter.add("%" + name + "%");
        }

        if (sku != null && !sku.trim().isEmpty()) {
            sql.append("sku like ? and ");
            parameter.add("%" + sku + "%");

        }

        if (categoryId != -1) {
            sql.append("categoryid = ? and ");
            parameter.add(String.valueOf(categoryId));

        }

        if (brandId != -1) {
            sql.append("brandid = ? and ");
            parameter.add(String.valueOf(brandId));

        }

        sql.setLength(sql.length() - 5);

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());) {
            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productList.add(mapFromResultSetToProduct(rs));
            }
            return productList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return productList;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        for (Product i : dao.searchProduct("Dell Inspiron 15", "D15-23", 1, 1)) {
            System.out.println(i);
        }
        Product p = dao.getProductFromId(15);
        Product spec = dao.getProductWithSpecification(p);
        Product a = dao.getProductFromId(4);
        a.setProductId(15);
        a.setName("SSD Samsung 123");
        a.setSku("KVR32S22S6/4");
        dao.addProduct(a);
        System.out.println(spec);
        List<Product> test = dao.searchProduct("Asus", "", -1, 2);
        for (Product t : test) {
            System.out.println(t);
        }
        RomDAO rom = new RomDAO();
        for (Product i : dao.getProductList()) {
            System.out.println(i.getImgUrl());
        }
        Product p = dao.getProductFromId(18);
        p.setDescription("con mèo kêu");
        p.setRom(rom.getRomById(2));
        dao.updateProduct(p);
        
    }
}
