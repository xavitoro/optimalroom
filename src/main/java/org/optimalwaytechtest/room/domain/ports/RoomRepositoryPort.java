package org.optimalwaytechtest.room.domain.ports;

import org.optimalwaytechtest.room.domain.entities.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepositoryPort {
    Room save(Room room);
    Optional<Room> findById(UUID id);
}
