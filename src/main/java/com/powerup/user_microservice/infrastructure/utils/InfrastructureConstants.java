package com.powerup.user_microservice.infrastructure.utils;

public class InfrastructureConstants {

    private InfrastructureConstants() {
    }


    //HTTP STATUS CODES
    public static final String RESPONSE_CODE_200 = "200";
    public static final String RESPONSE_CODE_201 = "201";
    public static final String RESPONSE_CODE_400 = "400";
    public static final String RESPONSE_CODE_500 = "500";

    // User controller
    public static final String USER_REQUEST_MAPPING = "/user";
    public static final String USER_TAG_NAME = "User";
    public static final String USER_TAG_DESCRIPTION = "Endpoints for managing users";
    public static final String USER_OPERATION_SUMMARY = "Create a new user";
    public static final String USER_OPERATION_DESCRIPTION = "Creates a new user and returns a 201 Created response.";
    public static final String USER_RESPONSE_201_DESCRIPTION = "User created successfully";
    public static final String USER_RESPONSE_400_DESCRIPTION = "Invalid request body";
    public static final String USER_RESPONSE_500_DESCRIPTION = "Internal server error";

    // Auth controller
    public static final String AUTH_REQUEST_MAPPING = "/auth";
    public static final String AUTH_TAG_NAME = "Authentication";
    public static final String AUTH_TAG_DESCRIPTION = "Endpoints for user authentication";
    public static final String AUTH_OPERATION_SUMMARY = "Login";
    public static final String AUTH_OPERATION_DESCRIPTION = "Endpoint for user login";
    public static final String AUTH_RESPONSE_200_DESCRIPTION = "Login successful";
    public static final String AUTH_RESPONSE_400_DESCRIPTION = "Invalid credentials";
    public static final String AUTH_RESPONSE_500_DESCRIPTION = "Internal server error";
    public static final String AUTH_LOGIN_ENDPOINT = "/login";


    // Role Interceptor constants
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_CLAIM = "role";
    public static final String JWT_MISSING_OR_INVALID = "JWT token is missing or invalid";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SELLER = "SELLER";


    //Message error login
    public static final String USER_NOT_FOUND = "User not found with email: ";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String TOKEN_INVALID = "Token is invalid";
}
