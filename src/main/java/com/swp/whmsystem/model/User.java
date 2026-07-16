/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.model;

/**
 *
 * @author Admin
 */
public class User {

    private int id;
    private String userName;
    private String fullName;
    private String password;
    private int roleId;
    private String phone;
    private String email;
    private String gender;
    private boolean isActive;
    private String firstname;
    private String lastname;

    public User() {
    }

    public User(int id, String userName, String fullName, int roleId, String phone, String email, String gender, boolean isActive) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.roleId = roleId;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.isActive = isActive;
    }

    public User(String userName, String fullName, String password, int roleId, String phone, String email, String gender, boolean isActive, String firstName, String lastName) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.roleId = roleId;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.firstname = firstName;
        this.lastname = lastName;
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", fullName=" + fullName + ", role=" + roleId + ", phone=" + phone + ", email=" + email + ", gender=" + gender + ", isActive=" + isActive + '}';
    }

}
