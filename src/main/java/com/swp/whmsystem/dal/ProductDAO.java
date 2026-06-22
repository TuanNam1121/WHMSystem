/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.ProductItem;

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
        } else if (p.getCategory().getName().equals("RAM")) {
            String sql = "insert into products(name, description, img_url, isactive, ramid, brandid, unitid, categoryid, sku) values (?,?,?,?,?,?,?,?,?)";
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
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (p.getCategory().getName().equals("ROM")) {
            String sql = "insert into products(name, description, img_url, isactive, romid, brandid, unitid, categoryid, sku, price) values (?,?,?,?,?,?,?,?,?)";
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
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // chỉ insert những cột có giá trị, null cho phần còn lại
            String sql = "insert into products(name, description, img_url, isactive, ramid, romid, chipid, brandid, modelid, unitid, categoryid, sku) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                // Nullable fields
                if (p.getRam() != null) {
                    ps.setInt(5, p.getRam().getId());
                } else {
                    ps.setNull(5, java.sql.Types.INTEGER);
                }
                if (p.getRom() != null) {
                    ps.setInt(6, p.getRom().getId());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }
                if (p.getChip() != null) {
                    ps.setInt(7, p.getChip().getId());
                } else {
                    ps.setNull(7, java.sql.Types.INTEGER);
                }
                if (p.getBrand() != null) {
                    ps.setInt(8, p.getBrand().getId());
                } else {
                    ps.setNull(8, java.sql.Types.INTEGER);
                }
                if (p.getModel() != null) {
                    ps.setInt(9, p.getModel().getId());
                } else {
                    ps.setNull(9, java.sql.Types.INTEGER);
                }
                if (p.getUnit() != null) {
                    ps.setInt(10, p.getUnit().getId());
                } else {
                    ps.setNull(10, java.sql.Types.INTEGER);
                }
                if (p.getCategory() != null) {
                    ps.setInt(11, p.getCategory().getCategoryId());
                } else {
                    ps.setNull(11, java.sql.Types.INTEGER);
                }
                ps.setString(12, p.getSku());
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
            if (rs.next()) {
                return mapFromResultSetToProduct(rs);
            }
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
            if (rs.next()) {
                return mapFromResultSetToProduct(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getProductNameById(int productid) {
        String sql = "select name from products where productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, productid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getProductQuantityById(int productid) {
        String sql = "select total_quantity from products where productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, productid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_quantity");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from products order by isActive desc";
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

    public List<Product> searchProductByName(String name) {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from products where name like ? order by isActive desc";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
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

    public void changeProductQuantity(int newQuantity, int id) {
        String sql = "update products set total_quantity = ? where productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Product getProductFromSKU(String sku) {
        String sql = "select * from products where sku = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, sku);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapFromResultSetToProduct(rs);
            }
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

    public boolean increaseQuantity(Product p) throws SQLException {
        String sql = "UPDATE products SET total_quantity = ? WHERE productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, p.getTotalQuantity());
            ps.setInt(2 , p.getProductId());
            System.out.println(sql);
            return ps.executeUpdate() != 0;
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateProduct(Product p) {
        if (p.getCategory().getName().contains("Laptop")) {
            String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, ramid = ?, romid = ?, chipid = ?, unitid = ? , categoryid = ? , brandid = ?, modelid = ?, sku = ? WHERE productid = ?";
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
                ps.setString(12, p.getSku());
                ps.setInt(13, p.getProductId());
                System.out.println(sql);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (p.getCategory().getName().equals("RAM")) {
            String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, ramid = ?, unitid = ? , categoryid = ? , brandid = ?, sku = ?,romid = ?, chipid = ?, modelid = ?  WHERE productid = ?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRam().getId());
                ps.setInt(6, p.getUnit().getId());
                ps.setInt(7, p.getCategory().getCategoryId());
                ps.setInt(8, p.getBrand().getId());
                ps.setString(9, p.getSku());
                ps.setNull(10, java.sql.Types.INTEGER);
                ps.setNull(11, java.sql.Types.INTEGER);
                ps.setNull(12, java.sql.Types.INTEGER);
                ps.setInt(13, p.getProductId());
                System.out.println(sql);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (p.getCategory().getName().equals("ROM")) {
            String sql = "UPDATE products SET name = ?, description = ?, img_url = ?, isactive = ?, romid = ?, unitid = ? , categoryid = ? , brandid = ?, sku = ?, ramid = ?, chipid = ?, modelid = ?  WHERE productid = ?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                ps.setInt(5, p.getRom().getId());
                ps.setInt(6, p.getUnit().getId());
                ps.setInt(7, p.getCategory().getCategoryId());
                ps.setInt(8, p.getBrand().getId());
                ps.setString(9, p.getSku());
                ps.setNull(10, java.sql.Types.INTEGER);
                ps.setNull(11, java.sql.Types.INTEGER);
                ps.setNull(12, java.sql.Types.INTEGER);
                ps.setInt(13, p.getProductId());
                System.out.println(sql);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String sql = "UPDATE products SET name=?, description=?, img_url=?, isactive=?, "
                    + "ramid=?, romid=?, chipid=?, brandid=?, modelid=?, unitid=?, categoryid=?"
                    + "WHERE productid=?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setString(3, p.getImgUrl());
                ps.setBoolean(4, p.isIsActive());
                if (p.getRam() != null) {
                    ps.setInt(5, p.getRam().getId());
                } else {
                    ps.setNull(5, java.sql.Types.INTEGER);
                }
                if (p.getRom() != null) {
                    ps.setInt(6, p.getRom().getId());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }
                if (p.getChip() != null) {
                    ps.setInt(7, p.getChip().getId());
                } else {
                    ps.setNull(7, java.sql.Types.INTEGER);
                }
                if (p.getBrand() != null) {
                    ps.setInt(8, p.getBrand().getId());
                } else {
                    ps.setNull(8, java.sql.Types.INTEGER);
                }
                if (p.getModel() != null) {
                    ps.setInt(9, p.getModel().getId());
                } else {
                    ps.setNull(9, java.sql.Types.INTEGER);
                }
                if (p.getUnit() != null) {
                    ps.setInt(10, p.getUnit().getId());
                } else {
                    ps.setNull(10, java.sql.Types.INTEGER);
                }
                if (p.getCategory() != null) {
                    ps.setInt(11, p.getCategory().getCategoryId());
                } else {
                    ps.setNull(11, java.sql.Types.INTEGER);
                }
                ps.setInt(12, p.getProductId());
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Product getProductFromCategoryId(int cateid) {
        String sql = "select * from products where categoryid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, cateid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapFromResultSetToProduct(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
        product.setImgUrl(rs.getString("img_url"));
        product.setSku(rs.getString("sku"));
        product.setTotalQuantity(rs.getInt("total_quantity"));
        product.setIsActive(rs.getBoolean("isactive"));
        product.setRam(ram.getRamById(rs.getInt("ramid")));
        product.setRom(rom.getRomById(rs.getInt("romid")));
        product.setChip(chip.getChipById(rs.getInt("chipid")));
        product.setUnit(unit.getUnitById(rs.getInt("unitid")));
        product.setModel(model.getModelById(rs.getInt("modelid")));
        product.setCategory(category.getCategoryById(rs.getInt("categoryid")));
        product.setBrand(brand.getBrandById(rs.getInt("brandid")));
        return product;
    }

    public List<Product> searchProduct(String name, int categoryId, int brandId, int isActive,
            String sortBy, int pageSize, int page) {
        List<Product> productList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select p.* from products p "
                + "left join categories c ON p.categoryid = c.categoryid "
                + "left join brands b ON p.brandid = b.brandid "
                + "where 1=1"
        );
        List<Object> parameter = new ArrayList<>();
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" and (p.name like ? or c.name like ? or b.name like ? or p.sku like ?)");
            parameter.add("%" + name.trim() + "%");
            parameter.add("%" + name.trim() + "%");
            parameter.add("%" + name.trim() + "%");
            parameter.add("%" + name.trim() + "%");
        }

        if (categoryId != -1) {
            sql.append(" and p.categoryid = ?");
            parameter.add(String.valueOf(categoryId));
        }

        if (brandId != -1) {
            sql.append(" and p.brandid = ?");
            parameter.add(String.valueOf(brandId));
        }

        if (isActive != -1) {
            sql.append(" and p.isActive = ?");
            parameter.add(String.valueOf(isActive));
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "nameAZ":
                    sql.append(" order by p.name asc");
                    break;
                case "nameZA":
                    sql.append(" order by p.name desc");
                    break;
                case "skuAZ":
                    sql.append(" order by p.sku asc");
                    break;
                case "skuZA":
                    sql.append(" order by p.sku desc");
                    break;
                case "cateAZ":
                    sql.append(" order by c.name asc");
                    break;
                case "cateZA":
                    sql.append(" order by c.name desc");
                    break;
                case "brandAZ":
                    sql.append(" order by b.name asc");
                    break;
                case "brandZA":
                    sql.append(" order by b.name desc");
                    break;
            }
        } else {
            sql.append(" order by p.productid asc");
        }

        int offset = (page - 1) * pageSize;
        sql.append(" limit ? offset ?");
        parameter.add(pageSize);
        parameter.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString());) {
            System.out.println(sql.toString());
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

    public int countProducts(String name, int categoryId, int brandId, int isActive) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from products p "
                + "left join categories c ON p.categoryid = c.categoryid "
                + "left join brands b ON p.brandid = b.brandid "
                + "where 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            String keyword = "%" + name.trim() + "%";
            sql.append(" and (p.name like ? or c.name like ? or b.name like ? or p.sku like ?)");
            parameters.add(keyword);
            parameters.add(keyword);
            parameters.add(keyword);
            parameters.add(keyword);
        }
        if (categoryId != -1) {
            sql.append(" and p.categoryid = ?");
            parameters.add(categoryId);
        }
        if (brandId != -1) {
            sql.append(" and p.brandid = ?");
            parameters.add(brandId);
        }
        if (isActive != -1) {
            sql.append(" and p.isActive = ?");
            parameters.add(isActive);
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    private ProductItem mapFromResultSetToProductItem(ResultSet rs) throws SQLException {
        ProductItem productItem = new ProductItem();
        productItem.setId(rs.getInt("id"));
        productItem.setSerial(rs.getString("serial"));
        productItem.setImportAt(rs.getTimestamp("imported_at"));
        productItem.setImportPrice(rs.getInt("imported_price"));
        productItem.setExportPrice(rs.getInt("export_price"));
        productItem.setActive((rs.getBoolean("isActive")));
        productItem.setGoodReceiptItemId(rs.getInt("goodreceiptsitemid"));
        productItem.setProductId(rs.getInt("product_id"));
        productItem.setStatus(rs.getString("status"));
        return productItem;
    }

    public List<ProductItem> getProductItems(int productId) {
        List<ProductItem> productItemList = new ArrayList<>();
        String sql = "select * from product_items pi "
                + "join products p on pi.product_id = p.productid"
                + " where p.productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productItemList.add(mapFromResultSetToProductItem(rs));
            }
            return productItemList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return productItemList;
    }

    public List<ProductItem> searchProductItems(int productId, String serial, String date,
            String status, String sortBy, int pageSize, int page) {
        List<ProductItem> productItemList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select pi.* from product_items pi "
                + "where pi.product_id = ?"
        );
        List<Object> parameter = new ArrayList<>();
        parameter.add(productId);

        if (serial != null && !serial.trim().isEmpty()) {
            sql.append(" and pi.serial like ?");
            parameter.add("%" + serial.trim() + "%");
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" and date_format(pi.imported_at, '%d/%m/%Y') = ?");
            parameter.add(date.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and pi.status = ?");
            parameter.add(status);
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "serialAZ":
                    sql.append(" order by pi.serial asc");
                    break;
                case "serialZA":
                    sql.append(" order by pi.serial desc");
                    break;
                case "dateNewest":
                    sql.append(" order by pi.imported_at desc");
                    break;
                case "dateOldest":
                    sql.append(" order by pi.imported_at asc");
                    break;
                case "importPriceLow":
                    sql.append(" order by pi.imported_price asc");
                    break;
                case "importPriceHigh":
                    sql.append(" order by pi.imported_price desc");
                    break;
                case "exportPriceLow":
                    sql.append(" order by pi.export_price asc");
                    break;
                case "exportPriceHigh":
                    sql.append(" order by pi.export_price desc");
                    break;
            }
        } else {
            sql.append(" order by pi.id asc");
        }

        int offset = (page - 1) * pageSize;
        sql.append(" limit ? offset ?");
        parameter.add(pageSize);
        parameter.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString());) {
            System.out.println(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productItemList.add(mapFromResultSetToProductItem(rs));
            }
            return productItemList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return productItemList;
    }

    public int countProductItems(int productId, String serial, String date, String status) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from product_items pi "
                + "where pi.product_id = ?"
        );
        List<Object> parameters = new ArrayList<>();
        parameters.add(productId);

        if (serial != null && !serial.trim().isEmpty()) {
            sql.append(" and pi.serial like ?");
            parameters.add("%" + serial.trim() + "%");
        }
        if (date != null && !date.trim().isEmpty()) {
            sql.append(" and date_format(pi.imported_at, '%d/%m/%Y') = ?");
            parameters.add(date.trim());
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and pi.status = ?");
            parameters.add(status);
        }
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public String getSKUFromId(int id) {
        String SKU = "";
        String sql = "select sku from products where productid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SKU = rs.getString("sku");
            }
            return SKU;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return SKU;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
//        RomDAO rom = new RomDAO();
//        for (Product i : dao.getProductList()) {
//            System.out.println(i.getImgUrl());
//        }
//        Product p1 = dao.getProductFromId(18);
//        p1.setDescription("con mèo kêu");
//        p1.setRom(rom.getRomById(2));
//        dao.updateProduct(p1);
//        List<Product> testSort = dao.searchProduct(null, -1, -1, -1, "brandAZ", 10, 1);
//        for (Product p2 : testSort) {
//            System.out.println(p2);
//        }
//
//        List<ProductItem> testItem = dao.getProductItems(1);
//        for (ProductItem pi : testItem) {
//            System.out.println(pi);
//        }
        String sku = dao.getSKUFromId(1);
        System.out.println(sku);
    }
}
