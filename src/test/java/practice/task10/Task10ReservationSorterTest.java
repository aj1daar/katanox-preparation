package practice.task10;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task10ReservationSorterTest {

    private final ReservationSorter sorter = new ReservationSorter();

    @Test
    void emptyListReturnsEmptyList() {
        assertTrue(sorter.sort(List.of()).isEmpty());
    }

    @Test
    void earlierCheckInComesFirst() {
        var later   = new Reservation("R-2", "H-1", "Alice", LocalDate.of(2024, 6, 10));
        var earlier = new Reservation("R-1", "H-1", "Alice", LocalDate.of(2024, 6, 1));
        var result  = sorter.sort(List.of(later, earlier));
        assertEquals("R-1", result.get(0).reservationId());
    }

    @Test
    void sameCheckInSortedByHotelIdAscending() {
        var date = LocalDate.of(2024, 6, 1);
        var b = new Reservation("R-2", "Hotel-B", "Alice", date);
        var a = new Reservation("R-1", "Hotel-A", "Alice", date);
        var result = sorter.sort(List.of(b, a));
        assertEquals("Hotel-A", result.get(0).hotelId());
    }

    @Test
    void sameCheckInSameHotelSortedByGuestNameAscending() {
        var date = LocalDate.of(2024, 6, 1);
        var z = new Reservation("R-2", "H-1", "Zara",  date);
        var a = new Reservation("R-1", "H-1", "Alice", date);
        var result = sorter.sort(List.of(z, a));
        assertEquals("Alice", result.get(0).guestName());
    }

    @Test
    void inputListIsNotMutated() {
        var date = LocalDate.of(2024, 6, 1);
        var original = new ArrayList<>(List.of(
                new Reservation("R-2", "H-1", "Zara",  date.plusDays(2)),
                new Reservation("R-1", "H-1", "Alice", date)
        ));
        var snapshot = List.copyOf(original);
        sorter.sort(original);
        assertEquals(snapshot, original);
    }
}
