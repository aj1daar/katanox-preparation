# SOLUTION — Task 06

---

## The deliberate ambiguity — is checkOut a stay night?

Hotel convention: `checkOut` is the day the guest **leaves**. They do not
occupy a room that night. So a booking [Jan 1, Jan 3] covers exactly two
nights: Jan 1 and Jan 2. The range is **[checkIn, checkOut)** — inclusive
start, exclusive end.

`LocalDate.datesUntil(end)` returns exactly this: all dates from start
(inclusive) to end (exclusive). This is the tool to reach for.

A candidate who treats checkOut as inclusive produces an extra night, which
causes `checkOutDateIsNotAStayNight` to fail: B1 and B2 would both claim
Jan 3, triggering a false alert.

---

## Approach

1. Build a capacity lookup map from the inventory list.
2. `flatMap` each booking into its individual stay nights using
   `LocalDate.datesUntil`.
3. Group by `(date, roomTypeId)` and count.
4. Filter to entries where count > capacity.
5. Map to `OverbookingAlert` and sort.

```java
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public List<OverbookingAlert> detect(List<Booking> bookings, List<RoomInventory> inventory) {
    var capacityByType = inventory.stream()
            .collect(Collectors.toMap(RoomInventory::roomTypeId, RoomInventory::capacity));

    record NightKey(java.time.LocalDate date, String roomTypeId) {}

    var countsByNight = bookings.stream()
            .flatMap(b -> b.checkIn().datesUntil(b.checkOut())
                    .map(date -> new NightKey(date, b.roomTypeId())))
            .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

    return countsByNight.entrySet().stream()
            .filter(e -> {
                int cap = capacityByType.getOrDefault(e.getKey().roomTypeId(), 0);
                return e.getValue() > cap;
            })
            .map(e -> {
                int cap = capacityByType.getOrDefault(e.getKey().roomTypeId(), 0);
                return new OverbookingAlert(
                        e.getKey().date(), e.getKey().roomTypeId(),
                        e.getValue().intValue(), cap);
            })
            .sorted(Comparator.comparing(OverbookingAlert::date)
                    .thenComparing(OverbookingAlert::roomTypeId))
            .toList();
}
```

Note: a local record inside a method is valid Java 16+.

---

## Follow-up questions

1. What is the time complexity? Where does `datesUntil` fit in?
2. A booking with checkIn == checkOut (zero-night stay) — is that valid?
   What does your implementation do with it?
3. `Collectors.groupingBy` + `counting` is equivalent to what SQL clause?
4. The result is not sorted until the final step. What happens to that sort
   if `countsByNight` has 10 million entries?
5. A room type missing from inventory triggers an alert with capacity 0. Is
   that the right behaviour, or should missing room types be ignored?
6. How would you adapt this if cancellations could arrive later, requiring
   a re-run of the detection?
