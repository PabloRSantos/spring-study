package com.example.demo.users.domain.adapters;

import com.example.demo.users.domain.models.UserModel;

public interface JwtAdapter {
    public String generateToken(UserModel userModel);

    public String validateToken(String token);
}
