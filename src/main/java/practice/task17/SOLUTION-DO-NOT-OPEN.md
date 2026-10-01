# SOLUTION — Task 17

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public UpdateResult applyUpdates(List<Reservation> reservations, List<StatusUpdate> updates) {
    Set<String> knownIds = reservations.stream()
            .map(Reservation::reservationId)
            .collect(Collectors.toSet());

    List<String> updated = new ArrayList<>();
    List<String> notFound = new ArrayList<>();

    for (var u : updates) {
        if (knownIds.contains(u.reservationId())) {
            updated.add(u.reservationId());
        } else {
            notFound.add(u.reservationId());
        }
    }
    return new UpdateResult(updated, notFound);
}
```

## Follow-up questions

1. The method only returns the summary (`updated`, `notFound`). What would
   change if the caller also needed the updated `Reservation` objects back?
2. What if two updates target the same `reservationId`? Should both appear
   in `updated`, or only one?
3. Records are immutable. If you need to represent the "after" state, how
   do you produce a modified record? (`withStatus` doesn't exist — Java
   records don't have wither methods by default. What are your options?)
4. The PMS may send the same cancellation twice. How would you make
   `applyUpdates` idempotent?
