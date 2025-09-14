package org.optimalwaytechtest.domain.ports;

import org.optimalwaytechtest.domain.entities.Reservation;

public interface NotifierPort {
    void notifyReservationCreated(Reservation reservation);
    void notifyReservationCancelled(Reservation reservation);
}
