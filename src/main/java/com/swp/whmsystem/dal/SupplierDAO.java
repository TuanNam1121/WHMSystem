package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public int countActiveSuppliers() {
        String sql = "select count(*) from suppliers where isactive = 1";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(rs.getInt("supplierid"));
        supplier.setSupplierName(rs.getString("suppliername"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setEmail(rs.getString("email"));
        supplier.setAddress(rs.getString("address"));
        supplier.setActive(rs.getBoolean("isactive"));
        return supplier;
    }

    public boolean insertSupplier(Supplier supplier) {
        String sql = "insert into suppliers (suppliername, phone, email, address) values (?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplier.getSupplierName());
            preparedStatement.setString(2, supplier.getPhone());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getAddress());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateSupplier(Supplier supplier) {
        String sql = "update suppliers set suppliername = ?, phone = ?, email = ?, address = ?, isactive = ? where supplierid = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplier.getSupplierName());
            preparedStatement.setString(2, supplier.getPhone());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getAddress());
            preparedStatement.setBoolean(5, supplier.isActive());
            preparedStatement.setInt(6, supplier.getSupplierId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Supplier> getAllSuppliers() {
        String sql = "select * from suppliers";
        List<Supplier> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToSupplier(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Supplier> getActiveSuppliers() {
        String sql = "select * from suppliers where isactive = 1";
        List<Supplier> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToSupplier(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Supplier getSupplierByName(String name) {
        String sql = "select * from suppliers where suppliername = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToSupplier(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Supplier getSupplierByPhone(String phone) {
        String sql = "select * from suppliers where phone = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToSupplier(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Supplier getSupplierByEmail(String email) {
        String sql = "select * from suppliers where email = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToSupplier(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Supplier getSupplierById(int id) {
        String sql = "select * from suppliers where supplierid = ?";
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToSupplier(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int countSupplier(String code, String name, String active) {
        StringBuilder sql = new StringBuilder("select count(*) from suppliers where 1=1");
        List<Object> parameter = new ArrayList<>();

        if (code != null && !code.trim().isEmpty()) {
            sql.append(" and supplierid = ?");
            try {
                parameter.add(Integer.parseInt(code.trim()));
            } catch (NumberFormatException e) {
                return 0; // Invalid ID format means no results
            }
        }
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" and suppliername like ?");
            parameter.add("%" + name.trim() + "%");
        }
        if (active != null && !active.trim().isEmpty()) {
            sql.append(" and isactive = ?");
            parameter.add("1".equals(active.trim()));
        }

        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                preparedStatement.setObject(i + 1, parameter.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<Supplier> searchSupplier(String code, String name, String active, int pageSize, int page) {
        StringBuilder sql = new StringBuilder("select * from suppliers where 1=1");
        List<Object> parameter = new ArrayList<>();

        if (code != null && !code.trim().isEmpty()) {
            sql.append(" and supplierid = ?");
            try {
                parameter.add(Integer.parseInt(code.trim()));
            } catch (NumberFormatException e) {
                return new ArrayList<>();
            }
        }
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" and suppliername like ?");
            parameter.add("%" + name.trim() + "%");
        }
        if (active != null && !active.trim().isEmpty()) {
            sql.append(" and isactive = ?");
            parameter.add("1".equals(active.trim()));
        }

        sql.append(" order by supplierid desc limit ? offset ?");
        parameter.add(pageSize);
        parameter.add((page - 1) * pageSize);

        List<Supplier> list = new ArrayList<>();
        try (Connection connection = DBContext.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameter.size(); i++) {
                preparedStatement.setObject(i + 1, parameter.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToSupplier(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
