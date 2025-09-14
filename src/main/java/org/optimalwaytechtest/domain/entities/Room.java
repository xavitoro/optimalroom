package org.optimalwaytechtest.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Room {
    @Id
    private final UUID id;
    private final String name;
    private final Integer capacity;

    public Room() {
        this.id = UUID.randomUUID();
        this.name = null;
        this.capacity = null;
    }

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
