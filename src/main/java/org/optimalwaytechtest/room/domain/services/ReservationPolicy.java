package org.optimalwaytechtest.room.domain.services;

import org.optimalwaytechtest.room.domain.valueobjects.TimeSlot;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing reservation related validations.
 */
public final class ReservationPolicy {

    private static final Duration MAX_CONTINUOUS_DURATION = Duration.ofHours(2);
    private static final Duration MERGE_GAP_THRESHOLD = Duration.ofMinutes(30);

    private ReservationPolicy() {
    }

    /**
     * Checks whether the candidate time slot violates the two hour rule.
     * @param existing existing reservations for the same user
     * @param candidate the slot being requested
     * @return {@code true} if the combined continuous segment containing the candidate
     * exceeds two hours
     */
    public static boolean violatesTwoHourRule(List<TimeSlot> existing, TimeSlot candidate) {
        Objects.requireNonNull(existing, "existing");
        Objects.requireNonNull(candidate, "candidate");

        List<TimeSlot> all = new ArrayList<>(existing);
        all.add(candidate);
        all.sort(Comparator.comparing(TimeSlot::getStart));

        List<TimeSlot> merged = new ArrayList<>();
        for (TimeSlot slot : all) {
            if (merged.isEmpty()) {
                merged.add(slot);
            } else {
                TimeSlot last = merged.get(merged.size() - 1);
                Duration gap = last.gapTo(slot);
                if (gap.compareTo(MERGE_GAP_THRESHOLD) < 0) {
                    Instant newEnd = last.getEnd().isAfter(slot.getEnd()) ? last.getEnd() : slot.getEnd();
                    merged.set(merged.size() - 1, new TimeSlot(last.getStart(), newEnd));
                } else {
                    merged.add(slot);
                }
            }
        }

        for (TimeSlot segment : merged) {
            if (segment.overlaps(candidate)) {
                return segment.duration().compareTo(MAX_CONTINUOUS_DURATION) > 0;
            }
        }
        return false;
    }
}