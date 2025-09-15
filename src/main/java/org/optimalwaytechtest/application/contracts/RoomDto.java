package org.optimalwaytechtest.application.contracts;

import java.util.UUID;

public record RoomDto(
        UUID id,
        String name,
        Integer capacity
) {}
