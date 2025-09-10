package org.optimalwaytechtest.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for {@link RoomEntity}.
 */
public interface RoomJpaRepository extends JpaRepository<RoomEntity, UUID> {
}
