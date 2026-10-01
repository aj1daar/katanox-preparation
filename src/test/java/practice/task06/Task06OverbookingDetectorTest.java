package practice.task06;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task06OverbookingDetectorTest {

    private final OverbookingDetector detector = new OverbookingDetector();

    private static LocalDate d(String iso) { return LocalDate.parse(iso); }

    @Test
    void noBookingsProducesNoAlerts() {
        var result = detector.detect(List.of(), List.of(new RoomInventory("STD", 5)));
        assertTrue(result.isEmpty());
    }

    @Test
    void bookingWithinCapacityProducesNoAlert() {
        var b = new Booking("B-1", "STD", d("2024-01-01"), d("2024-01-03"));
        var result = detector.detect(List.of(b), List.of(new RoomInventory("STD", 2)));
        assertTrue(result.isEmpty());
    }

    @Test
    void twoBookingsExceedingCapacityOfOneTriggersAlertPerNight() {
        var b1 = new Booking("B-1", "STD", d("2024-01-01"), d("2024-01-03"));
        var b2 = new Booking("B-2", "STD", d("2024-01-01"), d("2024-01-03"));
        var result = detector.detect(List.of(b1, b2), List.of(new RoomInventory("STD", 1)));
        assertEquals(2, result.size());
        assertEquals(d("2024-01-01"), result.get(0).date());
        assertEquals(d("2024-01-02"), result.get(1).date());
        assertEquals(2, result.get(0).booked());
        assertEquals(1, result.get(0).capacity());
    }

    @Test
    void checkOutDateIsNotAStayNight() {
        // B1 checks out Jan 3; B2 checks in Jan 3 — they should not clash on Jan 3
        var b1 = new Booking("B-1", "STD", d("2024-01-01"), d("2024-01-03"));
        var b2 = new Booking("B-2", "STD", d("2024-01-03"), d("2024-01-05"));
        var result = detector.detect(List.of(b1, b2), List.of(new RoomInventory("STD", 1)));
        assertTrue(result.isEmpty());
    }

    @Test
    void roomTypeAbsentFromInventoryHasZeroCapacity() {
        var b = new Booking("B-1", "PENTHOUSE", d("2024-06-01"), d("2024-06-02"));
        var result = detector.detect(List.of(b), List.of(new RoomInventory("STD", 10)));
        assertEquals(1, result.size());
        assertEquals("PENTHOUSE", result.get(0).roomTypeId());
        assertEquals(0, result.get(0).capacity());
    }

    @Test
    void twoMillionBookingsProcessedInReasonableTime() {
        int total = 2_000_000;
        var bookings = new ArrayList<Booking>(total);
        var inventory = List.of(new RoomInventory("STD", 500), new RoomInventory("DLX", 200));
        var base = d("2024-01-01");
        String[] types = {"STD", "DLX"};

        for (int i = 0; i < total; i++) {
            var checkIn  = base.plusDays(i % 365);
            var checkOut = checkIn.plusDays(1 + i % 4);
            bookings.add(new Booking("B-" + i, types[i % 2], checkIn, checkOut));
        }

        var start   = System.currentTimeMillis();
        var result  = detector.detect(bookings, inventory);
        var elapsed = System.currentTimeMillis() - start;

        assertNotNull(result);
        assertTrue(elapsed < 10000,
                "Expected 2M bookings in < 10s but took " + elapsed + "ms");
    }
}
