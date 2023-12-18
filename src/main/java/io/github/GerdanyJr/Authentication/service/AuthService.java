package io.github.GerdanyJr.Authentication.service;

import java.net.URI;

import io.github.GerdanyJr.Authentication.model.dto.Login;
import io.github.GerdanyJr.Authentication.model.entity.User;
import io.github.GerdanyJr.Authentication.model.request.RefreshTokenRequest;
import io.github.GerdanyJr.Authentication.model.response.AuthResponse;

public interface AuthService {
    public URI register(User user);

    public AuthResponse login(Login login);

    public AuthResponse refreshToken(RefreshTokenRequest refreshToken);
}
