package com.powerup.user_microservice.infrastructure.input.rest;


import com.powerup.user_microservice.application.dto.request.LoginRequestDto;
import com.powerup.user_microservice.application.dto.response.AuthResponseDto;
import com.powerup.user_microservice.application.handler.IAuthHandler;
import com.powerup.user_microservice.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(InfrastructureConstants.AUTH_REQUEST_MAPPING)
@RequiredArgsConstructor
@Tag(name = InfrastructureConstants.AUTH_TAG_NAME, description = InfrastructureConstants.AUTH_TAG_DESCRIPTION)
public class AuthController {

    private final IAuthHandler authHandler;

    @Operation(
            summary = InfrastructureConstants.AUTH_OPERATION_SUMMARY,
            description = InfrastructureConstants.AUTH_OPERATION_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_200, description = InfrastructureConstants.AUTH_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_400, description = InfrastructureConstants.AUTH_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = InfrastructureConstants.RESPONSE_CODE_500, description = InfrastructureConstants.AUTH_RESPONSE_500_DESCRIPTION)
    })
    @GetMapping(InfrastructureConstants.AUTH_LOGIN_ENDPOINT)
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authHandler.login(loginRequestDto));
    }
}
