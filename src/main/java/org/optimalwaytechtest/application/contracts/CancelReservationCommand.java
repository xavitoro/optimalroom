package org.optimalwaytechtest.application.contracts;

import java.util.UUID;

public record CancelReservationCommand(UUID reservationId) {}
