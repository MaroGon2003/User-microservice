package com.powerup.user_microservice.infrastructure.input.rest;

import com.powerup.user_microservice.application.dto.request.UserRequestDto;
import com.powerup.user_microservice.application.handler.IUserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Secured("ADMIN")
    @PostMapping("/save-seller")
    public ResponseEntity<String> saveUserSeller(@RequestBody @Valid UserRequestDto userRequestDto) {
        userHandler.saveUserSeller(userRequestDto);
        return ResponseEntity.ok("User Saved");
    }

}
