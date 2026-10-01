package practice.task17;

/**
 * A reservation as stored in the connectivity layer.
 *
 * @param reservationId unique identifier
 * @param hotelId       property this reservation belongs to
 * @param status        current status string (e.g. "PENDING", "CONFIRMED", "CANCELLED")
 */
public record Reservation(
        String reservationId,
        String hotelId,
        String status
) {}
