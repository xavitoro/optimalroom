package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "User information")
public record UserResponse(
        @Schema(description = "User identifier", example = "7a6b2e17-0c9b-4bdf-9b16-2fcf6de40a21")
        UUID id,
        @Schema(description = "Email address", example = "user@example.com")
        String email,
        @Schema(description = "Creation timestamp", example = "2024-01-01T08:00:00Z")
        Instant createdAt
) {}
