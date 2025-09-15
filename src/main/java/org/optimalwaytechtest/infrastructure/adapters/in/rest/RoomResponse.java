package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Room information")
public record RoomResponse(
        @Schema(description = "Room identifier", example = "a3f3a4f1-98fb-4a1c-9b9d-8120e47514c0")
        UUID id,
        @Schema(description = "Room name", example = "Main Conference Room")
        String name,
        @Schema(description = "Maximum capacity", example = "12")
        Integer capacity
) {}
