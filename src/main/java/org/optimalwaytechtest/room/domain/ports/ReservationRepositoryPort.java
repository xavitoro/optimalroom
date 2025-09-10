package org.optimalwaytechtest.room.domain.ports;

import org.optimalwaytechtest.room.domain.entities.Reservation;
import org.optimalwaytechtest.room.domain.valueobjects.ReservationSearchCriteria;
import org.optimalwaytechtest.room.domain.valueobjects.SearchResult;
import org.optimalwaytechtest.room.domain.valueobjects.TimeSlot;


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

