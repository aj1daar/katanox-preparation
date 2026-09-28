package practice.task04;

// Java 25 features used in this task:
//   record (ReservationEvent), var, Instant, Duration/ChronoUnit, stream filter + count
// Java 8 equivalent:
//   POJO with getters, Date or Calendar, manual loop with long comparison

import java.time.Instant;

public record ReservationEvent(
        String reservationId,
        String hotelId,
        Instant createdAt
) {}
