package practice.task16;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task16InventoryCheckerTest {

    private final InventoryChecker checker = new InventoryChecker();

    private static final LocalDate JAN1 = LocalDate.of(2025, 1, 1);
    private static final LocalDate JAN2 = LocalDate.of(2025, 1, 2);
    private static final LocalDate JAN3 = LocalDate.of(2025, 1, 3);

    private static AllocationRequest req(LocalDate in, LocalDate out, int rooms) {
        return new AllocationRequest("R-1", "DELUXE", in, out, rooms);
    }

    private static RoomInventory inv(LocalDate date, int total, int booked) {
        return new RoomInventory("DELUXE", date, total, booked);
    }

    @Test
    void sufficientRoomsOnAllNightsReturnsTrue() {
        var inventory = List.of(inv(JAN1, 5, 2), inv(JAN2, 5, 2));
        assertTrue(checker.canAllocate(req(JAN1, JAN3, 3), inventory));
    }

    @Test
    void oneNightFullReturnsFalse() {
        var inventory = List.of(inv(JAN1, 5, 3), inv(JAN2, 5, 5));
        assertFalse(checker.canAllocate(req(JAN1, JAN3, 3), inventory));
    }

    @Test
    void missingInventoryNightCountsAsZeroReturnsFalse() {
        // Jan 2 inventory is absent — treated as 0 available
        var inventory = List.of(inv(JAN1, 5, 2));
        assertFalse(checker.canAllocate(req(JAN1, JAN3, 1), inventory));
    }

    @Test
    void checkOutNightIsNotChecked() {
        // stay is Jan1 only; Jan2 has no inventory but should not be consulted
        var inventory = List.of(inv(JAN1, 5, 2));
        assertTrue(checker.canAllocate(req(JAN1, JAN2, 3), inventory));
    }

    @Test
    void exactlyEnoughRoomsReturnsTrue() {
        var inventory = List.of(inv(JAN1, 5, 2), inv(JAN2, 5, 2));
        // available = 3, needed = 3 → exactly enough
        assertTrue(checker.canAllocate(req(JAN1, JAN3, 3), inventory));
    }
}
