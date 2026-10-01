package practice.task20;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * First version — shipped 2024-Q1 for the partner event pipeline.
 * Converts raw timestamps to {@link Instant} using per-partner unit config.
 *
 * A silent data-corruption incident (KATX-612) was discovered three months
 * after deployment: events from Partners whose unit is "MILLIS" were being
 * stored with timestamps 1000× in the past. The root cause was never written
 * down. Read carefully before implementing {@link TimestampNormaliser}.
 */
public class TimestampNormaliserV1 {

    private static final String MILLIS  = "MILLIS";

    public List<NormalisedEvent> normalise(
            List<PartnerEvent> events,
            Map<String, String> partnerTimestampUnit) {

        return events.stream().map(e -> {
            var unit = partnerTimestampUnit.getOrDefault(e.partnerId(), "SECONDS");
            // BUG: always converts as epoch-seconds regardless of unit
            Instant occurredAt = Instant.ofEpochSecond(e.rawTimestamp());
            return new NormalisedEvent(e.eventId(), e.partnerId(), occurredAt, e.eventType());
        }).collect(Collectors.toList());
    }
}
