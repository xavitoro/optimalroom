package org.optimalwaytechtest.infrastructure.persistence;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data repository for {@link ReservationEntity}.
 */
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, UUID>,
        JpaSpecificationExecutor<ReservationEntity> {

    @Query("select (count(r) > 0) from ReservationEntity r where r.roomId = :roomId and r.status = 'ACTIVE' and r.startAt < :end and r.endAt > :start")
    boolean existsOverlap(@Param("roomId") UUID roomId, @Param("start") Instant start, @Param("end") Instant end);

    @Query("select r.startAt as startAt, r.endAt as endAt from ReservationEntity r where r.userId = :userId and r.startAt < :windowEnd and r.endAt > :windowStart order by r.startAt")
    List<SlotProjection> findUserSlotsAround(@Param("userId") UUID userId,
                                             @Param("windowStart") Instant windowStart,
                                             @Param("windowEnd") Instant windowEnd);

    interface SlotProjection {
        Instant getStartAt();
        Instant getEndAt();
    }

    @Modifying
    @Query("update ReservationEntity r set r.status = 'CANCELLED' where r.id = :id")
    void markCancelled(@Param("id") UUID id);
}
