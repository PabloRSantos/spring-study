package com.example.demo.users.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInResponseDTO {
        private final UserResponseDTO user;
        private final String payload;
}
