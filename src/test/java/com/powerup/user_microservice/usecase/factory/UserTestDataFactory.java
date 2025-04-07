package com.powerup.user_microservice.usecase.factory;

import com.powerup.user_microservice.domain.model.RoleEnum;
import com.powerup.user_microservice.domain.model.RoleModel;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.utils.DomainConstants;

import java.time.LocalDate;

public class UserTestDataFactory {

    public static UserModel createValidUser() {

        UserModel userModel = new UserModel();

        userModel.setId(1L);
        userModel.setName("John");
        userModel.setSurName("Doe");
        userModel.setDni(12345678);
        userModel.setPhone("123456789");
        userModel.setBirdDate(LocalDate.of(2000, 1, 1));
        userModel.setEmail("test@example.com");
        userModel.setPassword("password");

       return userModel;
    }

    public static UserModel createUserWithInvalidEmail() {

        RoleModel roleModel = createRoleModel();

        return new UserModel(
                1L,
                "John",
                "Doe",
                12345678,
                "123456789",
                LocalDate.of(2000, 1, 1),
                "invalid-email",
                "password",
                roleModel
        );
    }

    public static UserModel createUserWithInvalidPhone() {

        RoleModel roleModel = createRoleModel();

        return new UserModel(
                1L,
                "John",
                "Doe",
                12345678,
                "invalid-phone",
                LocalDate.of(2000, 1, 1),
                "test@example.com",
                "password",
                roleModel
        );
    }

    public static UserModel createUserWithInvalidDni() {

        RoleModel roleModel = createRoleModel();

        return new UserModel(
                1L,
                "John",
                "Doe",
                null,
                "123456789",
                LocalDate.of(2000, 1, 1),
                "test@example.com",
                "password",
                roleModel
        );
    }

    public static UserModel createUnderageUser() {

        RoleModel roleModel = createRoleModel();

        return new UserModel(
                1L,
                "John",
                "Doe",
                12345678,
                "123456789",
                LocalDate.now().minusYears(DomainConstants.AGE_MIN - 1),
                "test@example.com",
                "password",
                roleModel
        );
    }

    private static RoleModel createRoleModel(){
        RoleModel roleModel = new RoleModel();
        roleModel.setId(1L);
        roleModel.setName(RoleEnum.ROLE_SELLER);
        roleModel.setDescription("Seller role");
        return roleModel;
    }
}
