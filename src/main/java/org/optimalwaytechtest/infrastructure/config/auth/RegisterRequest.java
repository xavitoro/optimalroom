package org.optimalwaytechtest.infrastructure.config.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload used to register a new user")
public record RegisterRequest(
        @Schema(description = "Unique user email", example = "user@example.com")
        @NotBlank @Email String email,
        @Schema(description = "Account password", example = "P@55w0rd")
        @NotBlank @Size(min = 6) String password
) {}
