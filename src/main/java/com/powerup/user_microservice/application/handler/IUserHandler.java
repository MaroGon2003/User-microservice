package com.powerup.user_microservice.application.handler;

import com.powerup.user_microservice.application.dto.request.UserRequestDto;

public interface IUserHandler {

    void saveUserSeller(UserRequestDto userRequestDto);

}
