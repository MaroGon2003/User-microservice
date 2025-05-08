package com.powerup.user_microservice.domain.spi;

public interface IUserValidationPort {

    String validateUser(String email, String password);

}
