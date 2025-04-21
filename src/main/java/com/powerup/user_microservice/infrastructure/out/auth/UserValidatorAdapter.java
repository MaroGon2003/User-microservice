package com.powerup.user_microservice.infrastructure.out.auth;

import com.powerup.user_microservice.domain.spi.IUserValidationPort;
import com.powerup.user_microservice.infrastructure.configuration.security.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidatorAdapter implements IUserValidationPort {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    @Override
    public String validateUser(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  jwtToken.generateToken(authentication);

    }
}
