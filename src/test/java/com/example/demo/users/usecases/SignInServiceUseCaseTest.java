package com.example.demo.users.usecases;

import java.util.Optional;

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

import com.example.demo.users.domain.adapters.JwtAdapter;
import com.example.demo.users.domain.dtos.SignInRequestDTO;
import com.example.demo.users.domain.dtos.SignInResponseDTO;
import com.example.demo.users.domain.dtos.UserResponseDTO;
import com.example.demo.users.domain.mappers.SignInResponseMapper;
import com.example.demo.users.domain.models.UserModel;
import com.example.demo.users.infra.repositories.UserRepository;

class SignInServiceUseCaseTest {
    @InjectMocks
    private SignInUseCaseImpl signInUseCaseImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtAdapter jwt;

    @Mock
    SignInResponseMapper signInResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowIfUserNotExists() {
        var email = "some_email";
        var password = "some_password";

        var signInRequestDTO = new SignInRequestDTO(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        var exception = assertThrows(ResponseStatusException.class, () -> {
            signInUseCaseImpl.execute(signInRequestDTO);
        });

        verify(userRepository).findByEmail(email);
        assertEquals(401, exception.getBody().getStatus());
        assertEquals("Incorrect credentials", exception.getReason());
    }

    @Test
    void shouldThrowIfPasswordNotMatch() {
        var email = "some_email";
        var password = "some_password";

        var userModel = new UserModel();
        var signInRequestDTO = new SignInRequestDTO(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userModel));
        when(passwordEncoder.matches(password, userModel.getPassword())).thenReturn(false);

        var exception = assertThrows(ResponseStatusException.class, () -> {
            signInUseCaseImpl.execute(signInRequestDTO);
        });

        verify(userRepository).findByEmail(email);
        assertEquals(401, exception.getBody().getStatus());
        assertEquals("Incorrect credentials", exception.getReason());
    }

    @Test
    void shouldAuthenticateUser() {
        var email = "some_email";
        var password = "some_password";
        var jwtToken = "some_token";

        var userModel = new UserModel();
        var signInRequestDTO = new SignInRequestDTO(email, password);
        var userResponseDTO = new UserResponseDTO(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail());
        var signInResponseDTO = new SignInResponseDTO(userResponseDTO, jwtToken);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userModel));
        when(passwordEncoder.matches(password, userModel.getPassword())).thenReturn(true);
        when(jwt.generateToken(userModel)).thenReturn(jwtToken);
        when(signInResponseMapper.toDTO(userModel, jwtToken)).thenReturn(signInResponseDTO);

        var result = signInUseCaseImpl.execute(signInRequestDTO);

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(password, userModel.getPassword());
        verify(jwt).generateToken(userModel);
        verify(signInResponseMapper).toDTO(userModel, jwtToken);
        assertEquals(result, signInResponseDTO);
    }
}
