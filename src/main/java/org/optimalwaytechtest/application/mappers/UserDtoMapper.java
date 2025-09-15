package org.optimalwaytechtest.application.mappers;

import org.optimalwaytechtest.application.contracts.UserDto;
import org.optimalwaytechtest.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
