package com.example.demo.users.domain.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDTO {
    private final UUID id;
    private final String name;
    private final String email;
}
