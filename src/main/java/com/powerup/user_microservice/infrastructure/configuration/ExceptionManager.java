package com.powerup.user_microservice.infrastructure.configuration;

import com.powerup.user_microservice.domain.exception.UserAlreadyExistsException;
import com.powerup.user_microservice.domain.exception.UserUnderAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException e, WebRequest request) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> userAlreadyExistsException(UserAlreadyExistsException e, WebRequest request) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUnderAgeException.class)
    public ResponseEntity<ApiResponse> userUnderAgeException(UserUnderAgeException e, WebRequest request) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {

        Map<String, String> mapErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            mapErrors.put(field, message);
        });

        ApiResponse apiResponse = new ApiResponse(mapErrors.toString(), request.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
