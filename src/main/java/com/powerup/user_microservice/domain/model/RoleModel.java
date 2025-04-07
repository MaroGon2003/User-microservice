package com.powerup.user_microservice.domain.model;

public class RoleModel {

    private Long id;
    private RoleEnum name;
    private String description;

    public RoleModel() {
    }

    public RoleModel(Long id, RoleEnum name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
