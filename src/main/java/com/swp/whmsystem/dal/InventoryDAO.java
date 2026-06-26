package com.swp.whmsystem.dal;

import com.swp.whmsystem.dto.InventoryItemDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public List<InventoryItemDTO> getInventoryList() {
        return searchInventory("", "", 100000, 1);
    }

    public List<InventoryItemDTO> searchInventory(String keyword, String stockStatus,
            int pageSize, int page) {
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
        addStatusCondition(sql, stockStatus);
        sql.append(" order by quantity asc, p.name asc limit ? offset ?");

        int offset = (page - 1) * pageSize;
        parameters.add(pageSize);
        parameters.add(offset);

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            setParameters(ps, parameters);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapInventoryItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
        addStatusCondition(sql, stockStatus);
        sql.append(") inventory_result");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            setParameters(ps, parameters);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addStatusCondition(StringBuilder sql, String stockStatus) {
        if ("inStock".equals(stockStatus)) {
            sql.append(" having quantity > 10");
        } else if ("lowStock".equals(stockStatus)) {
            sql.append(" having quantity > 0 and quantity <= 10");
        } else if ("outOfStock".equals(stockStatus)) {
            sql.append(" having quantity = 0");
        }
    }

    private void setParameters(PreparedStatement ps, List<Object> parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            ps.setObject(i + 1, parameters.get(i));
        }
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
        item.setTotalValue(totalValue == null ? BigDecimal.ZERO : totalValue);
        return item;
    }
}
