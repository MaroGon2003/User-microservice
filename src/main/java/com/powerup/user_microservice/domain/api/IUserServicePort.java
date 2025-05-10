package com.powerup.user_microservice.domain.api;

import com.powerup.user_microservice.domain.model.UserModel;

public interface IUserServicePort {

    void saveUser(UserModel userModel);

    Long getUserIdByEmail(String email);

}
