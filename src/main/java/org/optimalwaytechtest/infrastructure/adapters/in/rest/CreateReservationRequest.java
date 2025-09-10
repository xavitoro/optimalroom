package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CreateReservationRequest(
        @NotNull UUID roomId,
        @NotNull UUID userId,
        @NotNull Instant start,
        @NotNull Instant end
) {}

