package com.powerup.user_microservice.domain.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("This class cannot be instantiated");
    }

    public static final int AGE_MIN = 18;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX = "\\+?\\d{1,13}";
    public static final String DNI_REGEX = "\\d+";

}
