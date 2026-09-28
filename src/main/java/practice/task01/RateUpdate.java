package practice.task01;

// Java 25 features used in this task:
//   record (RateUpdate), var, stream(), Collectors.toMap with merge function, stream().toList()
// Java 8 equivalent:
//   POJO class with private fields + getters, explicit HashMap, anonymous Comparator, new ArrayList<>()

import java.math.BigDecimal;

public record RateUpdate(
        String roomTypeId,
        String rateId,
        BigDecimal price,
        long timestampMs
) {}
