package org.optimalwaytechtest.application.contracts;

import org.optimalwaytechtest.room.domain.enums.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

public record ListReservationsQuery(
        UUID roomId,
        UUID userId,
        Instant startFrom,
        Instant endTo,
        ReservationStatus status,
        int page,
        int size
) {}
