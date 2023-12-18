package io.github.GerdanyJr.Authentication.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.GerdanyJr.Authentication.model.entity.User;
import io.github.GerdanyJr.Authentication.model.exception.BaseException;
import io.github.GerdanyJr.Authentication.model.exception.NotFoundException;
import io.github.GerdanyJr.Authentication.model.response.ErrorResponse;
import io.github.GerdanyJr.Authentication.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public JwtFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            if (token != null) {
                String subject = tokenService.getSubject(token);
                User user = userRepository.findByUsername(subject)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                var authToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            ErrorResponse error = toErrorResponse(e);
            response.setStatus(e.getCode());
            response.getWriter().write(toJson(error));
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            ErrorResponse error = new ErrorResponse(e.getMessage(),
                    HttpStatus.FORBIDDEN,
                    HttpStatus.FORBIDDEN.value(),
                    LocalDateTime.now());
            response.getWriter().write(toJson(error));
        }
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private ErrorResponse toErrorResponse(BaseException e) {
        return new ErrorResponse(e.getMessage(), e.getHttpStatus(), e.getCode(),
                LocalDateTime.now());
    }
}