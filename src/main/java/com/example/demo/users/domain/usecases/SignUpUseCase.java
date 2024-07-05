package com.example.demo.users.domain.usecases;

import com.example.demo.users.domain.dtos.SignUpRequestDTO;

public interface SignUpUseCase {
    public void execute(SignUpRequestDTO signUpRequestDTO);
}
