/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.model;

/**
 *
 * @author Admin
 */
public class Role {

    private int roleId;
    private String roleName;
    private boolean isActive;

    public Role(int roleId, String roleName, boolean isActive) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isActive = isActive;
    }

    public Role() {
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
