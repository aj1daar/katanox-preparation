package practice.task19;

import java.time.LocalDate;
import java.util.List;

public class RevenueCalculator {

    /**
     * Calculate total revenue earned on nights that fall within [from, to]
     * (both endpoints inclusive).
     *
     * For each booking, count only the nights that overlap with the window:
     * nights from max(checkIn, from) to min(checkOut, to+1) (exclusive upper).
     * Revenue for each counted night is the booking's {@code pricePerNight}.
     *
     * {@code bookingCount} is the number of distinct bookings that contributed
     * at least one night to the window.
     *
     * @param bookings all confirmed bookings
     * @param from     window start (inclusive)
     * @param to       window end (inclusive)
     * @return revenue summary for the window
     */
    public RevenueSummary calculate(List<StayBooking> bookings, LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException("not implemented");
    }
}
