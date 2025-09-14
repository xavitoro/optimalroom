package org.optimalwaytechtest.infrastructure.adapters;

import org.optimalwaytechtest.domain.entities.Reservation;
import org.optimalwaytechtest.domain.ports.NotifierPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Simple {@link NotifierPort} implementation that logs reservation events.
 */
@Repository
class LoggingNotifierAdapter implements NotifierPort {

    private static final Logger logger = LoggerFactory.getLogger(LoggingNotifierAdapter.class);

    @Override
    public void notifyReservationCreated(Reservation reservation) {
        logger.info("Reservation created: {}", reservation);
    }

    @Override
    public void notifyReservationCancelled(Reservation reservation) {
        logger.info("Reservation cancelled: {}", reservation);
    }
}