package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Payload to create a reservation")
public record CreateReservationRequest(
        @Schema(description = "Identifier of the room", example = "29faed8f-ad42-4e06-9560-9fe40198d114")
        @NotNull UUID roomId,
        @Schema(description = "Identifier of the user", example = "605adc79-1235-4a05-ae6a-c18e54997e1f")
        @NotNull UUID userId,
        @Schema(description = "Start time of the reservation", example = "2025-09-11T16:00:00Z")
        @NotNull Instant start,
        @Schema(description = "End time of the reservation", example = "2025-09-11T17:00:00Z")
        @NotNull Instant end
) {}

