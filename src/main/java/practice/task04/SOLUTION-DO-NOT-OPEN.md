# SOLUTION — Task 04

---

## Approach

Compute the cutoff instant, then count events whose `createdAt` is within
`[cutoff, now]`. The boundary is **inclusive** on both ends as documented in
the Javadoc.

```java
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public long countInLastMinutes(List<ReservationEvent> events, long windowMinutes, Instant now) {
    var cutoff = now.minus(windowMinutes, ChronoUnit.MINUTES);
    return events.stream()
            .filter(e -> !e.createdAt().isBefore(cutoff) && !e.createdAt().isAfter(now))
            .count();
}
```

`!isBefore(cutoff)` is `>= cutoff` and `!isAfter(now)` is `<= now`.
Using `isBefore`/`isAfter` avoids the common mistake of calling `.compareTo()`
or converting to epoch millis manually.

---

## The edge case the obvious solution gets wrong

A candidate who writes:

```java
e.createdAt().isAfter(cutoff) && !e.createdAt().isAfter(now)
```

uses **strict** greater-than for the start. The test
`eventExactlyAtWindowStartIsIncluded` catches this: an event at exactly the
cutoff moment is excluded by `isAfter` but included by `!isBefore`.

Ask them: "your filter uses `isAfter(cutoff)` — what happens to a booking
that arrived at exactly the cutoff second?"

---

## The testability point

A candidate who hardcodes `Instant.now()` inside the method produces a test
that can only assert counts at the time of execution. The `now` parameter was
the hint. If they don't notice it and ask why it is there, prompt them: "how
would you write a unit test for this if the method called `Instant.now()`
internally?"

---

## Follow-up questions

1. This is O(n) per query. If the dashboard polls every 5 seconds, what is
   the load on the system at 1 M events? How would you make repeated queries
   cheaper?
2. The events are currently in a `List`. What data structure would make range
   queries faster? What are the trade-offs?
3. Two events have identical `createdAt`. Does your solution count both?
   Should it?
4. `windowMinutes` is a `long`. What happens if the caller passes `0` or `-1`?
   Should the method validate this?
5. The list is `List<ReservationEvent>`. What if it were a real-time stream of
   millions of events per minute — how does your implementation change?
