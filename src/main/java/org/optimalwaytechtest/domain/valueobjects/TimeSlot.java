package org.optimalwaytechtest.domain.valueobjects;
import org.optimalwaytechtest.domain.exceptions.ValidationException;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Value object representing a time interval.
 */
public final class TimeSlot {

    private final Instant start;
    private final Instant end;

    public TimeSlot(Instant start, Instant end) {
        if (start == null || end == null) {
            throw new ValidationException("start and end must be provided");
        }
        if (!end.isAfter(start)) {
            throw new ValidationException("end must be after start");
        }
        this.start = start;
        this.end = end;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    /**
     * Checks whether this time slot overlaps with another one.
     */
    public boolean overlaps(TimeSlot other) {
        Objects.requireNonNull(other, "other");
        return start.isBefore(other.end) && end.isAfter(other.start);
    }

    /**
     * Duration of the slot.
     */
    public Duration duration() {
        return Duration.between(start, end);
    }

    /**
     * Returns a new {@code TimeSlot} expanded by the given duration at both ends.
     * The provided duration must be non negative.
     */
    public TimeSlot expand(Duration padding) {
        Objects.requireNonNull(padding, "padding");
        if (padding.isNegative()) {
            throw new ValidationException("padding must be non negative");
        }
        return new TimeSlot(start.minus(padding), end.plus(padding));
    }

    /**
     * Calculates the gap between this slot and the next one. If the slots
     * overlap or touch each other the gap is {@link Duration#ZERO}.
     */
    public Duration gapTo(TimeSlot next) {
        Objects.requireNonNull(next, "next");
        if (!next.start.isAfter(this.end)) {
            return Duration.ZERO;
        }
        return Duration.between(this.end, next.start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot that)) return false;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
