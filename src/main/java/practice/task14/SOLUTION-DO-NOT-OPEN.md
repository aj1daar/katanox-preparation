# SOLUTION — Task 14

```java
public List<RatePlan> findEligible(BookingRequest request, List<RatePlan> plans) {
    return plans.stream()
            .filter(p -> p.minStayNights() == null
                    || request.stayNights() >= p.minStayNights())
            .filter(p -> p.maxAdvanceBookingDays() == null
                    || request.advanceBookingDays() <= p.maxAdvanceBookingDays())
            .toList();
}
```

## Follow-up questions

1. The tests don't enforce order. Should the result be sorted by `pricePerNight`?
   What does the caller need?
2. What happens if `checkIn.equals(checkOut)` (zero-night stay)?
   Should that pass a `minStayNights = 1` restriction?
3. If we add a `blackoutDates` restriction, how does the filter change?
4. How would you cache eligibility results if the same `(roomTypeId, checkIn)`
   pair is queried 10,000 times in one batch?
