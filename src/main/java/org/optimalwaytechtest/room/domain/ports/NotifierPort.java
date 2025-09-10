package org.optimalwaytechtest.room.domain.ports;

import org.optimalwaytechtest.room.domain.entities.Reservation;

public interface NotifierPort {
    void notifyReservationCreated(Reservation reservation);
    void notifyReservationCancelled(Reservation reservation);
}
