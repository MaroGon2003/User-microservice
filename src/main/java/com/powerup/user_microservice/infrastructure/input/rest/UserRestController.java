package com.powerup.user_microservice.infrastructure.input.rest;

import com.powerup.user_microservice.application.dto.request.UserRequestDto;
import com.powerup.user_microservice.application.handler.IUserHandler;
import com.powerup.user_microservice.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(InfrastructureConstants.USER_REQUEST_MAPPING)
@RequiredArgsConstructor
@Tag(name = InfrastructureConstants.USER_TAG_NAME, description = InfrastructureConstants.USER_TAG_DESCRIPTION)
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
            summary = InfrastructureConstants.USER_OPERATION_SUMMARY,
            description = InfrastructureConstants.USER_OPERATION_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_201, description = InfrastructureConstants.USER_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_400, description = InfrastructureConstants.USER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_500, description = InfrastructureConstants.USER_RESPONSE_500_DESCRIPTION)
    })
    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(InfrastructureConstants.USER_GET_ID_BY_EMAIL_ENDPOINT)
    @Operation(
            summary = InfrastructureConstants.USER_GET_ID_BY_EMAIL_SUMMARY,
            description = InfrastructureConstants.USER_GET_ID_BY_EMAIL_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_200, description = InfrastructureConstants.USER_GET_ID_BY_EMAIL_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_400, description = InfrastructureConstants.USER_GET_ID_BY_EMAIL_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_404, description = InfrastructureConstants.USER_GET_ID_BY_EMAIL_RESPONSE_404_DESCRIPTION)
    })
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userHandler.getUserIdByEmail(email));
    }

 }

