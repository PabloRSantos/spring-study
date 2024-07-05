package com.example.demo.users.infra.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.users.domain.adapters.JwtAdapter;
import com.example.demo.users.domain.models.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAdapterImpl implements JwtAdapter {
    @Value("${jwt.private.key}")
    private String secret;

    @Override
    public String generateToken(UserModel userModel) {
        var expiration = LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("spring-api")
                .withSubject(userModel.getEmail())
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    @Override
    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("spring-api")
                .build()
                .verify(token)
                .getSubject();

    }
}
