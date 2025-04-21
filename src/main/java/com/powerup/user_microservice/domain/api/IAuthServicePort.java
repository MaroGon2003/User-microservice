package com.powerup.user_microservice.domain.api;

public interface IAuthServicePort {

    String login(String email, String password);

}
