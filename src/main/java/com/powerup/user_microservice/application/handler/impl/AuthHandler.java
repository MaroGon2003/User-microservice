package com.powerup.user_microservice.application.handler.impl;

import com.powerup.user_microservice.application.dto.request.LoginRequestDto;
import com.powerup.user_microservice.application.dto.response.AuthResponseDto;
import com.powerup.user_microservice.application.handler.IAuthHandler;
import com.powerup.user_microservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort authServicePort;

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {

        return new AuthResponseDto(authServicePort.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

    }
}
