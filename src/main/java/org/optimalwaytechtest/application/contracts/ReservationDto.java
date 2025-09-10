package org.optimalwaytechtest.application.contracts;

import org.optimalwaytechtest.room.domain.enums.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

public record ReservationDto(
        UUID id,
        UUID roomId,
        UUID userId,
        Instant start,
        Instant end,
        ReservationStatus status,
        Instant createdAt
) {}
