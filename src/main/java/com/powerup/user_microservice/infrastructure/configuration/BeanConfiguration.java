package com.powerup.user_microservice.infrastructure.configuration;

import com.powerup.user_microservice.domain.api.IAuthServicePort;
import com.powerup.user_microservice.domain.api.IUserServicePort;
import com.powerup.user_microservice.domain.spi.*;
import com.powerup.user_microservice.domain.usecase.AuthUseCase;
import com.powerup.user_microservice.domain.usecase.UserUseCase;
import com.powerup.user_microservice.infrastructure.configuration.security.jwt.JwtToken;
import com.powerup.user_microservice.infrastructure.out.encrypter.EncrypterAdapter;
import com.powerup.user_microservice.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IRolRepository;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRepository;
import com.powerup.user_microservice.infrastructure.out.roleinterceptor.RoleInterceptorAdapter;
import jakarta.servlet.http.HttpServletRequest;
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
    private final IRolRepository rolRepository;
    private final IRolEntityMapper rolEntityMapper;

    private final IUserValidationPort validationPort;
    private final ILoginAttemptPersistencePort loginAttemptPersistencePort;
    private final HttpServletRequest request;
    private final JwtToken jwtToken;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter( userRepository, rolRepository, userEntityMapper,rolEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), encryptPort(), roleInterceptorPort());
    }

    @Bean
    public IEncrypterPort encryptPort() {
        return new EncrypterAdapter(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IRoleInterceptorPort roleInterceptorPort() {
        return new RoleInterceptorAdapter(request,jwtToken);
    }

    @Bean
    public IAuthServicePort authServicePort() {
        return new AuthUseCase(validationPort, loginAttemptPersistencePort);
    }

}
