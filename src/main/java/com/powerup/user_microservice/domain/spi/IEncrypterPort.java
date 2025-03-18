package com.powerup.user_microservice.domain.spi;

public interface IEncrypterPort {

    String encryptPassword(String password);

}
