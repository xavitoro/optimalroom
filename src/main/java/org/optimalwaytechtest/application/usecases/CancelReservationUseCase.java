package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.*;
import org.optimalwaytechtest.application.mappers.ReservationDtoMapper;
import org.optimalwaytechtest.room.domain.entities.Reservation;
import org.optimalwaytechtest.room.domain.enums.ReservationStatus;
import org.optimalwaytechtest.room.domain.exceptions.NotFoundException;
import org.optimalwaytechtest.room.domain.ports.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelReservationUseCase {

    private final ReservationRepositoryPort reservationRepository;
    private final NotifierPort notifier;
    private final ReservationDtoMapper mapper;

    public CancelReservationUseCase(ReservationRepositoryPort reservationRepository,
                                    NotifierPort notifier,
                                    ReservationDtoMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @Transactional
    public ReservationDto handle(CancelReservationCommand cmd) {
        Reservation reservation = reservationRepository.findById(cmd.reservationId())
                .orElseThrow(() -> new NotFoundException("Reservation not found: " + cmd.reservationId()));
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            return mapper.toDto(reservation);
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.markCancelled(reservation.getId());
        notifier.notifyReservationCancelled(reservation);
        return mapper.toDto(reservation);
    }
}
