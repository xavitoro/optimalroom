package org.optimalwaytechtest.room.domain.entities;

import java.util.Objects;
import java.util.UUID;

public class Room {
    private final UUID id;
    private final String name;
    private final Integer capacity;

    public Room(UUID id, String name, Integer capacity) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.capacity = capacity; // capacity can be null
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
