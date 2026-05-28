package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Brand;
import com.swp.whmsystem.model.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDAO {
    public List<Model> getAll() {
        List<Model> list = new ArrayList<>();

        String sql = "SELECT m.modelid, m.name, m.isactive, " +
                "b.brandid, b.name AS brand_name, b.description, b.createdat, b.updatedat " +
                "FROM models m JOIN brands b ON m.brandid = b.brandid";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Model model = new Model();
                model.setId(rs.getInt("modelid"));
                model.setName(rs.getString("name"));
                model.setActive(rs.getBoolean("isactive"));

                Brand brand = new Brand();
                brand.setId(rs.getInt("brandid"));
                brand.setName(rs.getString("brand_name"));
                brand.setDescription(rs.getString("description"));
                brand.setCreatedAt(rs.getDate("createdat"));
                brand.setUpdatedAt(rs.getDate("updatedat"));
                model.setBrand(brand);

                list.add(model);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
