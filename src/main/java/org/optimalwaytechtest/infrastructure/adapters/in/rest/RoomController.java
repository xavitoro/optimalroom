package org.optimalwaytechtest.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.optimalwaytechtest.application.contracts.RoomDto;
import org.optimalwaytechtest.application.usecases.ListRoomsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@Tag(name = "Rooms", description = "Manage rooms")
public class RoomController {

    private final ListRoomsUseCase listRooms;
    private final RoomWebMapper mapper;

    public RoomController(ListRoomsUseCase listRooms, RoomWebMapper mapper) {
        this.listRooms = listRooms;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List rooms", description = "Returns all available rooms")
    public List<RoomResponse> list() {
        List<RoomDto> rooms = listRooms.handle();
        return rooms.stream().map(mapper::toResponse).toList();
    }
}
