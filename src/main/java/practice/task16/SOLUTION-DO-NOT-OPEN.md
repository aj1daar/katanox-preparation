# SOLUTION — Task 16

```java
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public boolean canAllocate(AllocationRequest request, List<RoomInventory> inventory) {
    Map<LocalDate, RoomInventory> byDate = inventory.stream()
            .filter(i -> i.roomTypeId().equals(request.roomTypeId()))
            .collect(Collectors.toMap(RoomInventory::date, i -> i));

    return request.checkIn()
            .datesUntil(request.checkOut())
            .allMatch(night -> {
                var inv = byDate.get(night);
                return inv != null && inv.available() >= request.roomsNeeded();
            });
}
```

## Follow-up questions

1. `datesUntil` + `allMatch` is short-circuit: it stops at the first failure.
   Does the naive nested-loop approach share this property?
2. What if `totalRooms < bookedRooms` (negative available)? Should we
   throw, clamp to 0, or let it fail the check naturally?
3. The inventory list may contain rows for other room types. Why filter by
   `roomTypeId` before building the map?
4. At scale (1,000 requests × 30 nights each = 30,000 map lookups), is
   building one map per request acceptable? What would you change?
