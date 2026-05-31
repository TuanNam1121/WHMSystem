/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import java.util.*;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;

import com.swp.whmsystem.model.User;
import com.swp.whmsystem.model.UserDTO;
import com.swp.whmsystem.utils.HashPassword;

/**
 *
 * @author Admin
 */
public class UserDAO {

    public List<User> getAllUsers() {
        String sql = "Select * from users where roleid != 1";
        List<User> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User i = mapResultSetToUser(rs);
                list.add(i);
            }
            return list;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<User> searchUser(String keyword, String roleId, String sortBy) {
        List<String> acceptedSortField = new ArrayList<>(List.of("roleid", "username", "isactive"));
        String sql = "Select * from users where roleid != 1";
        List<User> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            if (keyword != null && !keyword.isBlank()) sql += " AND fullname like '%" + keyword + "%' ";
            if (roleId != null && !roleId.isBlank()) {
                int roleIdParse = Integer.parseInt(roleId);
                sql += " AND roleid = " + roleIdParse + " ";
            }
            if (sortBy != null && acceptedSortField.contains(sortBy)) {
                sql += " ORDER BY " + sortBy;
                if (sortBy.equals("isactive")) sql += " DESC";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User i = mapResultSetToUser(rs);
                list.add(i);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public User getUserFromId(int userId) {
        String sql = "Select * from users where userid = ?";
        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User i = mapResultSetToUser(rs);
                return i;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public User checkLogin(String username, String password) {
        String sql = "Select * from users where username = ?";
        try (Connection conn = DBContext.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("passwordhash");
                if (HashPassword.checkPassword(password, hashedPassword)) {
                    User i = mapResultSetToUser(rs);
                    return i;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean isActiveUser(User i) {
        String sql = "select isactive from users where userid = ?";
        try (Connection conn = DBContext.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, i.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isActive");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public User mapResultSetToUser(ResultSet rs) throws SQLException {
        User i = new User();
        i.setId(rs.getInt("userid"));
        i.setUserName(rs.getString("username"));
        i.setPhone(rs.getString("phone"));
        i.setGender(rs.getString("gender"));
        i.setEmail(rs.getString("email"));
        i.setFullName(rs.getString("fullname"));
        i.setIsActive(rs.getBoolean("isActive"));
        i.setRoleId(rs.getInt("roleid"));
        return i;
    }

    public boolean addNewUser(User user) {
        String sql = "Insert into users(username, passwordhash, roleid, phone, email, gender, fullname, isactive)"
                + "values (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, HashPassword.hashPassword(user.getPassword()));
            ps.setInt(3, user.getRoleId());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getFullName());
            ps.setBoolean(8, true);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT 1 FROM users WHERE LOWER(username) = LOWER(?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM users WHERE LOWER(email) = LOWER(?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }


    public boolean existsByUsernameExceptUserId(String username, int userId) {
        String sql = "SELECT 1 FROM users WHERE LOWER(username) = LOWER(?) AND userid != ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean existsByEmailExceptUserId(String email, int userId) {
        String sql = "SELECT 1 FROM users WHERE LOWER(email) = LOWER(?) AND userid != ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean updateUserInformation(User user) {
        String sql = "Update Users SET username = ?, roleid = ? , phone = ?, email = ?, gender = ?, fullname = ?, isActive = ? where userid = ?";
        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setInt(2, user.getRoleId());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getGender());
            ps.setString(6, user.getFullName());
            ps.setBoolean(7, user.isIsActive());
            ps.setInt(8, user.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean updateUserPassword(int userID, String newHashedPassword) {
        String sql = "Update Users SET passwordhash = ? where userid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, newHashedPassword);
            ps.setInt(2, userID);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public String getPasswordById(int userId) {
        String sql = "SELECT passwordhash FROM Users WHERE userid = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("passwordhash");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public User getUser(String username, String email) {
        String sql = "select userid, username, email from users where email = ? and username = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, email);
            ps.setString(2, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUserName(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public User getUserFullInformation(int userid) {

        String sql = "select * from users where userid = ?";

        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, userid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUserName(rs.getString("username"));
                    user.setFullName(rs.getString("fullname"));
                    user.setPassword(rs.getString("passwordhash"));
                    user.setRoleId(rs.getInt("roleid"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setGender(rs.getString("gender"));
                    user.setIsActive(rs.getBoolean("isactive"));
                    user.setFirstname(rs.getString("firstname"));
                    user.setLastname(rs.getString("lastname"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean updateProfile(UserDTO dto) {
        if (dto == null) {
            return false;
        }

        StringBuilder sql = new StringBuilder("update users set ");
        List<Object> parameter = new ArrayList<>();

        if (dto.getFirstname() != null && !dto.getFirstname().trim().isEmpty()) {
            sql.append("firstname = ?, ");
            parameter.add(dto.getFirstname());
        }

        if (dto.getLastname() != null && !dto.getLastname().trim().isEmpty()) {
            sql.append("lastname = ?, ");
            parameter.add(dto.getLastname());
        }

        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            sql.append("email = ?, ");
            parameter.add(dto.getEmail());
        }

        if (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) {
            sql.append("phone = ?, ");
            parameter.add(dto.getPhone());
        }

        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            sql.append("username = ?, ");
            parameter.add(dto.getUsername());
        }

        if (parameter.isEmpty()) {
            return false;
        }

        sql.setLength(sql.length() - 2);
        sql.append(" where userid = ?");
        parameter.add(dto.getId());

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString());) {

            for (int i = 0; i < parameter.size(); i++) {
                ps.setObject(i + 1, parameter.get(i));
            }

            int rowAffected = ps.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    public static void main(String[] args) {
        UserDAO user = new UserDAO();

        List<User> list = user.getAllUsers();
        for (User i : list) {
            System.out.println(i.toString());
        }

        // 2 Nguyen Thi Manager 5 Male 0900000002 manager@gmail.com
        User a = new User(2, "manager01", "Nguyen Thi Manager", 5, "0900000002", "manager@gmail.com", "Male", true);
        System.out.println(user.updateUserInformation(a));
        System.out.println(user.getUserFromId(2));

        System.out.println(user.getUserFullInformation(10));
    }
}
