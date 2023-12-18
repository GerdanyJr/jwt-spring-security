package io.github.GerdanyJr.Authentication.service.impl;

import java.net.URI;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.GerdanyJr.Authentication.config.TokenService;
import io.github.GerdanyJr.Authentication.model.dto.Login;
import io.github.GerdanyJr.Authentication.model.dto.Token;
import io.github.GerdanyJr.Authentication.model.entity.User;
import io.github.GerdanyJr.Authentication.model.exception.ConflictException;
import io.github.GerdanyJr.Authentication.model.exception.NotFoundException;
import io.github.GerdanyJr.Authentication.model.request.RefreshTokenRequest;
import io.github.GerdanyJr.Authentication.model.response.AuthResponse;
import io.github.GerdanyJr.Authentication.repository.UserRepository;
import io.github.GerdanyJr.Authentication.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

        private final AuthenticationManager authenticationManager;
        private final TokenService tokenService;
        private final UserRepository userRepository;
        private final BCryptPasswordEncoder encoder;

        public AuthServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService,
                        UserRepository userRepository, BCryptPasswordEncoder encoder) {
                this.authenticationManager = authenticationManager;
                this.tokenService = tokenService;
                this.userRepository = userRepository;
                this.encoder = encoder;
        }

        public URI register(User user) {
                if (userRepository.existsByUsername(user.getUsername())) {
                        throw new ConflictException("Usuário já criado com esse username");
                }
                user.setPassword(encoder.encode(user.getPassword()));
                User createdUser = userRepository.save(user);
                return ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("{userId}")
                                .buildAndExpand(createdUser.getId())
                                .toUri();
        }

        public AuthResponse login(Login login) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                login.username(),
                                login.password());
                Authentication authenticate = authenticationManager.authenticate(authToken);
                User user = (User) authenticate.getPrincipal();
                Token acessToken = tokenService.generateAcessToken(user);
                Token refreshToken = tokenService.generateRefreshToken(user);
                return new AuthResponse(acessToken.token(), refreshToken.token(), acessToken.expiresIn());
        }

        public AuthResponse refreshToken(RefreshTokenRequest refreshToken) {
                String subject = tokenService.getRefreshTokenSubject(refreshToken.refreshToken());
                User user = userRepository.findByUsername(subject)
                                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                Token acessToken = tokenService.generateAcessToken(user);
                Token newRefreshToken = tokenService.generateRefreshToken(user);
                return new AuthResponse(acessToken.token(),
                                newRefreshToken.token(),
                                acessToken.expiresIn());
        }

}
