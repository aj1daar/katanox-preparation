package practice.task08;

// Java 25 features used in this task:
//   records (ChannelRate, ParityViolation), var, Collectors.groupingBy,
//   stream().flatMap(), Map.Entry streaming, Comparator chaining
// Java 8 equivalent:
//   POJOs, manual HashMap grouping, nested for-each loops

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChannelRate(
        String roomTypeId,
        LocalDate date,
        String channel,
        BigDecimal price
) {}
