package com.powerup.user_microservice.application.dto.request;

import com.powerup.user_microservice.application.utils.ApplicationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = ApplicationConstants.EMAIL_NOT_EMPTY)
    @Email(message = ApplicationConstants.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ApplicationConstants.PASSWORD_NOT_EMPTY)
    private String password;

}
