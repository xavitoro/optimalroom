package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import org.optimalwaytechtest.application.contracts.RoomDto;
import org.springframework.stereotype.Component;

@Component
class RoomWebMapper {

    RoomResponse toResponse(RoomDto dto) {
        return new RoomResponse(dto.id(), dto.name(), dto.capacity());
    }
}
