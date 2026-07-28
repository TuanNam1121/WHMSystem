package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.InventoryItemDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public List<InventoryItemDTO> getInventoryList() {
        return searchInventory("", "", "quantityDesc", 100000, 1);
    }

    public List<InventoryItemDTO> getLowStockProducts(int threshold) {
        List<InventoryItemDTO> list = new ArrayList<>();
        String sql = "select p.productid, p.name, p.sku, p.img_url, u.name as unit_name, "
                + "count(pi.id) as quantity, coalesce(sum(pi.imported_price), 0) as total_value "
                + "from products p "
                + "left join units u on p.unitid = u.id "
                + "left join product_items pi on p.productid = pi.product_id and pi.status = 'AVAILABLE' "
                + "group by p.productid, p.name, p.sku, p.img_url, u.name "
                + "having quantity < ? "
                + "order by quantity asc, p.name asc";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapInventoryItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<InventoryItemDTO> searchInventory(String keyword, String stockStatus,
            String sortBy, int pageSize, int page) {
        List<InventoryItemDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "select p.productid, p.name, p.sku, p.img_url, u.name as unit_name, "
                + "count(pi.id) as quantity, coalesce(sum(pi.imported_price), 0) as total_value "
                + "from products p "
                + "left join units u on p.unitid = u.id "
                + "left join product_items pi on p.productid = pi.product_id and pi.status = 'AVAILABLE' "
                + "where 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (p.name like ? or p.sku like ? or u.name like ?)");
            parameters.add("%" + keyword.trim() + "%");
            parameters.add("%" + keyword.trim() + "%");
            parameters.add("%" + keyword.trim() + "%");
        }

        sql.append(" group by p.productid, p.name, p.sku, p.img_url, u.name");
        if ("inStock".equals(stockStatus)) {
            sql.append(" having quantity > 10");
        } else if ("lowStock".equals(stockStatus)) {
            sql.append(" having quantity > 0 and quantity <= 10");
        } else if ("outOfStock".equals(stockStatus)) {
            sql.append(" having quantity = 0");
        }

        if ("quantityAsc".equals(sortBy)) {
            sql.append(" order by quantity asc, p.name asc");
        } else if ("valueDesc".equals(sortBy)) {
            sql.append(" order by total_value desc, p.name asc");
        } else if ("valueAsc".equals(sortBy)) {
            sql.append(" order by total_value asc, p.name asc");
        } else {
            sql.append(" order by quantity desc, p.name asc");
        }
        sql.append(" limit ? offset ?");

        int offset = (page - 1) * pageSize;
        parameters.add(pageSize);
        parameters.add(offset);

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapInventoryItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean isHavingProductInInventory(int productId){
        String sql = """
                     select count(pi.id), p.name from products p
                     left join product_items pi on p.productid = pi.product_id
                     where pi.product_id = ? and pi.status = 'AVAILABLE'""";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countInventory(String keyword, String stockStatus) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from ("
                + "select p.productid, count(pi.id) as quantity "
                + "from products p "
                + "left join units u on p.unitid = u.id "
                + "left join product_items pi on p.productid = pi.product_id and pi.status = 'AVAILABLE' "
                + "where 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (p.name like ? or p.sku like ? or u.name like ?)");
            parameters.add("%" + keyword.trim() + "%");
            parameters.add("%" + keyword.trim() + "%");
            parameters.add("%" + keyword.trim() + "%");
        }

        sql.append(" group by p.productid");
        if ("inStock".equals(stockStatus)) {
            sql.append(" having quantity > 10");
        } else if ("lowStock".equals(stockStatus)) {
            sql.append(" having quantity > 0 and quantity <= 10");
        } else if ("outOfStock".equals(stockStatus)) {
            sql.append(" having quantity = 0");
        }
        sql.append(") inventory_result");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private InventoryItemDTO mapInventoryItem(ResultSet rs) throws Exception {
        InventoryItemDTO item = new InventoryItemDTO();
        item.setProductId(rs.getInt("productid"));
        item.setProductName(rs.getString("name"));
        item.setSku(rs.getString("sku"));
        item.setImgUrl(rs.getString("img_url"));
        item.setUnitName(rs.getString("unit_name"));
        item.setQuantity(rs.getInt("quantity"));

        BigDecimal totalValue = rs.getBigDecimal("total_value");
        if (totalValue == null) {
            totalValue = BigDecimal.ZERO;
        }
        item.setTotalValue(totalValue);
        return item;
    }
    
    public static void main(String[] args) {
        InventoryDAO dao = new InventoryDAO();
        System.out.println(dao.isHavingProductInInventory(2));
    }
}
