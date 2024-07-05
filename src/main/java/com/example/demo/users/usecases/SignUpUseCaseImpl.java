package com.example.demo.users.usecases;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.users.domain.dtos.SignUpRequestDTO;
import com.example.demo.users.domain.enums.UserRoleEnum;
import com.example.demo.users.domain.mappers.SignUpRequestMapper;
import com.example.demo.users.domain.usecases.SignUpUseCase;
import com.example.demo.users.infra.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpUseCaseImpl implements SignUpUseCase {
    private final UserRepository userRepository;
    private final SignUpRequestMapper signUpRequestMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void execute(SignUpRequestDTO signUpRequestDTO) {
        var alreadyExists = this.userRepository.existsByEmail(signUpRequestDTO.getEmail());

        if (alreadyExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        var hashedPassword = this.passwordEncoder.encode(signUpRequestDTO.getPassword());

        var userModel = this.signUpRequestMapper.toModel(signUpRequestDTO);
        userModel.setPassword(hashedPassword);
        userModel.setRole(UserRoleEnum.USER);

        this.userRepository.save(userModel);
    }
}
