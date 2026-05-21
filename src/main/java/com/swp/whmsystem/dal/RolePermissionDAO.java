package com.swp.whmsystem.dal;

import com.swp.whmsystem.model.*;
import com.swp.whmsystem.dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionDAO {
    PreparedStatement st;
    ResultSet rs;

    public RolePermissionDAO() {
    }
    

    public List<RolePermission> getAllRolePermission() {
        String sql = "select * from role_permissions";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<RolePermission> result = new ArrayList<>();
            while (rs.next()) {
                RolePermission i = mapResultSetToRequest(rs);
                result.add(i);
            }
            return result;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public void insertRolePermission(int permissionId, int roleId) {
        try {Connection conn = DBContext.getConnection();
            String sql = "insert into role_permissions (roleid, permissionid) values (?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, roleId);
            st.setInt(2, permissionId);
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void deleteRolePermission(Permission p) {
        try {Connection conn = DBContext.getConnection();
            String sql = "DELETE FROM role_permissions WHERE permissionid = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, p.getPermissionId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void deletePermissionRole(Role r) {
        try {Connection conn = DBContext.getConnection();
            String sql = "DELETE FROM role_permissions WHERE roleid = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, r.getRoleId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public List<Permission> getPermissionByRole(int roleId) {

        String sql =  "SELECT * FROM permission p JOIN role_permissions rp ON rp.permissionid = p.permissionid WHERE rp.roleid = ?";

        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            List<Permission> result = new ArrayList<>();

            while (rs.next()) {
                int permissionId = rs.getInt("permissionid");
                String permissionName = rs.getString("permissionname");
                String description = rs.getString("description");
                Permission r = new Permission(permissionId, permissionName, description);
                result.add(r);
            }

            return result;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public List<Role> getRoleByPermission(Permission p) {

        String sql = "SELECT * FROM role r JOIN role_permissions rp ON rp.roleid = r.roleid WHERE rp.permissionid = ?";
        try (
                Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getPermissionId());
            ResultSet rs = ps.executeQuery();
            List<Role> result = new ArrayList<>();

            while (rs.next()) {
                int roleId = rs.getInt("roleid");
                String roleName = rs.getString("rolename");
                boolean isActive = rs.getBoolean("isActive");
                Role r = new Role(roleId, roleName, isActive);
                result.add(r);
            }

            return result;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    private RolePermission mapResultSetToRequest(ResultSet rs) throws SQLException {
        RolePermission i = new RolePermission();
        i.setPermissionId(rs.getInt("roleid"));
        i.setRoleId(rs.getInt("permissionid"));
        return i;
    }

    public static void main(String[] args) {
        RolePermissionDAO dao = new RolePermissionDAO();
        List<RolePermission> list = dao.getAllRolePermission();
        for (RolePermission i : list) {
            System.out.println(i.toString());
        }
    }
}
