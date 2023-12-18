package io.github.GerdanyJr.Authentication.model.dto;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank(message = "Username não pode ser vazio") String username,
        @NotBlank(message = "Password não pode ser vazio") String password) {
}
