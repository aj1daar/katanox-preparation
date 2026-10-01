# SOLUTION — Task 07

---

## The performance problem in V1

V1 does **two full scans of the event list per unique reservationId**:
1. One scan to collect all unique IDs.
2. One scan per ID to collect that ID's events.

If there are n total events and m unique reservations, the cost is O(n + n·m).
With 5M events and 1M reservations that is 5 × 10¹² operations in the worst
case. KATX-512 was filed after a 200k-event test took 40 seconds.

Fix: group all events by reservationId in a single O(n) pass first
(`Collectors.groupingBy` or `computeIfAbsent`), then process each group.
Total cost drops to O(n log n) — dominated by sorting within each group.

## The behavioural issue in V1

`BookingModified` is only applied when `current.status() == ACTIVE`:
```java
} else if (e instanceof BookingEvent.BookingModified m) {
    if (current != null && current.status() == BookingStatus.ACTIVE) {
```

This means a MODIFIED event that arrives **after a CANCELLED** is silently
dropped. Whether that is correct is the ambiguity (see README). V1 never
clarified it — it just happened to drop it. A correct implementation must
make an explicit choice and state it.

---

## Solution

```java
import java.util.*;
import java.util.stream.Collectors;

public Map<String, BookingSnapshot> process(List<BookingEvent> events) {
    var grouped = events.stream()
            .collect(Collectors.groupingBy(BookingEvent::reservationId));

    var result = new HashMap<String, BookingSnapshot>(grouped.size() * 2);
    for (var entry : grouped.entrySet()) {
        var ordered = entry.getValue().stream()
                .sorted(Comparator.comparing(BookingEvent::occurredAt))
                .toList();
        BookingSnapshot current = null;
        for (var e : ordered) {
            current = apply(current, e);
        }
        if (current != null) result.put(entry.getKey(), current);
    }
    return result;
}

private BookingSnapshot apply(BookingSnapshot s, BookingEvent e) {
    return switch (e) {
        case BookingEvent.BookingCreated c -> new BookingSnapshot(
                c.reservationId(), BookingStatus.ACTIVE,
                c.guestName(), c.checkIn(), c.checkOut(), c.roomTypeId());
        case BookingEvent.BookingModified m -> s == null ? null :
                new BookingSnapshot(s.reservationId(), s.status(),
                        m.guestName(), m.checkIn(), m.checkOut(), s.roomTypeId());
        case BookingEvent.BookingCancelled ignored -> s == null ? null :
                new BookingSnapshot(s.reservationId(), BookingStatus.CANCELLED,
                        s.guestName(), s.checkIn(), s.checkOut(), s.roomTypeId());
        case BookingEvent.BookingReinstated ignored -> s == null ? null :
                new BookingSnapshot(s.reservationId(), BookingStatus.ACTIVE,
                        s.guestName(), s.checkIn(), s.checkOut(), s.roomTypeId());
        case BookingEvent.BookingNoShow ignored -> s == null ? null :
                new BookingSnapshot(s.reservationId(), BookingStatus.NO_SHOW,
                        s.guestName(), s.checkIn(), s.checkOut(), s.roomTypeId());
    };
}
```

Decision on MODIFIED-after-CANCELLED: apply it regardless of status. A
date change may have been sent by the OTA before they knew the cancellation
had already been processed. Silently dropping it is worse than applying it.

---

## Follow-up questions

1. What is the time complexity of the fixed version vs V1?
2. The sealed switch has no `default`. What happens when a new event type
   is added to `BookingEvent`?
3. Two `BookingCreated` events arrive for the same reservationId (duplicate
   submission). What does your implementation do?
4. You sort each group in-memory. With 5M events the entire list is in
   memory. What would you do if the event log came from Kafka and was too
   large to load at once?
5. `BookingReinstated` after `NO_SHOW` — valid or not? How would you enforce
   that in the type system rather than at runtime?
6. What data structure would you use if you needed the current state to be
   queryable in real-time as events arrive, rather than processed in a batch?
