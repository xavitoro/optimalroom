package org.optimalwaytechtest.application.mappers;

import org.optimalwaytechtest.application.contracts.CreateReservationCommand;
import org.optimalwaytechtest.application.contracts.ReservationDto;
import org.optimalwaytechtest.room.domain.entities.Reservation;
import org.optimalwaytechtest.room.domain.enums.ReservationStatus;
import org.optimalwaytechtest.room.domain.valueobjects.TimeSlot;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/**
 * Maps between domain reservations and DTOs.
 */
@Component
public class ReservationDtoMapper {

    public ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getRoomId(),
                reservation.getUserId(),
                reservation.getSlot().getStart(),
                reservation.getSlot().getEnd(),
                reservation.getStatus(),
                reservation.getCreatedAt()
        );
    }

    public Reservation fromCommand(CreateReservationCommand cmd) {
        TimeSlot slot = new TimeSlot(cmd.start(), cmd.end());
        return new Reservation(
                UUID.randomUUID(),
                cmd.roomId(),
                cmd.userId(),
                slot,
                ReservationStatus.ACTIVE,
                Instant.now()
        );
    }
}
