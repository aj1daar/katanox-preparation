package practice.task07;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task07BookingStateProcessorTest {

    private final BookingStateProcessor processor = new BookingStateProcessor();

    private static final LocalDate CHECK_IN  = LocalDate.of(2024, 6, 1);
    private static final LocalDate CHECK_OUT = LocalDate.of(2024, 6, 7);
    private static final Instant   T0        = Instant.parse("2024-01-01T10:00:00Z");

    private static Instant t(int plusSeconds) { return T0.plusSeconds(plusSeconds); }

    @Test
    void createdEventProducesActiveSnapshot() {
        var event  = new BookingEvent.BookingCreated("R-1", t(0), "Alice", CHECK_IN, CHECK_OUT, "DLX");
        var result = processor.process(List.of(event));
        assertEquals(BookingStatus.ACTIVE, result.get("R-1").status());
        assertEquals("Alice", result.get("R-1").guestName());
    }

    @Test
    void cancelledAfterCreatedProducesCancelledSnapshot() {
        var created   = new BookingEvent.BookingCreated("R-1", t(0), "Alice", CHECK_IN, CHECK_OUT, "DLX");
        var cancelled = new BookingEvent.BookingCancelled("R-1", t(10));
        var result    = processor.process(List.of(created, cancelled));
        assertEquals(BookingStatus.CANCELLED, result.get("R-1").status());
    }

    @Test
    void reinstatedAfterCancelledProducesActiveSnapshot() {
        var created    = new BookingEvent.BookingCreated("R-1", t(0), "Alice", CHECK_IN, CHECK_OUT, "DLX");
        var cancelled  = new BookingEvent.BookingCancelled("R-1", t(10));
        var reinstated = new BookingEvent.BookingReinstated("R-1", t(20));
        var result     = processor.process(List.of(created, cancelled, reinstated));
        assertEquals(BookingStatus.ACTIVE, result.get("R-1").status());
    }

    @Test
    void outOfOrderEventsAreAppliedChronologically() {
        // cancelled arrives before created in the list — must still be applied in time order
        var cancelled = new BookingEvent.BookingCancelled("R-1", t(10));
        var created   = new BookingEvent.BookingCreated("R-1", t(0), "Alice", CHECK_IN, CHECK_OUT, "DLX");
        var result    = processor.process(List.of(cancelled, created));
        assertEquals(BookingStatus.CANCELLED, result.get("R-1").status());
    }

    @Test
    void modifiedEventUpdatesGuestNameAndDates() {
        var newCheckIn  = LocalDate.of(2024, 7, 1);
        var newCheckOut = LocalDate.of(2024, 7, 5);
        var created  = new BookingEvent.BookingCreated("R-1", t(0), "Alice", CHECK_IN, CHECK_OUT, "DLX");
        var modified = new BookingEvent.BookingModified("R-1", t(10), "Bob", newCheckIn, newCheckOut);
        var result   = processor.process(List.of(created, modified));
        assertEquals("Bob",        result.get("R-1").guestName());
        assertEquals(newCheckIn,   result.get("R-1").checkIn());
        assertEquals(newCheckOut,  result.get("R-1").checkOut());
        assertEquals(BookingStatus.ACTIVE, result.get("R-1").status());
    }

    @Test
    void multipleReservationsProcessedIndependently() {
        var r1Created   = new BookingEvent.BookingCreated("R-1", t(0), "Alice", CHECK_IN, CHECK_OUT, "STD");
        var r2Created   = new BookingEvent.BookingCreated("R-2", t(0), "Bob",   CHECK_IN, CHECK_OUT, "DLX");
        var r2Cancelled = new BookingEvent.BookingCancelled("R-2", t(5));
        var result      = processor.process(List.of(r1Created, r2Created, r2Cancelled));
        assertEquals(BookingStatus.ACTIVE,    result.get("R-1").status());
        assertEquals(BookingStatus.CANCELLED, result.get("R-2").status());
    }

    @Test
    void fiveMillionEventsProcessedInReasonableTime() {
        int reservations = 1_000_000;
        int eventsPerRes = 5;
        var events = new ArrayList<BookingEvent>(reservations * eventsPerRes);

        for (int i = 0; i < reservations; i++) {
            String id = "R-" + i;
            var ci = CHECK_IN.plusDays(i % 300);
            var co = ci.plusDays(3);
            events.add(new BookingEvent.BookingCreated(id, t(i * 10),     "Guest-" + i, ci, co, "STD"));
            events.add(new BookingEvent.BookingModified(id, t(i * 10 + 1), "Guest-" + i + "-M", ci, co));
            events.add(new BookingEvent.BookingCancelled(id, t(i * 10 + 2)));
            events.add(new BookingEvent.BookingReinstated(id, t(i * 10 + 3)));
            events.add(new BookingEvent.BookingNoShow(id, t(i * 10 + 4)));
        }

        var start   = System.currentTimeMillis();
        var result  = processor.process(events);
        var elapsed = System.currentTimeMillis() - start;

        assertEquals(reservations, result.size());
        assertEquals(BookingStatus.NO_SHOW, result.get("R-0").status());
        assertTrue(elapsed < 15000,
                "Expected 5M events in < 15s but took " + elapsed + "ms");
    }
}
