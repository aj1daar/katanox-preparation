package practice.task20;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Task20TimestampNormaliserTest {

    private final TimestampNormaliser normaliser = new TimestampNormaliser();

    // 2024-01-15 12:00:00 UTC
    private static final long EPOCH_SECONDS = 1705320000L;
    private static final long EPOCH_MILLIS  = EPOCH_SECONDS * 1000L;
    private static final Instant EXPECTED   = Instant.ofEpochSecond(EPOCH_SECONDS);

    @Test
    void secondsUnitProducesCorrectInstant() {
        var event = new PartnerEvent("E-1", "PARTNER_A", EPOCH_SECONDS, "BOOKING_CREATED");
        var config = Map.of("PARTNER_A", "SECONDS");
        var result = normaliser.normalise(List.of(event), config);
        assertEquals(EXPECTED, result.get(0).occurredAt());
    }

    @Test
    void millisUnitProducesCorrectInstant() {
        // V1 bug: this test would fail with V1 — it would return year ~1970
        var event = new PartnerEvent("E-1", "PARTNER_B", EPOCH_MILLIS, "BOOKING_CREATED");
        var config = Map.of("PARTNER_B", "MILLIS");
        var result = normaliser.normalise(List.of(event), config);
        assertEquals(EXPECTED, result.get(0).occurredAt());
    }

    @Test
    void missingPartnerDefaultsToSeconds() {
        var event = new PartnerEvent("E-1", "UNKNOWN_PARTNER", EPOCH_SECONDS, "BOOKING_CREATED");
        var result = normaliser.normalise(List.of(event), Map.of());
        assertEquals(EXPECTED, result.get(0).occurredAt());
    }

    @Test
    void mixedPartnersNormalisedCorrectly() {
        var eventA = new PartnerEvent("E-1", "PA", EPOCH_SECONDS, "BOOKING_CREATED");
        var eventB = new PartnerEvent("E-2", "PB", EPOCH_MILLIS,  "BOOKING_CREATED");
        var config = Map.of("PA", "SECONDS", "PB", "MILLIS");
        var result = normaliser.normalise(List.of(eventA, eventB), config);
        assertEquals(EXPECTED, result.get(0).occurredAt());
        assertEquals(EXPECTED, result.get(1).occurredAt());
    }

    @Test
    void emptyEventListReturnsEmpty() {
        assertTrue(normaliser.normalise(List.of(), Map.of()).isEmpty());
    }
}
