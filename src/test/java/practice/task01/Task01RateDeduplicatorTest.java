package practice.task01;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Task01RateDeduplicatorTest {

    private final RateDeduplicatorImpl deduplicator = new RateDeduplicatorImpl();

    @Test
    void emptyInputReturnsEmptyMap() {
        var result = deduplicator.deduplicate(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void singleUpdateIsRetained() {
        var update = new RateUpdate("room-1", "rate-abc", new BigDecimal("120.00"), 1000L);
        var result = deduplicator.deduplicate(List.of(update));
        assertEquals(update, result.get("rate-abc"));
    }

    @Test
    void higherTimestampWinsOverLower() {
        var older  = new RateUpdate("room-1", "rate-abc", new BigDecimal("120.00"), 1000L);
        var newer  = new RateUpdate("room-1", "rate-abc", new BigDecimal("150.00"), 2000L);
        var result = deduplicator.deduplicate(List.of(older, newer));
        assertEquals(new BigDecimal("150.00"), result.get("rate-abc").price());
    }

    @Test
    void outOfOrderDeliveryDoesNotAffectResult() {
        // newer update appears first in the list; older arrives last — older must still lose
        var newer  = new RateUpdate("room-1", "rate-abc", new BigDecimal("150.00"), 2000L);
        var older  = new RateUpdate("room-1", "rate-abc", new BigDecimal("120.00"), 1000L);
        var result = deduplicator.deduplicate(List.of(newer, older));
        assertEquals(new BigDecimal("150.00"), result.get("rate-abc").price());
    }

    @Test
    void updatesForDifferentRateIdsAreAllRetained() {
        var a = new RateUpdate("room-1", "rate-aaa", new BigDecimal("100.00"), 1000L);
        var b = new RateUpdate("room-1", "rate-bbb", new BigDecimal("200.00"), 1000L);
        var result = deduplicator.deduplicate(List.of(a, b));
        assertEquals(2, result.size());
        assertTrue(result.containsKey("rate-aaa"));
        assertTrue(result.containsKey("rate-bbb"));
    }

    @Test
    void oneMillionUpdatesCompleteWithinThreeSeconds() {
        int total     = 1_000_000;
        int uniqueIds = 50_000;
        var rng       = new Random(42L);
        var updates   = new ArrayList<RateUpdate>(total);
        var prices    = new BigDecimal[20];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = BigDecimal.valueOf(50 + i * 10);
        }

        for (int i = 0; i < total; i++) {
            updates.add(new RateUpdate(
                    "room-" + (i % 200),
                    "rate-" + (i % uniqueIds),
                    prices[i % prices.length],
                    rng.nextLong(10_000_000L)
            ));
        }

        var start  = System.currentTimeMillis();
        var result = deduplicator.deduplicate(updates);
        var elapsed = System.currentTimeMillis() - start;

        assertFalse(result.isEmpty());
        assertTrue(elapsed < 3000,
                "Expected deduplication of 1M updates in < 3s but took " + elapsed + "ms");
    }
}
