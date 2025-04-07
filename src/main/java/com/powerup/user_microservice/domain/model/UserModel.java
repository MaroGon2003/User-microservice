package com.powerup.user_microservice.domain.model;

import java.time.LocalDate;

public class UserModel {

    private Long id;
    private String name;
    private String surName;
    private Integer dni;
    private String phone;
    private LocalDate birdDate;
    private String email;
    private String password;

    private RoleModel roleModel;

    public UserModel() {
    }

    public UserModel(Long id, String name, String surName, Integer dni, String phone, LocalDate birdDate, String email,
            String password, RoleModel roleModel) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.dni = dni;
        this.phone = phone;
        this.birdDate = birdDate;
        this.email = email;
        this.password = password;
        this.roleModel = roleModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirdDate() {
        return birdDate;
    }

    public void setBirdDate(LocalDate birdDate) {
        this.birdDate = birdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }
}
