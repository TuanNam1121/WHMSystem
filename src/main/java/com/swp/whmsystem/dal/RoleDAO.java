/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;
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
public class RoleDAO {

    public List<Role> getAllRole() {
        String sql = "select * from role";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Role> result = new ArrayList<>();
            while (rs.next()) {
                Role i = mapResultSetToRole(rs);
                result.add(i);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Role> getAllRoleToAssign() {
        String sql = "select * from role where rolename != 'Admin'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Role> result = new ArrayList<>();
            while (rs.next()) {
                Role i = mapResultSetToRole(rs);
                result.add(i);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public String getRoleNameFromUserID(int userId) {
        String sql = "select r.rolename from role r join user u on u.roleid = r.roleid where u.userid = ?;";

        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("r.rolename");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getRoleNamFromRoleID(int id) {
        String sql = "select rolename from role where roleid = ?";

        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getString("rolename");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public Integer getRoleIdFromRoleName(String roleName) {
        String sql = "select roleid from role where rolename = ?";

        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("roleid");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role i = new Role();
        i.setRoleId(rs.getInt("roleid"));
        i.setRoleName(rs.getString("rolename"));
        i.setIsActive(rs.getBoolean("isActive"));
        return i;
    }

    //HungTQ added
    public Role getRoleById(int id) {
        String sql = "select * from role where roleid = ?";
        try (Connection conn = DBContext.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Role i = mapResultSetToRole(rs);
                return i;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    //also HungTQ added
    public void updateRole(Role r) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "UPDATE role SET rolename = ?, isActive = ? WHERE roleid = ?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setString(1, r.getRoleName());
            st.setBoolean(2, r.isIsActive());
            st.setInt(3, r.getRoleId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void insertRole(Role r) {
        try {
            Connection conn = DBContext.getConnection();
            String sql = "insert into role (rolename,isActive) values (?,?)";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setString(1, r.getRoleName());
            st.setBoolean(2, r.isIsActive());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RoleDAO dao = new RoleDAO();
        List<Role> res = dao.getAllRole();

        for (Role i : res) {
            System.out.println(i.getRoleId() + " " + i.getRoleName());
        }
        dao.getRoleNamFromRoleID(1);
        System.out.println(dao.getRoleNameFromUserID(2));
        System.out.println(dao.getRoleIdFromRoleName("STAFF"));
    }
}
