package org.optimalwaytechtest.application.contracts;

import java.time.Instant;
import java.util.UUID;

public record UserDto(
        UUID id,
        String email,
        Instant createdAt
) {}
