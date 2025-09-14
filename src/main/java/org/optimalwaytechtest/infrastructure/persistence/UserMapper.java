package org.optimalwaytechtest.infrastructure.persistence;

import org.optimalwaytechtest.domain.entities.User;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link User} domain objects and {@link UserEntity}.
 */
@Component
class UserMapper {

    UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getEmail(), user.getPasswordHash(), user.getCreatedAt());
    }

    User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getEmail(), entity.getPasswordHash(), entity.getCreatedAt());
    }
}
