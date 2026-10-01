# SOLUTION — Task 10

```java
import java.util.Comparator;
import java.util.List;

public List<Reservation> sort(List<Reservation> reservations) {
    return reservations.stream()
            .sorted(Comparator.comparing(Reservation::checkIn)
                    .thenComparing(Reservation::hotelId)
                    .thenComparing(Reservation::guestName))
            .toList();
}
```

`stream().sorted().toList()` does not modify the input. Calling
`Collections.sort(reservations, ...)` would mutate it — the test
`inputListIsNotMutated` catches that mistake.

## Follow-up questions

1. What is the time complexity? Would it change if the list were already
   partially sorted?
2. `Comparator.comparing` takes a key extractor. How would you sort
   `guestName` case-insensitively?
3. What happens if `checkIn` is null for some reservations?
4. The caller asks for descending check-in instead. What changes?
5. This returns `List.of`-style immutable list. What if the caller needs to
   add to the result?
