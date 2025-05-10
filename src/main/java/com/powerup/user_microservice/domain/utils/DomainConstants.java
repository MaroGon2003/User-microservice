package com.powerup.user_microservice.domain.utils;

public class DomainConstants {

    private DomainConstants() {
    }

    //VALIDATION ---------------------------------------------------------------
    public static final int AGE_MIN = 18;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX = "\\+?\\d{1,13}";
    public static final String DNI_REGEX = "\\d+";

    //ERROR MESSAGES ---------------------------------------------------------------

    public static final String USER_UNDER_AGE = "User is under age";
    public static final String USER_ALREADY_EXISTS = "User already exists with email: %s or DNI: %s";
    public static final String INVALID_USER_ROLE = "Invalid user role";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email format";
    public static final String INVALID_PHONE_MESSAGE = "Invalid phone format";
    public static final String INVALID_DNI_MESSAGE = "DNI must be numeric";
    public static final String USER_NOT_FOUND = "User not found with email";


    //LOGIN ----------------------------------------------------------------
    public static final String ACCOUNT_LOCKED_MESSAGE = "Account: %s is locked due to too many failed login attempts";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid email or password";

    public static final int MAX_ATTEMPTS = 3;

}
