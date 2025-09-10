package org.optimalwaytechtest.room.domain;

import org.junit.jupiter.api.Test;
import org.optimalwaytechtest.room.domain.services.ReservationPolicy;
import org.optimalwaytechtest.room.domain.valueobjects.TimeSlot;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationPolicyTest {

    @Test
    void segmentExactlyTwoHoursIsAllowed() {
        TimeSlot existing = slot(9, 0, 10, 0);
        TimeSlot candidate = slot(10, 0, 11, 0);
        assertFalse(ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate));
    }

    @Test
    void segmentLongerThanTwoHoursIsRejected() {
        TimeSlot existing = slot(9, 0, 10, 0);
        TimeSlot candidate = slot(10, 0, 11, 1);
        assertTrue(ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate));
    }

    @Test
    void gapOfTwentyNineMinutesMergesAndViolates() {
        TimeSlot existing = slot(9, 0, 10, 0);
        TimeSlot candidate = slot(10, 29, 11, 59);
        assertTrue(ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate));
    }

    @Test
    void gapOfThirtyMinutesDoesNotMerge() {
        TimeSlot existing = slot(9, 0, 10, 0);
        TimeSlot candidate = slot(10, 30, 11, 59);
        assertFalse(ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate));
    }

    @Test
    void partialOverlapCreatesViolation() {
        TimeSlot existing = slot(9, 0, 11, 0);
        TimeSlot candidate = slot(10, 30, 12, 31);
        assertTrue(ReservationPolicy.violatesTwoHourRule(List.of(existing), candidate));
    }

    private static TimeSlot slot(int startHour, int startMinute, int endHour, int endMinute) {
        LocalDate day = LocalDate.of(2024, 1, 1);
        Instant start = day.atTime(LocalTime.of(startHour, startMinute)).toInstant(ZoneOffset.UTC);
        Instant end = day.atTime(LocalTime.of(endHour, endMinute)).toInstant(ZoneOffset.UTC);
        return new TimeSlot(start, end);
    }
}