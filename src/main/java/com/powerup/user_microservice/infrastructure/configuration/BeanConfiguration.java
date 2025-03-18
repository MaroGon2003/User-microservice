package com.powerup.user_microservice.infrastructure.configuration;

import com.powerup.user_microservice.domain.api.IUserServicePort;
import com.powerup.user_microservice.domain.spi.IEncrypterPort;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.domain.usecase.UserUseCase;
import com.powerup.user_microservice.infrastructure.out.encrypter.EncrypterAdapter;
import com.powerup.user_microservice.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IUserRolesEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IRolRepository;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRepository;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IUserRolesRepository userRolesRepository;
    private final IUserRolesEntityMapper userRolesEntityMapper;
    private final IRolRepository rolRepository;
    private final IRolEntityMapper rolEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter( userRepository, rolRepository,userRolesRepository, userEntityMapper,rolEntityMapper, userRolesEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), encrypterPort());
    }

    @Bean
    public IEncrypterPort encrypterPort() {
        return new EncrypterAdapter(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
