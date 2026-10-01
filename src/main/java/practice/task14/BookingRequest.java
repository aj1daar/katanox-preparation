package practice.task14;

import java.time.LocalDate;

/**
 * A guest's request to book a room under some rate plan.
 *
 * @param roomTypeId          the room type the guest wants
 * @param checkIn             arrival date (inclusive)
 * @param checkOut            departure date (exclusive — not a stay night)
 * @param advanceBookingDays  how many days in advance the booking is made
 */
public record BookingRequest(
        String roomTypeId,
        LocalDate checkIn,
        LocalDate checkOut,
        int advanceBookingDays
) {
    public int stayNights() {
        return (int) checkIn.datesUntil(checkOut).count();
    }
}
