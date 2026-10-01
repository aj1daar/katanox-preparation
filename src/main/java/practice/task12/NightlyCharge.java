package practice.task12;

// Java 25 features: records, stream().sorted().limit(), Comparator.reversed(), var
// Java 8 equivalent: POJO with getters, Collections.sort + subList

import java.math.BigDecimal;
import java.time.LocalDate;

public record NightlyCharge(String reservationId, LocalDate date, BigDecimal price) {}
