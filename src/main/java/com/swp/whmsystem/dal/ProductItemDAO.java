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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductItemDAO {

    public List<ProductItem> getAllProductItem() {
        List<ProductItem> list = new ArrayList<>();
        String sql = "select * from product_items order by imported_at desc";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductItem p = mapResultsetToProductItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductItem> getAllProductItemByGoodReceiptItemId(int goodReceiptItemId) {
        List<ProductItem> list = new ArrayList<>();
        String sql = "select * from product_items where goodreceiptsitemid = ? order by imported_at desc";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, goodReceiptItemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductItem p = mapResultsetToProductItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductItem> getAllProductItemByGoodReceiptID(int grId) {
        List<ProductItem> list = new ArrayList<>();
        String sql = """
                     select pi.id, pi.serial, pi.imported_price, pi.export_price, pi.isactive, pi.imported_at, pi.product_id, pi.goodreceiptsitemid, pi.status 
                     from product_items pi join good_receipts_items gri on pi.goodreceiptsitemid = gri.id where gri.goodreceiptid = ?""";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, grId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductItem p = mapResultsetToProductItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductItem> getAllProductItemByProductId(int prId) {
        List<ProductItem> list = new ArrayList<>();
        String sql = "select * from product_items where product_id = ? order by imported_at desc";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, prId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductItem p = mapResultsetToProductItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductItem> getAllProductItemByOrderId(int orderId) {
        List<ProductItem> list = new ArrayList<>();
        String sql = "select p.* from product_items p "
                + "join export_receipt_serials ers on p.id = ers.product_item_id "
                + "join export_receipt_details erd on ers.export_receipt_detail_id = erd.id "
                + "join export_receipts er on erd.export_receipt_id = er.id "
                + "where er.order_id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductItem p = mapResultsetToProductItem(resultSet);
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductItemStatus(ProductItem pi) {
        String sql = "update product_items set status = ? where id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, pi.getStatus());
            ps.setInt(2, pi.getId());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ProductItem mapResultsetToProductItem(ResultSet rs) throws SQLException {
        ProductItem i = new ProductItem();
        i.setId(rs.getInt("id"));
        i.setSerial(rs.getString("serial"));
        i.setProductId(rs.getInt("product_id"));
        i.setImportPrice(rs.getInt("imported_price"));
        i.setExportPrice(rs.getInt("export_price"));
        i.setStatus(rs.getString("status"));
        i.setGoodReceiptItemId(rs.getInt("goodreceiptsitemid"));
        i.setImportAt(rs.getTimestamp("imported_at"));
        return i;
    }

    public boolean insertProductItem(ProductItem item) {
        String sql = "INSERT INTO product_items (serial, product_id, imported_price, goodreceiptsitemid, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getSerial());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getImportPrice());
            preparedStatement.setInt(4, item.getGoodReceiptItemId());
            preparedStatement.setString(5, item.getStatus() != null ? item.getStatus() : "AVAILABLE");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int insertProductItemReturnId(ProductItem item) {
        String sql = "INSERT INTO product_items (serial, product_id, imported_price, goodreceiptsitemid, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getSerial());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getImportPrice());
            preparedStatement.setObject(4, item.getGoodReceiptItemId() == 0 ? null : item.getGoodReceiptItemId());
            preparedStatement.setString(5, item.getStatus() != null ? item.getStatus() : "AVAILABLE");

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public ProductItem existedSerial(String serial) {
        String sql = "SELECT * FROM wms.product_items where serial = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, serial);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return mapResultsetToProductItem(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ProductItem existedSerial(int productId, String serial) {
        String sql = "SELECT * FROM wms.product_items where serial = ? and product_id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, serial);
            preparedStatement.setInt(2, productId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return mapResultsetToProductItem(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int getExportPriceWithSoldStatus(int productItemId) {
        String sql = "select export_price from product_items "
                + "where status = 'SOLD' and id = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productItemId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("export_price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static void main(String[] args) {
        ProductItemDAO dao = new ProductItemDAO();
        System.out.println(dao.getExportPriceWithSoldStatus(6));
    }

}
