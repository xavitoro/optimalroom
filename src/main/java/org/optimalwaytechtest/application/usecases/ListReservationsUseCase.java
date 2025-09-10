package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.*;
import org.optimalwaytechtest.application.mappers.ReservationDtoMapper;
import org.optimalwaytechtest.room.domain.entities.Reservation;
import org.optimalwaytechtest.room.domain.ports.ReservationRepositoryPort;
import org.optimalwaytechtest.room.domain.valueobjects.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListReservationsUseCase {

    private final ReservationRepositoryPort reservationRepository;
    private final ReservationDtoMapper mapper;

    public ListReservationsUseCase(ReservationRepositoryPort reservationRepository,
                                   ReservationDtoMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    public ReservationPageDto handle(ListReservationsQuery query) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria(
                query.roomId(),
                query.userId(),
                query.startFrom(),
                query.endTo(),
                query.status(),
                query.page(),
                query.size()
        );
        SearchResult<Reservation> result = reservationRepository.search(criteria);
        List<ReservationDto> items = result.items().stream().map(mapper::toDto).toList();
        return new ReservationPageDto(items, result.total());
    }
}
