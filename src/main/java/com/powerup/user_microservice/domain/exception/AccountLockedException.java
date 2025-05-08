package com.powerup.user_microservice.domain.exception;

public class AccountLockedException extends RuntimeException{

    public AccountLockedException(String message, String email) {
        super(String.format(message, email));
    }

}
