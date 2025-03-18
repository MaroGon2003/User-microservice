package com.powerup.user_microservice.domain.model;

public class UserRolesModel {

    private UserModel userModel;

    private RolModel rolModel;

    public UserRolesModel() {
    }

    public UserRolesModel(UserModel userModel, RolModel rolModel) {
        this.userModel = userModel;
        this.rolModel = rolModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public RolModel getRolModel() {
        return rolModel;
    }

    public void setRolModel(RolModel rolModel) {
        this.rolModel = rolModel;
    }
}
