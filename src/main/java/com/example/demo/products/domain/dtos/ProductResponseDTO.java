package com.example.demo.products.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.users.domain.dtos.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductResponseDTO {
        private final UUID id;
        private final String name;
        private final BigDecimal value;
        private final UserResponseDTO author;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
}
