package com.example.demo.users.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
public class SignInRequestDTO {
        @NotBlank
        @Email
        private final String email;

        @NotBlank
        private final String password;
}
