package practice.task15;

import java.math.BigDecimal;

/**
 * Aggregated pricing statistics for a set of nightly rates.
 *
 * @param total    sum of all nightly prices
 * @param average  mean nightly price (rounded to 2 decimal places, HALF_UP)
 * @param minNight cheapest single night
 * @param maxNight most expensive single night
 */
public record PriceBreakdown(
        BigDecimal total,
        BigDecimal average,
        BigDecimal minNight,
        BigDecimal maxNight
) {}
