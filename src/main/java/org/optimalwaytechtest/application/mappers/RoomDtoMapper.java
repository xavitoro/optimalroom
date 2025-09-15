package org.optimalwaytechtest.application.mappers;

import org.optimalwaytechtest.application.contracts.RoomDto;
import org.optimalwaytechtest.domain.entities.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomDtoMapper {

    public RoomDto toDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getCapacity());
    }
}
