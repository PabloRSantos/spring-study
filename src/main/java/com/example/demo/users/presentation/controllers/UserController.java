package com.example.demo.users.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.users.domain.dtos.SignInRequestDTO;
import com.example.demo.users.domain.dtos.SignInResponseDTO;
import com.example.demo.users.domain.dtos.SignUpRequestDTO;
import com.example.demo.users.domain.usecases.SignInUseCase;
import com.example.demo.users.domain.usecases.SignUpUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Auth")
public class UserController {
    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        this.signUpUseCase.execute(signUpRequestDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.signInUseCase.execute((signInRequestDTO)));
    }
}
