package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import java.util.List;

public record ErrorResponse(
        int code,
        String message,
        List<String> details
) {}

