package org.optimalwaytechtest.domain.ports;

import org.optimalwaytechtest.domain.entities.Reservation;
import org.optimalwaytechtest.domain.valueobjects.ReservationSearchCriteria;
import org.optimalwaytechtest.domain.valueobjects.SearchResult;
import org.optimalwaytechtest.domain.valueobjects.TimeSlot;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(UUID id);
    boolean existsOverlap(UUID roomId, Instant start, Instant end);
    List<TimeSlot> findUserSlotsAround(UUID userId, Instant windowStart, Instant windowEnd);
    SearchResult<Reservation> search(ReservationSearchCriteria criteria);
    void markCancelled(UUID id);
}

