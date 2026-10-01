# SOLUTION — Task 08

---

## Approach

1. Group all rates by `(roomTypeId, date, channel)`, keeping the **minimum**
   price per channel (for the multi-rate edge case).
2. Group the minimums by `(roomTypeId, date)`.
3. For each group, find the DIRECT rate. If absent, skip.
4. Compare every non-DIRECT rate against DIRECT; emit a violation if lower.

```java
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public List<ParityViolation> findViolations(List<ChannelRate> rates) {
    record SlotKey(String roomTypeId, java.time.LocalDate date, String channel) {}
    record DateKey(String roomTypeId, java.time.LocalDate date) {}

    // Step 1: lowest price per (roomType, date, channel)
    var lowestPerChannel = rates.stream()
            .collect(Collectors.toMap(
                    r -> new SlotKey(r.roomTypeId(), r.date(), r.channel()),
                    r -> r,
                    (a, b) -> a.price().compareTo(b.price()) <= 0 ? a : b
            ));

    // Step 2: group by (roomType, date)
    var byDateAndType = lowestPerChannel.values().stream()
            .collect(Collectors.groupingBy(r -> new DateKey(r.roomTypeId(), r.date())));

    // Step 3 + 4: find violations
    return byDateAndType.entrySet().stream()
            .flatMap(e -> {
                var group      = e.getValue();
                var directRate = group.stream()
                        .filter(r -> DIRECT.equals(r.channel()))
                        .findFirst();
                if (directRate.isEmpty()) return Stream.empty();
                var directPrice = directRate.get().price();
                return group.stream()
                        .filter(r -> !DIRECT.equals(r.channel()))
                        .filter(r -> r.price().compareTo(directPrice) < 0)
                        .map(r -> new ParityViolation(
                                r.roomTypeId(), r.date(), r.channel(),
                                directPrice, r.price()));
            })
            .sorted(Comparator.comparing(ParityViolation::date)
                    .thenComparing(ParityViolation::roomTypeId)
                    .thenComparing(ParityViolation::offendingChannel))
            .toList();
}
```

---

## The edge case the obvious solution gets wrong

A channel with two rates for the same slot — for example a correction feed
that re-sends at a lower price. A naive implementation that takes the first or
last rate seen gives a non-deterministic result. `Collectors.toMap` with a
merge function that keeps the minimum is the correct tool.

`whenChannelHasMultipleRatesLowestIsUsedForComparison` catches this: OTA_A
sends 110.00 then 90.00; only the 90.00 should be compared against DIRECT.

---

## Follow-up questions

1. What is the time and space complexity?
2. `BigDecimal.compareTo` vs `BigDecimal.equals` — which is correct for price
   comparison, and why do they differ?
3. The DIRECT rate itself could appear multiple times (rate corrections).
   Does your implementation handle that? What price does it use?
4. The README says "same currency" to keep it simple. In production the rates
   are in different currencies. How does your solution change?
5. You sort the results at the end. The sort is O(k log k) where k is the
   number of violations. In the worst case (all channels undercut DIRECT for
   all dates) what is k relative to the input size?
6. A hotel manager asks: "which OTA violates parity most often?" How would
   you extend this to produce a per-channel violation count?
