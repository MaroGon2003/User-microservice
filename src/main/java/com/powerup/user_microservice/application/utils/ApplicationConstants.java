package com.powerup.user_microservice.application.utils;

public class ApplicationConstants {

    private ApplicationConstants() {
        // Prevent instantiation
    }

    //LOGIN REQUEST
    public static final String EMAIL_NOT_EMPTY = "Email must not be empty";
    public static final String EMAIL_INVALID = "Must be a well-formed email address";
    public static final String PASSWORD_NOT_EMPTY = "Password must not be empty";


    // USER REQUEST
    public static final String NAME_NOT_EMPTY = "Name must not be empty";
    public static final String SURNAME_NOT_EMPTY = "Surname must not be empty";
    public static final String DNI_NOT_NULL = "DNI must not be null";
    public static final String PHONE_NOT_EMPTY = "Phone must not be empty";
    public static final String PHONE_INVALID = "The phone number should max 13 digits and only numbers and also can start with +";
    public static final String BIRTH_DATE_NOT_NULL = "Birth date must not be null";
    public static final int PHONE_MAX_SIZE = 13;
    public static final String PHONE_REGEX = "\\+?\\d+";

}
