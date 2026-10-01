package practice.task17;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task17ReservationUpdaterTest {

    private final ReservationUpdater updater = new ReservationUpdater();

    @Test
    void allUpdatesMatchedProducesNoNotFound() {
        var reservations = List.of(
                new Reservation("R-1", "H-1", "PENDING"),
                new Reservation("R-2", "H-1", "PENDING")
        );
        var updates = List.of(
                new StatusUpdate("R-1", "CONFIRMED"),
                new StatusUpdate("R-2", "CONFIRMED")
        );
        var result = updater.applyUpdates(reservations, updates);
        assertEquals(2, result.updated().size());
        assertTrue(result.notFound().isEmpty());
    }

    @Test
    void unknownIdGoesToNotFound() {
        var reservations = List.of(new Reservation("R-1", "H-1", "PENDING"));
        var updates = List.of(new StatusUpdate("R-999", "CANCELLED"));
        var result = updater.applyUpdates(reservations, updates);
        assertTrue(result.updated().isEmpty());
        assertEquals(List.of("R-999"), result.notFound());
    }

    @Test
    void mixedBatchSplitsCorrectly() {
        var reservations = List.of(
                new Reservation("R-1", "H-1", "PENDING"),
                new Reservation("R-2", "H-1", "PENDING")
        );
        var updates = List.of(
                new StatusUpdate("R-1", "CONFIRMED"),
                new StatusUpdate("R-3", "CONFIRMED")
        );
        var result = updater.applyUpdates(reservations, updates);
        assertEquals(List.of("R-1"), result.updated());
        assertEquals(List.of("R-3"), result.notFound());
    }

    @Test
    void emptyUpdatesReturnsEmptyResult() {
        var reservations = List.of(new Reservation("R-1", "H-1", "CONFIRMED"));
        var result = updater.applyUpdates(reservations, List.of());
        assertTrue(result.updated().isEmpty());
        assertTrue(result.notFound().isEmpty());
    }

    @Test
    void largeInputIsProcessedEfficiently() {
        int n = 100_000;
        var reservations = new java.util.ArrayList<Reservation>(n);
        for (int i = 0; i < n; i++) {
            reservations.add(new Reservation("R-" + i, "H-1", "PENDING"));
        }
        var updates = new java.util.ArrayList<StatusUpdate>(n);
        for (int i = 0; i < n; i++) {
            updates.add(new StatusUpdate("R-" + i, "CONFIRMED"));
        }
        var result = updater.applyUpdates(reservations, updates);
        assertEquals(n, result.updated().size());
        assertTrue(result.notFound().isEmpty());
    }
}
