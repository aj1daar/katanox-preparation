# SOLUTION — Task 19

```java
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public RevenueSummary calculate(List<StayBooking> bookings, LocalDate from, LocalDate to) {
    LocalDate windowEnd = to.plusDays(1); // make exclusive for datesUntil

    BigDecimal total = BigDecimal.ZERO;
    int count = 0;

    for (var b : bookings) {
        LocalDate overlapStart = b.checkIn().isAfter(from)  ? b.checkIn()  : from;
        LocalDate overlapEnd   = b.checkOut().isBefore(windowEnd) ? b.checkOut() : windowEnd;

        if (overlapStart.isBefore(overlapEnd)) {
            long nights = overlapStart.datesUntil(overlapEnd).count();
            total = total.add(b.pricePerNight().multiply(BigDecimal.valueOf(nights)));
            count++;
        }
    }
    return new RevenueSummary(from, to, total, count);
}
```

## Follow-up questions

1. `datesUntil` returns a `Stream<LocalDate>`. We only need the count — could
   you compute `nights` without materialising the stream?
   (`ChronoUnit.DAYS.between(overlapStart, overlapEnd)`)
2. The revenue here is `nights × pricePerNight`. In reality, nightly prices
   vary day-by-day. How would you change the data model?
3. What if a booking is cancelled mid-stay? Should `checkOut` be updated to
   the actual departure date, or should a separate `CancellationEvent` exist?
4. Finance wants revenue grouped by month, not a single total. What changes?
