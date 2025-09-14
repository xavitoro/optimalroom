import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.optimalwaytechtest.RoomApplication;
import org.optimalwaytechtest.infrastructure.persistence.*;
import org.optimalwaytechtest.domain.enums.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RoomApplication.class)
class ReservationJpaRepositoryTest {

    @Autowired
    private ReservationJpaRepository repository;
    @Autowired
    private RoomJpaRepository roomRepository;
    @Autowired
    private UserJpaRepository userRepository;

    @Test
    void existsOverlapDetectsActiveReservationOverlap() {
        UUID userId = UUID.randomUUID();
        userRepository.save(new UserEntity(userId, "mil@example.com", "P@55w0rd", Instant.now()));
        UUID roomId = UUID.randomUUID();
        roomRepository.save(new RoomEntity(roomId, "MIC", 4, Instant.now()));

        ReservationEntity existing = new ReservationEntity(
                UUID.randomUUID(), roomId, userId,
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:00:00Z"),
                ReservationStatus.ACTIVE, Instant.now());
        repository.save(existing);

        boolean overlap = repository.existsOverlap(roomId,
                Instant.parse("2024-01-01T10:30:00Z"),
                Instant.parse("2024-01-01T10:45:00Z"));

        assertThat(overlap).isTrue();
    }

    @Test
    void existsOverlapIgnoresCancelledReservations() {
        UUID userId = UUID.randomUUID();
        userRepository.save(new UserEntity(userId, "mail@example.com", "P@55w0rd", Instant.now()));
        UUID roomId = UUID.randomUUID();
        roomRepository.save(new RoomEntity(roomId, "MAC", 10, Instant.now()));

        ReservationEntity existing = new ReservationEntity(
                UUID.randomUUID(), roomId, userId,
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:00:00Z"),
                ReservationStatus.CANCELLED, Instant.now());
        repository.save(existing);

        boolean overlap = repository.existsOverlap(roomId,
                Instant.parse("2024-01-01T10:30:00Z"),
                Instant.parse("2024-01-01T10:45:00Z"));

        assertThat(overlap).isFalse();
    }

    @Test
    void findUserSlotsAroundReturnsSlotsWithinWindow() {
        UUID userId = UUID.randomUUID();
        userRepository.save(new UserEntity(userId, "meil@example.com", "P@55w0rd", Instant.now()));
        UUID roomId = UUID.randomUUID();
        roomRepository.save(new RoomEntity(roomId, "MEC", 300, Instant.now()));

        repository.save(new ReservationEntity(UUID.randomUUID(), roomId, userId,
                Instant.parse("2024-01-01T08:00:00Z"),
                Instant.parse("2024-01-01T09:00:00Z"),
                ReservationStatus.ACTIVE, Instant.now()));

        ReservationEntity r1 = new ReservationEntity(UUID.randomUUID(), roomId, userId,
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:00:00Z"),
                ReservationStatus.ACTIVE, Instant.now());
        repository.save(r1);

        ReservationEntity r2 = new ReservationEntity(UUID.randomUUID(), roomId, userId,
                Instant.parse("2024-01-01T11:30:00Z"),
                Instant.parse("2024-01-01T12:30:00Z"),
                ReservationStatus.ACTIVE, Instant.now());
        repository.save(r2);

        repository.save(new ReservationEntity(UUID.randomUUID(), roomId, userId,
                Instant.parse("2024-01-01T12:00:00Z"),
                Instant.parse("2024-01-01T13:00:00Z"),
                ReservationStatus.ACTIVE, Instant.now()));

        var slots = repository.findUserSlotsAround(userId,
                Instant.parse("2024-01-01T09:00:00Z"),
                Instant.parse("2024-01-01T12:00:00Z"));

        assertThat(slots).hasSize(2);
        assertThat(slots.get(0).getStartAt()).isEqualTo(r1.getStartAt());
        assertThat(slots.get(0).getEndAt()).isEqualTo(r1.getEndAt());
        assertThat(slots.get(1).getStartAt()).isEqualTo(r2.getStartAt());
        assertThat(slots.get(1).getEndAt()).isEqualTo(r2.getEndAt());
    }
}
