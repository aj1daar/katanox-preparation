package practice.task06;

// Java 25 features used in this task:
//   records (Booking, RoomInventory, OverbookingAlert), var, stream().flatMap(),
//   LocalDate.datesUntil(), Collectors.groupingBy + counting, Comparator.comparing chained
// Java 8 equivalent:
//   POJOs with getters, explicit date loop with plusDays, HashMap with manual count merge

import java.time.LocalDate;

public record Booking(
        String bookingId,
        String roomTypeId,
        LocalDate checkIn,
        LocalDate checkOut
) {}
