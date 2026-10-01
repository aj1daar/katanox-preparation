package practice.task13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task13GuestDeduplicatorTest {

    private final GuestDeduplicator deduplicator = new GuestDeduplicator();

    @Test
    void noMatchingEmailsProducesEmptyResult() {
        var guests = List.of(
                new GuestRecord("G-1", "Alice", "alice@hotel.com", "PMS_A"),
                new GuestRecord("G-2", "Bob",   "bob@hotel.com",   "PMS_B")
        );
        assertTrue(deduplicator.findDuplicates(guests).isEmpty());
    }

    @Test
    void twoGuestsWithSameEmailFormOneGroup() {
        var guests = List.of(
                new GuestRecord("G-1", "Alice",  "alice@hotel.com", "PMS_A"),
                new GuestRecord("G-2", "A. Smith","alice@hotel.com", "PMS_B")
        );
        var result = deduplicator.findDuplicates(guests);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
    }

    @Test
    void emailComparisonIsCaseInsensitive() {
        // V1 bug: "Alice@hotel.com" and "alice@hotel.com" were treated as different
        var guests = List.of(
                new GuestRecord("G-1", "Alice", "Alice@hotel.com", "PMS_A"),
                new GuestRecord("G-2", "Alice", "alice@hotel.com", "PMS_B")
        );
        var result = deduplicator.findDuplicates(guests);
        assertEquals(1, result.size());
    }

    @Test
    void guestsWithNullOrBlankEmailAreExcluded() {
        var guests = List.of(
                new GuestRecord("G-1", "Alice", null,  "PMS_A"),
                new GuestRecord("G-2", "Bob",   "   ", "PMS_B"),
                new GuestRecord("G-3", "Carol", "carol@hotel.com", "PMS_A"),
                new GuestRecord("G-4", "Carol", "carol@hotel.com", "PMS_B")
        );
        var result = deduplicator.findDuplicates(guests);
        assertEquals(1, result.size());
    }

    @Test
    void threeGuestsWithSameEmailGroupedTogether() {
        var guests = List.of(
                new GuestRecord("G-1", "X", "x@hotel.com", "PMS_A"),
                new GuestRecord("G-2", "X", "x@hotel.com", "PMS_B"),
                new GuestRecord("G-3", "X", "X@hotel.com", "PMS_C")
        );
        var result = deduplicator.findDuplicates(guests);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).size());
    }
}
