package practice.task18;

import java.time.LocalDate;

/**
 * A booking attempt that may be blocked by stop-sell restrictions.
 *
 * @param channel    distribution channel (e.g. "BOOKING_COM", "DIRECT")
 * @param roomTypeId requested room type
 * @param date       the check-in date of the booking attempt
 */
public record BookingAttempt(String channel, String roomTypeId, LocalDate date) {}
