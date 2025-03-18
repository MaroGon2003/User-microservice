package com.powerup.user_microservice.infrastructure.out.encrypter;

import com.powerup.user_microservice.domain.spi.IEncrypterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class EncrypterAdapter implements IEncrypterPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
