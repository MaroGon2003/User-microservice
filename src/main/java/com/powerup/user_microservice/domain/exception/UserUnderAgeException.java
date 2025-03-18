package com.powerup.user_microservice.domain.exception;

public class UserUnderAgeException extends RuntimeException {

    public UserUnderAgeException(String message) {
        super(message);
    }
}
