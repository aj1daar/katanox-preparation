package practice.task16;

import java.time.LocalDate;

/**
 * A request to allocate rooms for a reservation.
 *
 * @param reservationId unique reservation identifier
 * @param roomTypeId    the type of room being booked
 * @param checkIn       first night (inclusive)
 * @param checkOut      departure date (exclusive — not a stay night)
 * @param roomsNeeded   how many rooms of this type are required
 */
public record AllocationRequest(
        String reservationId,
        String roomTypeId,
        LocalDate checkIn,
        LocalDate checkOut,
        int roomsNeeded
) {}
