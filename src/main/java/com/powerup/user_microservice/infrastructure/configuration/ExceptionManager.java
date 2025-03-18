package com.powerup.user_microservice.infrastructure.configuration;

import com.powerup.user_microservice.domain.exception.InvalidUserRoleException;
import com.powerup.user_microservice.domain.exception.UserAlreadyExistsException;
import com.powerup.user_microservice.domain.exception.UserUnderAgeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionManager {

    private static final String ERROR = "error";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> illegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ResponseEntity<Map<String,String>> invalidUserRoleException(InvalidUserRoleException e){
        return ResponseEntity.badRequest().body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> userAlreadyExistsException(UserAlreadyExistsException e){
        return ResponseEntity.badRequest().body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler(UserUnderAgeException.class)
    public ResponseEntity<Map<String,String>> userUnderAgeException(UserUnderAgeException e){
        return ResponseEntity.badRequest().body(Map.of(ERROR, e.getMessage()));
    }

}
