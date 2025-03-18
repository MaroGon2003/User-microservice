package com.powerup.user_microservice.domain.exception;

public class InvalidUserRoleException extends RuntimeException {

    public InvalidUserRoleException(String message) {
        super(message);
    }
}
