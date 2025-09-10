package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import java.util.List;

public record ReservationPageResponse(
        List<ReservationResponse> items,
        long total,
        int page,
        int size
) {}

