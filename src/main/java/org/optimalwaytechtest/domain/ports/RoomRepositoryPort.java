package org.optimalwaytechtest.domain.ports;

import org.optimalwaytechtest.domain.entities.Room;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepositoryPort {
    Room save(Room room);
    Optional<Room> findById(UUID id);
    List<Room> findAll();
}
