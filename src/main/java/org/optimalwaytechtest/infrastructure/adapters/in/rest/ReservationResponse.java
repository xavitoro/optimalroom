package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import org.optimalwaytechtest.room.domain.enums.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID roomId,
        UUID userId,
        Instant start,
        Instant end,
        ReservationStatus status,
        Instant createdAt
) {}

