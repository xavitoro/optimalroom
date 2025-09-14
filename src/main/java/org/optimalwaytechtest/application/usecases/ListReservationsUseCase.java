package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.*;
import org.optimalwaytechtest.application.mappers.ReservationDtoMapper;
import org.optimalwaytechtest.domain.entities.Reservation;
import org.optimalwaytechtest.domain.ports.ReservationRepositoryPort;
import org.optimalwaytechtest.domain.valueobjects.ReservationSearchCriteria;
import org.optimalwaytechtest.domain.valueobjects.SearchResult;
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
