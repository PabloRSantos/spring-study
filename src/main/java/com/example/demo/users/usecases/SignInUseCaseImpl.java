package com.example.demo.users.usecases;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.users.domain.adapters.JwtAdapter;
import com.example.demo.users.domain.dtos.SignInRequestDTO;
import com.example.demo.users.domain.dtos.SignInResponseDTO;
import com.example.demo.users.domain.mappers.SignInResponseMapper;
import com.example.demo.users.domain.usecases.SignInUseCase;
import com.example.demo.users.infra.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignInUseCaseImpl implements SignInUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAdapter jwt;
    private final SignInResponseMapper signInResponseMapper;

    @Override
    public SignInResponseDTO execute(SignInRequestDTO signInRequestDTO) {
        var userModel = this.userRepository.findByEmail(signInRequestDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials"));

        var validPassword = this.passwordEncoder.matches(signInRequestDTO.getPassword(), userModel.getPassword());
        if (!validPassword) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials");
        }

        var token = this.jwt.generateToken(userModel);
        return this.signInResponseMapper.toDTO(userModel, token);
    }
}
