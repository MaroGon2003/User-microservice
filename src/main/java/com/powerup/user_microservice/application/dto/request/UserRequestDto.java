package com.powerup.user_microservice.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surName;

    @NotNull
    private Integer dni;

    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = "\\+?\\d+", message = "The phone number should max 13 digits and only numbers and also can start with +")
    private String phone;

    @NotNull
    private LocalDate birdDate;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
