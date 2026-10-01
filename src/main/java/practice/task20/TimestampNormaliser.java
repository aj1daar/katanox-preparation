package practice.task20;

import java.util.List;
import java.util.Map;

public class TimestampNormaliser {

    /**
     * Normalise raw partner events to standard {@link java.time.Instant} timestamps.
     *
     * Each partner's timestamp unit is stored in {@code partnerTimestampUnit}:
     * <ul>
     *   <li>{@code "SECONDS"} — the raw value is epoch seconds</li>
     *   <li>{@code "MILLIS"}  — the raw value is epoch milliseconds</li>
     * </ul>
     * If a partner is not present in the map, default to {@code "SECONDS"}.
     *
     * Read {@link TimestampNormaliserV1} first — incident KATX-612 was caused
     * by a bug in it. Identify the bug before writing this implementation.
     *
     * @param events                 raw partner events
     * @param partnerTimestampUnit   partner-id → "SECONDS" | "MILLIS"
     * @return events with correctly normalised timestamps
     */
    public List<NormalisedEvent> normalise(
            List<PartnerEvent> events,
            Map<String, String> partnerTimestampUnit) {
        throw new UnsupportedOperationException("not implemented");
    }
}
