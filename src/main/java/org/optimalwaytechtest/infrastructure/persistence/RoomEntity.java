package org.optimalwaytechtest.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA entity for rooms table.
 */
@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer capacity;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected RoomEntity() {
    }

    public RoomEntity(UUID id, String name, Integer capacity, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
