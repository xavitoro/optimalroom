package org.optimalwaytechtest.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.optimalwaytechtest.domain.entities.Room;
import org.optimalwaytechtest.domain.ports.RoomRepositoryPort;
import org.springframework.stereotype.Component;

/**
 * Persistence adapter for {@link RoomRepositoryPort}.
 */
@Component
class RoomPersistenceAdapter implements RoomRepositoryPort {

    private final RoomJpaRepository repository;
    private final RoomMapper mapper;

    RoomPersistenceAdapter(RoomJpaRepository repository, RoomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Room save(Room room) {
        RoomEntity entity = mapper.toEntity(room);
        RoomEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Room> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
