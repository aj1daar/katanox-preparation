package practice.task20;

import java.time.Instant;

/**
 * A partner event with its timestamp converted to a standard {@link Instant}.
 *
 * @param eventId    unique event identifier
 * @param partnerId  the partner who sent this event
 * @param occurredAt wall-clock time of the event
 * @param eventType  e.g. "BOOKING_CREATED"
 */
public record NormalisedEvent(
        String eventId,
        String partnerId,
        Instant occurredAt,
        String eventType
) {}
