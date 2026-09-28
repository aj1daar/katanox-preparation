package practice.task04;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task04ReservationWindowTest {

    private final ReservationWindow window = new ReservationWindow();

    private static final Instant NOW = Instant.parse("2024-06-01T12:00:00Z");

    private static ReservationEvent event(String id, Instant createdAt) {
        return new ReservationEvent(id, "hotel-1", createdAt);
    }

    @Test
    void emptyListReturnsZero() {
        assertEquals(0L, window.countInLastMinutes(List.of(), 10, NOW));
    }

    @Test
    void eventExactlyAtNowIsIncluded() {
        var e = event("r-1", NOW);
        assertEquals(1L, window.countInLastMinutes(List.of(e), 10, NOW));
    }

    @Test
    void eventExactlyAtWindowStartIsIncluded() {
        var cutoff = NOW.minus(10, ChronoUnit.MINUTES);
        var e = event("r-1", cutoff);
        assertEquals(1L, window.countInLastMinutes(List.of(e), 10, NOW));
    }

    @Test
    void eventOneSecondBeforeWindowIsExcluded() {
        var justOutside = NOW.minus(10, ChronoUnit.MINUTES).minusSeconds(1);
        var e = event("r-1", justOutside);
        assertEquals(0L, window.countInLastMinutes(List.of(e), 10, NOW));
    }

    @Test
    void eventAfterNowIsExcluded() {
        var future = NOW.plusSeconds(1);
        var e = event("r-1", future);
        assertEquals(0L, window.countInLastMinutes(List.of(e), 10, NOW));
    }

    @Test
    void onlyEventsInsideWindowAreCounted() {
        var inside1 = event("r-1", NOW.minus(5, ChronoUnit.MINUTES));
        var inside2 = event("r-2", NOW.minus(9, ChronoUnit.MINUTES));
        var outside = event("r-3", NOW.minus(11, ChronoUnit.MINUTES));
        assertEquals(2L, window.countInLastMinutes(List.of(inside1, inside2, outside), 10, NOW));
    }

    @Test
    void oneMillionEventsProcessedInReasonableTime() {
        int total  = 1_000_000;
        var events = new ArrayList<ReservationEvent>(total);
        for (int i = 0; i < total; i++) {
            // half inside the 60-minute window, half outside
            long offsetMinutes = (i % 2 == 0) ? -(i % 59) : -(60 + i % 300);
            events.add(event("r-" + i, NOW.plus(offsetMinutes, ChronoUnit.MINUTES)));
        }

        var start   = System.currentTimeMillis();
        var count   = window.countInLastMinutes(events, 60, NOW);
        var elapsed = System.currentTimeMillis() - start;

        assertTrue(count > 0);
        assertTrue(elapsed < 2000,
                "Expected 1M events in < 2s but took " + elapsed + "ms");
    }
}
