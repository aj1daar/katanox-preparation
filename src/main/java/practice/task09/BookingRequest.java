package practice.task09;

// Java 25 features: records, var, stream().filter(), List.copyOf(), sealed type for errors
// Java 8 equivalent: POJO with getters, ArrayList, manual null checks

import java.time.LocalDate;

public record BookingRequest(
        String guestId,
        String hotelId,
        String roomTypeId,
        LocalDate checkIn,
        LocalDate checkOut
) {}
