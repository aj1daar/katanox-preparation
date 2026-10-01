package practice.task10;

// Java 25 features: record, Comparator.comparing().thenComparing() chaining, stream().toList()
// Java 8 equivalent: POJO with getters, Collections.sort with multi-field anonymous Comparator

import java.time.LocalDate;

public record Reservation(
        String reservationId,
        String hotelId,
        String guestName,
        LocalDate checkIn
) {}
