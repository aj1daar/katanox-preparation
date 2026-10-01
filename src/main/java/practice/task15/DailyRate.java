package practice.task15;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The nightly price for one room type on one specific date.
 */
public record DailyRate(
        LocalDate date,
        String roomTypeId,
        BigDecimal price
) {}
