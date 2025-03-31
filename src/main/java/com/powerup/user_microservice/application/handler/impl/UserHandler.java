package com.powerup.user_microservice.application.handler.impl;

import com.powerup.user_microservice.application.dto.request.UserRequestDto;
import com.powerup.user_microservice.application.handler.IUserHandler;
import com.powerup.user_microservice.application.mapper.IUserRequestMapper;
import com.powerup.user_microservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {

        userServicePort.saveUser(userRequestMapper.toUserModel(userRequestDto));

    }
}
