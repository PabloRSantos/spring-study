package com.example.demo.users.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
    ADMIN("admin"),
    USER("user");

    private final String role;
}
