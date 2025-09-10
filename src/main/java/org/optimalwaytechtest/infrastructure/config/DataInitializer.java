package org.optimalwaytechtest.infrastructure.config;

import java.time.Instant;
import java.util.UUID;

import org.optimalwaytechtest.infrastructure.persistence.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoomJpaRepository roomRepository;

    public DataInitializer(RoomJpaRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {
        if (roomRepository.count() == 0) {
            RoomEntity room = new RoomEntity(UUID.randomUUID(), "Initial Room", 10, Instant.now());
            roomRepository.save(room);
        }
    }
}
