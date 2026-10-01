package practice.task11;

// Java 25 features: records, Collectors.groupingBy + reducing, BigDecimal streams, var
// Java 8 equivalent: POJO, HashMap with manual accumulation, BigDecimal.add loop

import java.math.BigDecimal;

public record BookingSummary(String reservationId, String channel, BigDecimal amount) {}
