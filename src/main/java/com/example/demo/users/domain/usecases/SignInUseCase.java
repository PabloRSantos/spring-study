package com.example.demo.users.domain.usecases;

import com.example.demo.users.domain.dtos.SignInRequestDTO;
import com.example.demo.users.domain.dtos.SignInResponseDTO;

public interface SignInUseCase {
    public SignInResponseDTO execute(SignInRequestDTO signInRequestDTO);
}
