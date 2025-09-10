package org.optimalwaytechtest.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.optimalwaytechtest.room.domain.entities.User;
import org.optimalwaytechtest.room.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Component;

/**
 * Persistence adapter for {@link UserRepositoryPort}.
 */
@Component
class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    UserPersistenceAdapter(UserJpaRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
