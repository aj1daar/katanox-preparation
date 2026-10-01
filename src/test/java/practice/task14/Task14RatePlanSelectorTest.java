package practice.task14;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task14RatePlanSelectorTest {

    private final RatePlanSelector selector = new RatePlanSelector();

    private static BookingRequest req(int stayNights, int advanceDays) {
        var checkIn = LocalDate.of(2025, 6, 1);
        return new BookingRequest("DELUXE", checkIn, checkIn.plusDays(stayNights), advanceDays);
    }

    private static RatePlan plan(String id, Integer minStay, Integer maxAdvance) {
        return new RatePlan(id, id, minStay, maxAdvance, new BigDecimal("100.00"));
    }

    @Test
    void noRestrictionsMatchesAnyRequest() {
        var plans = List.of(plan("FLEX", null, null));
        var result = selector.findEligible(req(2, 10), plans);
        assertEquals(1, result.size());
        assertEquals("FLEX", result.get(0).planId());
    }

    @Test
    void planExcludedWhenStayTooShort() {
        var plans = List.of(
                plan("LONG_STAY", 7, null),
                plan("FLEX", null, null)
        );
        var result = selector.findEligible(req(3, 10), plans);
        assertEquals(1, result.size());
        assertEquals("FLEX", result.get(0).planId());
    }

    @Test
    void planExcludedWhenBookedTooFarInAdvance() {
        var plans = List.of(
                plan("LAST_MINUTE", null, 7),
                plan("FLEX", null, null)
        );
        var result = selector.findEligible(req(2, 30), plans);
        assertEquals(1, result.size());
        assertEquals("FLEX", result.get(0).planId());
    }

    @Test
    void bothRestrictionsPassedReturnsEligible() {
        var plan = new RatePlan("EARLY_BIRD", "Early Bird", 2, 90,
                new BigDecimal("80.00"));
        var result = selector.findEligible(req(3, 60), List.of(plan));
        assertEquals(1, result.size());
    }

    @Test
    void emptyPlansReturnsEmpty() {
        assertTrue(selector.findEligible(req(2, 5), List.of()).isEmpty());
    }
}
