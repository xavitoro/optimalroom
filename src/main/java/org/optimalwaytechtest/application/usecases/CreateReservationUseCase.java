package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.*;
import org.optimalwaytechtest.application.mappers.ReservationDtoMapper;
import org.optimalwaytechtest.domain.entities.Reservation;
import org.optimalwaytechtest.domain.exceptions.ConflictException;
import org.optimalwaytechtest.domain.exceptions.NotFoundException;
import org.optimalwaytechtest.domain.exceptions.ValidationException;
import org.optimalwaytechtest.domain.ports.NotifierPort;
import org.optimalwaytechtest.domain.ports.ReservationRepositoryPort;
import org.optimalwaytechtest.domain.ports.RoomRepositoryPort;
import org.optimalwaytechtest.domain.ports.UserRepositoryPort;
import org.optimalwaytechtest.domain.services.ReservationPolicy;
import org.optimalwaytechtest.domain.valueobjects.TimeSlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
public class CreateReservationUseCase {

    private final ReservationRepositoryPort reservationRepository;
    private final UserRepositoryPort userRepository;
    private final RoomRepositoryPort roomRepository;
    private final NotifierPort notifier;
    private final ReservationDtoMapper mapper;

    public CreateReservationUseCase(ReservationRepositoryPort reservationRepository,
                                    UserRepositoryPort userRepository,
                                    RoomRepositoryPort roomRepository,
                                    NotifierPort notifier,
                                    ReservationDtoMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @Transactional
    public ReservationDto handle(CreateReservationCommand cmd) {
        userRepository.findById(cmd.userId())
                .orElseThrow(() -> new NotFoundException("User not found: " + cmd.userId()));
        roomRepository.findById(cmd.roomId())
                .orElseThrow(() -> new NotFoundException("Room not found: " + cmd.roomId()));

        Reservation reservation = mapper.fromCommand(cmd);
        TimeSlot slot = reservation.getSlot();

        if (reservationRepository.existsOverlap(cmd.roomId(), slot.getStart(), slot.getEnd())) {
            throw new ConflictException("Reservation overlaps with existing one");
        }

        var windowStart = slot.getStart().minus(Duration.ofHours(2));
        var windowEnd = slot.getEnd().plus(Duration.ofHours(2));
        List<TimeSlot> existing = reservationRepository.findUserSlotsAround(cmd.userId(), windowStart, windowEnd);
        if (ReservationPolicy.violatesTwoHourRule(existing, slot)) {
            throw new ConflictException("Reservation violates two hour rule");
        }

        Reservation saved = reservationRepository.save(reservation);
        notifier.notifyReservationCreated(saved);
        return mapper.toDto(saved);
    }
}
