package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import org.optimalwaytechtest.room.domain.enums.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Reservation information")
public record ReservationResponse(
        @Schema(description = "Reservation identifier", example = "ee13997a-40ce-4c92-b421-fd8a33bb319c")
        UUID id,
        @Schema(description = "Room identifier", example = "fdbc01fb-4459-4c9d-83a6-6f917524e8a3")
        UUID roomId,
        @Schema(description = "User identifier", example = "42cea974-8f2a-4a83-a51f-519de3517d43")
        UUID userId,
        @Schema(description = "Start time", example = "2025-09-11T16:00:00Z")
        Instant start,
        @Schema(description = "End time", example = "2025-09-11T17:00:00Z")
        Instant end,
        @Schema(description = "Current status", example = "CONFIRMED")
        ReservationStatus status,
        @Schema(description = "Creation timestamp", example = "2025-09-11T16:29:00Z")
        Instant createdAt
) {}

