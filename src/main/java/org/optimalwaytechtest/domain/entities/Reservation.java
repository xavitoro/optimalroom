package org.optimalwaytechtest.domain.entities;

import org.optimalwaytechtest.domain.enums.ReservationStatus;
import org.optimalwaytechtest.domain.valueobjects.TimeSlot;

import java.util.Objects;
import java.util.UUID;
import java.time.Instant;

public class Reservation {
    private final UUID id;
    private final UUID roomId;
    private final UUID userId;
    private final TimeSlot slot;
    private ReservationStatus status;
    private final Instant createdAt;

    public Reservation(UUID id, UUID roomId, UUID userId, TimeSlot slot, ReservationStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id");
        this.roomId = Objects.requireNonNull(roomId, "roomId");
        this.userId = Objects.requireNonNull(userId, "userId");
        this.slot = Objects.requireNonNull(slot, "slot");
        this.status = Objects.requireNonNull(status, "status");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
    }

    public UUID getId() {
        return id;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public UUID getUserId() {
        return userId;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = Objects.requireNonNull(status, "status");
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
