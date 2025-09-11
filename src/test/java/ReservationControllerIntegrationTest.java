import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.optimalwaytechtest.infrastructure.adapters.in.rest.CreateReservationRequest;
import org.optimalwaytechtest.infrastructure.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ReservationControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private RoomJpaRepository roomRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UUID userId;
    private UUID roomId;
    private final String email = "user@example.com";
    private final String rawPassword = "secret";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roomRepository.deleteAll();
        userId = UUID.randomUUID();
        roomId = UUID.randomUUID();
        UserEntity user = new UserEntity(userId, email, passwordEncoder.encode(rawPassword), Instant.now());
        userRepository.save(user);
        RoomEntity room = new RoomEntity(roomId, "Room A", 10, Instant.now());
        roomRepository.save(room);
    }

    @Test
    void createReservationAndCheckConflicts() {
        CreateReservationRequest first = new CreateReservationRequest(
                roomId,
                userId,
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:00:00Z"));

        webTestClient.post().uri("/reservations")
                .headers(h -> h.setBasicAuth(email, rawPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(first)
                .exchange()
                .expectStatus().isCreated();

        CreateReservationRequest overlapping = new CreateReservationRequest(
                roomId,
                userId,
                Instant.parse("2024-01-01T10:30:00Z"),
                Instant.parse("2024-01-01T11:30:00Z"));

        webTestClient.post().uri("/reservations")
                .headers(h -> h.setBasicAuth(email, rawPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(overlapping)
                .exchange()
                .expectStatus().isEqualTo(409);

        CreateReservationRequest twoHourRule = new CreateReservationRequest(
                roomId,
                userId,
                Instant.parse("2024-01-01T11:15:00Z"),
                Instant.parse("2024-01-01T12:15:00Z"));

        webTestClient.post().uri("/reservations")
                .headers(h -> h.setBasicAuth(email, rawPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(twoHourRule)
                .exchange()
                .expectStatus().isEqualTo(409);
    }
}
