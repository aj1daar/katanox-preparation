package practice.task19;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A confirmed booking with a fixed nightly rate.
 *
 * @param bookingId     unique booking identifier
 * @param checkIn       first night (inclusive)
 * @param checkOut      departure date (exclusive — not a stay night)
 * @param pricePerNight nightly rate charged to the guest
 */
public record StayBooking(
        String bookingId,
        LocalDate checkIn,
        LocalDate checkOut,
        BigDecimal pricePerNight
) {}
