package com.powerup.user_microservice.domain.spi;

public interface IRoleInterceptorPort {

    String getRoleName();

    boolean isAdmin();

    boolean isSeller();

    boolean jwtExists();
}
