package io.github.GerdanyJr.Authentication.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
                @JsonProperty("acess_token") String acessToken,
                @JsonProperty("refresh_token") String refreshToken,
                @JsonProperty("expires_in") Long expiresIn) {

}