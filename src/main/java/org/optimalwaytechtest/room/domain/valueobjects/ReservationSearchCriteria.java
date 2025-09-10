package org.optimalwaytechtest.room.domain.valueobjects;

import org.optimalwaytechtest.room.domain.enums.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

public record ReservationSearchCriteria (
    UUID roomId,
    UUID userId,
    Instant startFrom,
    Instant endTo,
    ReservationStatus status,
    int page,
    int size
) {}
