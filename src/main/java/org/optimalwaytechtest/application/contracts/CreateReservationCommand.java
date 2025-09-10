package org.optimalwaytechtest.application.contracts;

import java.time.Instant;
import java.util.UUID;

public record CreateReservationCommand(
        UUID roomId,
        UUID userId,
        Instant start,
        Instant end
) {}
