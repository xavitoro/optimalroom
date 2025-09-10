package org.optimalwaytechtest.room.domain.ports;

import org.optimalwaytechtest.room.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
}
