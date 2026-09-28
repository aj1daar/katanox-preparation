package practice.task03;

// Java 25 features used in this task:
//   sealed interface with record subtypes, pattern matching for switch, var, records
// Java 8 equivalent:
//   abstract class hierarchy, instanceof chain with explicit casts, POJO output class

import java.math.BigDecimal;
import java.time.LocalDate;

public record Booking(
        String reservationId,
        LocalDate checkIn,
        LocalDate checkOut,
        String roomCode,
        BigDecimal amount,
        String currency
) {}
