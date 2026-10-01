package practice.task14;

import java.math.BigDecimal;

/**
 * A published rate plan with optional booking restrictions.
 *
 * @param planId                 unique plan identifier
 * @param name                   human-readable name
 * @param minStayNights          minimum nights required (null = no restriction)
 * @param maxAdvanceBookingDays  latest allowed booking window (null = no restriction)
 * @param pricePerNight          nightly rate in the channel currency
 */
public record RatePlan(
        String planId,
        String name,
        Integer minStayNights,
        Integer maxAdvanceBookingDays,
        BigDecimal pricePerNight
) {}
