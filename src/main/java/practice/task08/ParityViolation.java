package practice.task08;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParityViolation(
        String roomTypeId,
        LocalDate date,
        String offendingChannel,
        BigDecimal directPrice,
        BigDecimal channelPrice
) {}
