package practice.task19;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Aggregated revenue for a reporting window.
 *
 * @param from          window start (inclusive)
 * @param to            window end (inclusive)
 * @param totalRevenue  sum of nightly revenue for nights within [from, to]
 * @param bookingCount  number of bookings that contributed at least one night
 */
public record RevenueSummary(
        LocalDate from,
        LocalDate to,
        BigDecimal totalRevenue,
        int bookingCount
) {}
