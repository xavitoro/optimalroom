package org.optimalwaytechtest.infrastructure.persistence;

import org.optimalwaytechtest.domain.entities.Reservation;
import org.optimalwaytechtest.domain.enums.ReservationStatus;
import org.optimalwaytechtest.domain.valueobjects.TimeSlot;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link Reservation} domain objects and {@link ReservationEntity}.
 */
@Component
class ReservationMapper {

    ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getRoomId(),
                reservation.getUserId(),
                reservation.getSlot().getStart(),
                reservation.getSlot().getEnd(),
                ReservationStatus.valueOf(reservation.getStatus().name()),
                reservation.getCreatedAt()
        );
    }

    Reservation toDomain(ReservationEntity entity) {
        TimeSlot slot = new TimeSlot(entity.getStartAt(), entity.getEndAt());
        return new Reservation(
                entity.getId(),
                entity.getRoomId(),
                entity.getUserId(),
                slot,
                ReservationStatus.valueOf(entity.getStatus().name()),
                entity.getCreatedAt()
        );
    }

    TimeSlot toDomain(ReservationJpaRepository.SlotProjection projection) {
        return new TimeSlot(projection.getStartAt(), projection.getEndAt());
    }
}
