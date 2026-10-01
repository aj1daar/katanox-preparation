package practice.task20;

/**
 * A raw event received from a partner's webhook.
 *
 * {@code rawTimestamp} is an integer epoch value — but partners disagree on
 * the unit. Some send milliseconds since epoch, others send seconds.
 * The unit for each partner is stored in a configuration map.
 *
 * @param eventId      unique event identifier
 * @param partnerId    the partner who sent this event
 * @param rawTimestamp epoch value in an unspecified unit (seconds or millis)
 * @param eventType    e.g. "BOOKING_CREATED", "BOOKING_CANCELLED"
 */
public record PartnerEvent(
        String eventId,
        String partnerId,
        long rawTimestamp,
        String eventType
) {}
