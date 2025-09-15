package org.optimalwaytechtest.application.usecases;

import org.optimalwaytechtest.application.contracts.RoomDto;
import org.optimalwaytechtest.application.mappers.RoomDtoMapper;
import org.optimalwaytechtest.domain.ports.RoomRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRoomsUseCase {

    private final RoomRepositoryPort roomRepository;
    private final RoomDtoMapper mapper;

    public ListRoomsUseCase(RoomRepositoryPort roomRepository, RoomDtoMapper mapper) {
        this.roomRepository = roomRepository;
        this.mapper = mapper;
    }

    public List<RoomDto> handle() {
        return roomRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
