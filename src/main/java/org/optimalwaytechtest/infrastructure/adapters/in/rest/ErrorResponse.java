package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Standard error response")
public record ErrorResponse(
        @Schema(description = "HTTP status code", example = "400")
        int code,
        @Schema(description = "Error message", example = "Invalid request")
        String message,
        @Schema(description = "Validation or processing details")
        List<String> details
) {}

