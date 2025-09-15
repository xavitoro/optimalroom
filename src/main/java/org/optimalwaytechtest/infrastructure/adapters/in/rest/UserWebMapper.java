package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import org.optimalwaytechtest.application.contracts.UserDto;
import org.springframework.stereotype.Component;

@Component
class UserWebMapper {

    UserResponse toResponse(UserDto dto) {
        return new UserResponse(dto.id(), dto.email(), dto.createdAt());
    }
}
