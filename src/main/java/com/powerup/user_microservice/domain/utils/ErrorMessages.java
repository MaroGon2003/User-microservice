package com.powerup.user_microservice.domain.utils;

public class ErrorMessages {

    private ErrorMessages() {
        throw new IllegalStateException("This class cannot be instantiated");
    }

    public static final String USER_UNDER_AGE = "User is under age";
    public static final String USER_ALREADY_EXISTS = "User already exists with email: %s or DNI: %s";
    public static final String INVALID_USER_ROLE = "Invalid user role";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email format";
    public static final String INVALID_PHONE_MESSAGE = "Invalid phone format";
    public static final String INVALID_DNI_MESSAGE = "DNI must be numeric";


}
