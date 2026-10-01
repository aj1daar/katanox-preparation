package practice.task18;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static practice.task18.StopSellRestriction.*;

class Task18StopSellCheckerTest {

    private final StopSellChecker checker = new StopSellChecker();

    private static final LocalDate JAN5  = LocalDate.of(2025, 1, 5);
    private static final LocalDate JAN10 = LocalDate.of(2025, 1, 10);
    private static final LocalDate JAN15 = LocalDate.of(2025, 1, 15);

    @Test
    void noRestrictionsAllowsAttempt() {
        var attempt = new BookingAttempt("BOOKING_COM", "DELUXE", JAN5);
        assertFalse(checker.isBlocked(attempt, List.of()));
    }

    @Test
    void channelRestrictionBlocksMatchingChannel() {
        var attempt = new BookingAttempt("BOOKING_COM", "DELUXE", JAN5);
        var restrictions = List.<StopSellRestriction>of(new ChannelRestriction("BOOKING_COM"));
        assertTrue(checker.isBlocked(attempt, restrictions));
    }

    @Test
    void channelRestrictionDoesNotBlockOtherChannel() {
        var attempt = new BookingAttempt("EXPEDIA", "DELUXE", JAN5);
        var restrictions = List.<StopSellRestriction>of(new ChannelRestriction("BOOKING_COM"));
        assertFalse(checker.isBlocked(attempt, restrictions));
    }

    @Test
    void roomTypeRestrictionBlocksMatchingRoomType() {
        var attempt = new BookingAttempt("DIRECT", "SUITE", JAN5);
        var restrictions = List.<StopSellRestriction>of(new RoomTypeRestriction("SUITE"));
        assertTrue(checker.isBlocked(attempt, restrictions));
    }

    @Test
    void dateRangeRestrictionBlocksDateWithinRange() {
        // range Jan 1–10; attempt on Jan 5 should be blocked
        var attempt = new BookingAttempt("DIRECT", "DELUXE", JAN5);
        var restrictions = List.<StopSellRestriction>of(
                new DateRangeRestriction(LocalDate.of(2025, 1, 1), JAN10));
        assertTrue(checker.isBlocked(attempt, restrictions));
    }

    @Test
    void dateRangeRestrictionDoesNotBlockDateOutsideRange() {
        // range Jan 1–10; attempt on Jan 15 should pass
        var attempt = new BookingAttempt("DIRECT", "DELUXE", JAN15);
        var restrictions = List.<StopSellRestriction>of(
                new DateRangeRestriction(LocalDate.of(2025, 1, 1), JAN10));
        assertFalse(checker.isBlocked(attempt, restrictions));
    }
}
