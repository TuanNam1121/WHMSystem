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

//

public class PermissionDAO extends DBContext{
//    Connection connection;
    PreparedStatement st;
    ResultSet rs;

    public PermissionDAO(){
    }
public List<Permission> getAllPermission(){
        String sql = "select * from permission";
        
        try(Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()){
            List<Permission> result = new ArrayList<>();
            while(rs.next()){
                Permission p = mapResultSetToPermission(rs);
                result.add(p);
            }
            return result;
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return null;
    }


 public Permission getPermission(int permission_id) {
        try {Connection conn = DBContext.getConnection(); 
            String sql = "select * from permission where permissionid=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setInt(1, permission_id);
            rs = st.executeQuery(); //only select
            if (rs.next()) {

                Permission p = mapResultSetToPermission(rs);

                return p;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
 
  public Permission getPermissionByName(String permission_name) {
        try {Connection conn = DBContext.getConnection(); 
            String sql = "select * from permission where permissionname=?";
            PreparedStatement st;
            ResultSet rs;
            st = conn.prepareStatement(sql);
            st.setString(1, permission_name);
            rs = st.executeQuery(); //only select
            if (rs.next()) {
                Permission p = mapResultSetToPermission(rs);

                return p;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void insertPermission(Permission p) {
        try {Connection conn = DBContext.getConnection();
            String sql = "insert into permission (permissionname,description) values (?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, p.getPermissionName());
            st.setString(2, p.getDescription());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updatePermission(Permission p) {
        try {Connection conn = DBContext.getConnection();
            String sql = "UPDATE permission SET permissionname = ?, description = ? WHERE permissionid = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, p.getPermissionName());
            st.setString(2, p.getDescription());
            st.setInt(3, p.getPermissionId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deletePermission(Permission p) {
        try {Connection conn = DBContext.getConnection();
            String sql = "DELETE FROM permission WHERE permissionid = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, p.getPermissionId());
            st.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
        private Permission mapResultSetToPermission(ResultSet rs) throws SQLException {
        Permission i = new Permission();
        i.setPermissionId(rs.getInt("permissionid"));
        i.setPermissionName(rs.getString("permissionname"));
        i.setDescription(rs.getString("description"));
        return i;
    }

     public static void main(String[] args) {
        PermissionDAO dao = null;
            dao = new PermissionDAO();
        List<Permission> list = dao.getAllPermission();
        Permission per = new Permission();
        per.setPermissionId(1);
        per.setDescription("Xem thong tin user");
        per.setPermissionName("VIEWUSER");
        dao.updatePermission(per);
        
        for(Permission i : dao.getAllPermission()){
            System.out.println(i.toString());
        }
    }
}
