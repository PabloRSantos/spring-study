package com.example.demo.users.usecases;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.users.domain.dtos.SignUpRequestDTO;
import com.example.demo.users.domain.mappers.SignUpRequestMapper;
import com.example.demo.users.domain.models.UserModel;
import com.example.demo.users.infra.repositories.UserRepository;

class SignUpServiceUseCaseTest {
    @InjectMocks
    SignUpUseCaseImpl signUpUseCaseImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    SignUpRequestMapper signUpRequestMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserModel userModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowIfUserAlreadyExists() {
        var signUpRequestDTO = new SignUpRequestDTO(
                "some_name",
                "some_email",
                "some_password",
                LocalDate.now());

        when(userRepository.existsByEmail(signUpRequestDTO.getEmail())).thenReturn(true);

        var exception = assertThrows(ResponseStatusException.class, () -> {
            signUpUseCaseImpl.execute(signUpRequestDTO);
        });

        verify(userRepository).existsByEmail(signUpRequestDTO.getEmail());
        assertEquals(409, exception.getBody().getStatus());
        assertEquals("User already exists", exception.getReason());
    }

    @Test
    void shouldCreateUser() {
        var hashedPassword = "some_hash";
        var signUpRequestDTO = new SignUpRequestDTO(
                "some_name",
                "some_email",
                "some_password",
                LocalDate.now());

        when(userRepository.existsByEmail(signUpRequestDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpRequestDTO.getPassword())).thenReturn(hashedPassword);
        when(signUpRequestMapper.toModel(signUpRequestDTO)).thenReturn(userModel);

        signUpUseCaseImpl.execute(signUpRequestDTO);

        verify(userRepository).existsByEmail(signUpRequestDTO.getEmail());
        verify(passwordEncoder).encode(signUpRequestDTO.getPassword());
        verify(signUpRequestMapper).toModel(signUpRequestDTO);
        verify(userModel).setPassword(hashedPassword);
        verify(userRepository).save(userModel);
    }
}
