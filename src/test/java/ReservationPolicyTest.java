import static org.assertj.core.api.Assertions.assertThat;

import org.optimalwaytechtest.domain.services.ReservationPolicy;
import org.optimalwaytechtest.domain.valueobjects.TimeSlot;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReservationPolicyTest {

    @Test
    void allowsExactlyTwoHoursContinuous() {
        TimeSlot existing = new TimeSlot(
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:00:00Z"));
        TimeSlot candidate = new TimeSlot(
                Instant.parse("2024-01-01T11:15:00Z"),
                Instant.parse("2024-01-01T12:00:00Z"));
        boolean result = ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate);
        assertThat(result).isFalse();
    }

    @Test
    void detectsViolationWhenContinuousExceedsTwoHours() {
        TimeSlot existing = new TimeSlot(
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:00:00Z"));
        TimeSlot candidate = new TimeSlot(
                Instant.parse("2024-01-01T11:15:00Z"),
                Instant.parse("2024-01-01T12:15:00Z"));
        boolean result = ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate);
        assertThat(result).isTrue();
    }

    @Test
    void doesNotMergeWhenGapEqualsThreshold() {
        TimeSlot existing = new TimeSlot(
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T11:30:00Z"));
        TimeSlot candidate = new TimeSlot(
                Instant.parse("2024-01-01T12:00:00Z"),
                Instant.parse("2024-01-01T12:30:00Z"));
        boolean result = ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate);
        assertThat(result).isFalse();
    }

    @Test
    void detectsViolationWhenSingleSlotExceedsTwoHours() {
        TimeSlot candidate = new TimeSlot(
                Instant.parse("2024-01-01T10:00:00Z"),
                Instant.parse("2024-01-01T12:01:00Z"));
        boolean result = ReservationPolicy.violatesTwoHourRule(List.of(), candidate);
        assertThat(result).isTrue();
    }
}
