package org.optimalwaytechtest.infrastructure.persistence;

import org.optimalwaytechtest.room.domain.entities.Room;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link Room} domain objects and {@link RoomEntity}.
 */
@Component
class RoomMapper {

    RoomEntity toEntity(Room room) {
        return new RoomEntity(room.getId(), room.getName(), room.getCapacity(), java.time.Instant.now());
    }

    Room toDomain(RoomEntity entity) {
        return new Room(entity.getId(), entity.getName(), entity.getCapacity());
    }
}
