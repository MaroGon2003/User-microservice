package com.powerup.user_microservice.application.handler.impl;

import com.powerup.user_microservice.application.dto.request.LoginRequestDto;
import com.powerup.user_microservice.application.dto.response.AuthResponseDto;
import com.powerup.user_microservice.application.handler.IAuthHandler;
import com.powerup.user_microservice.infrastructure.configuration.security.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtToken.generateToken(authentication);
        return new AuthResponseDto(jwt);
    }
}
