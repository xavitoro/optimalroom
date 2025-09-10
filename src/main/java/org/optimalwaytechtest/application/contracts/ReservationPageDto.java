package org.optimalwaytechtest.application.contracts;

import java.util.List;

public record ReservationPageDto(
        List<ReservationDto> items,
        long total
) {}
