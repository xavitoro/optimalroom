package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated reservation response")
public record ReservationPageResponse(
        @Schema(description = "Reservations on the current page")
        List<ReservationResponse> items,
        @Schema(description = "Total number of reservations", example = "42")
        long total,
        @Schema(description = "Current page number", example = "0")
        int page,
        @Schema(description = "Size of the page", example = "20")
        int size
) {}

