package com.powerup.user_microservice.domain.spi;

import com.powerup.user_microservice.domain.model.RoleModel;
import com.powerup.user_microservice.domain.model.UserModel;

import java.util.Optional;

public interface IUserPersistencePort {

    void saveUser(UserModel userModel);

    RoleModel getRolById(Long id);

    boolean existUserByEmailOrDni(String email, Integer dni);

    Optional<UserModel> getUserByEmail(String email);
}
