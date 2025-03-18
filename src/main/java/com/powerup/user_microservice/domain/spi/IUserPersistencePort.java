package com.powerup.user_microservice.domain.spi;

import com.powerup.user_microservice.domain.model.RolModel;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.model.UserRolesModel;

public interface IUserPersistencePort {

    UserModel saveUser(UserModel userModel);

    void saveUserRoles(UserRolesModel userRolesModel);

    RolModel getRolById(Long id);

    boolean existUserByEmailOrDni(String email, Integer dni);
}
