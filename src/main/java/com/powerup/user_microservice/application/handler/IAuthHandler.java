package com.powerup.user_microservice.application.handler;

import com.powerup.user_microservice.application.dto.request.LoginRequestDto;
import com.powerup.user_microservice.application.dto.response.AuthResponseDto;

public interface IAuthHandler {

    AuthResponseDto login(LoginRequestDto loginRequestDto);

}
