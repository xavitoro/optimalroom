package org.optimalwaytechtest.infrastructure.persistence;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.optimalwaytechtest.domain.entities.Reservation;
import org.optimalwaytechtest.domain.enums.ReservationStatus;
import org.optimalwaytechtest.domain.ports.ReservationRepositoryPort;
import org.optimalwaytechtest.domain.valueobjects.ReservationSearchCriteria;
import org.optimalwaytechtest.domain.valueobjects.SearchResult;
import org.optimalwaytechtest.domain.valueobjects.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persistence adapter for {@link ReservationRepositoryPort}.
 */
@Component
class ReservationPersistenceAdapter implements ReservationRepositoryPort {

    private final ReservationJpaRepository repository;
    private final ReservationMapper mapper;

    ReservationPersistenceAdapter(ReservationJpaRepository repository, ReservationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = mapper.toEntity(reservation);
        ReservationEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsOverlap(UUID roomId, Instant start, Instant end) {
        return repository.existsOverlap(roomId, start, end);
    }

    @Override
    public List<TimeSlot> findUserSlotsAround(UUID userId, Instant windowStart, Instant windowEnd) {
        return repository.findUserSlotsAround(userId, windowStart, windowEnd)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public SearchResult<Reservation> search(ReservationSearchCriteria criteria) {
        Specification<ReservationEntity> spec = (root, query, cb) -> {
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();
            if (criteria.roomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), criteria.roomId()));
            }
            if (criteria.userId() != null) {
                predicates.add(cb.equal(root.get("userId"), criteria.userId()));
            }
            if (criteria.startFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startAt"), criteria.startFrom()));
            }
            if (criteria.endTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endAt"), criteria.endTo()));
            }
            if (criteria.status() != null) {
                predicates.add(cb.equal(root.get("status"), ReservationStatus.valueOf(criteria.status().name())));
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        PageRequest pageRequest = PageRequest.of(criteria.page(), criteria.size(), Sort.by("startAt").ascending());
        Page<ReservationEntity> page = repository.findAll(spec, pageRequest);
        List<Reservation> items = page.stream().map(mapper::toDomain).toList();
        return new SearchResult<>(items, page.getTotalElements());
    }

    @Override
    @Transactional
    public void markCancelled(UUID id) {
        repository.markCancelled(id);
    }
}
