package io.github.GerdanyJr.Authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.GerdanyJr.Authentication.model.dto.Login;
import io.github.GerdanyJr.Authentication.model.entity.User;
import io.github.GerdanyJr.Authentication.model.request.RefreshTokenRequest;
import io.github.GerdanyJr.Authentication.model.response.AuthResponse;
import io.github.GerdanyJr.Authentication.service.impl.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) {
        return ResponseEntity.created(authService.register(user)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Login login) {
        return ResponseEntity.ok(authService.login(login));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

}
