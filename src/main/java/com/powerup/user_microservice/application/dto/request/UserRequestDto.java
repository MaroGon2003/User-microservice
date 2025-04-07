package com.powerup.user_microservice.application.dto.request;

import com.powerup.user_microservice.application.utils.ApplicationConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = ApplicationConstants.NAME_NOT_EMPTY)
    private String name;

    @NotBlank(message = ApplicationConstants.SURNAME_NOT_EMPTY)
    private String surName;

    @NotNull(message = ApplicationConstants.DNI_NOT_NULL)
    private Integer dni;

    @NotBlank(message = ApplicationConstants.PHONE_NOT_EMPTY)
    @Size(max = ApplicationConstants.PHONE_MAX_SIZE)
    @Pattern(regexp = ApplicationConstants.PHONE_REGEX, message = ApplicationConstants.PHONE_INVALID)
    private String phone;

    @NotNull(message = ApplicationConstants.BIRTH_DATE_NOT_NULL)
    private LocalDate birdDate;

    @Email(message = ApplicationConstants.EMAIL_INVALID)
    @NotBlank(message = ApplicationConstants.EMAIL_NOT_EMPTY)
    private String email;

    @NotBlank(message = ApplicationConstants.PASSWORD_NOT_EMPTY)
    private String password;
}
