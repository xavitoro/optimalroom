package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.ReservationDto;
import org.optimalwaytechtest.application.mappers.ReservationDtoMapper;
import org.optimalwaytechtest.domain.ports.ReservationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllReservationsUseCase {

    private final ReservationRepositoryPort reservationRepository;
    private final ReservationDtoMapper mapper;

    public ListAllReservationsUseCase(ReservationRepositoryPort reservationRepository,
                                      ReservationDtoMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    public List<ReservationDto> handle() {
        return reservationRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
