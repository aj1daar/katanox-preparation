# SOLUTION — Task 13

## The bug in V1

`byEmail.computeIfAbsent(g.email(), ...)` uses `g.email()` as the map key
verbatim. `"Alice@hotel.com"` and `"alice@hotel.com"` produce two separate
keys. The fix: normalise with `.toLowerCase()` before using as a key.

That was KATX-489: partner PMS_A sent mixed-case emails, PMS_B sent lowercase.
Thousands of pairs that should have matched were not identified.

```java
import java.util.List;
import java.util.stream.Collectors;

public List<List<GuestRecord>> findDuplicates(List<GuestRecord> guests) {
    return guests.stream()
            .filter(g -> g.email() != null && !g.email().isBlank())
            .collect(Collectors.groupingBy(g -> g.email().toLowerCase()))
            .values().stream()
            .filter(group -> group.size() > 1)
            .toList();
}
```

## Follow-up questions

1. `.toLowerCase()` uses the default locale. In Turkish, `"I".toLowerCase()`
   is `"ı"` (dotless i), not `"i"`. Should you use `Locale.ROOT` here?
2. The method returns `List<List<GuestRecord>>`. What ordering guarantees
   does the caller get on the outer list? On the inner list?
3. Two guests have the same email but different names — which one is
   "correct"? How would you surface that ambiguity to the caller?
4. At 2M records, how much memory does the intermediate `groupingBy` map use?
5. How would you extend this to match not just on email but also on
   (firstName, lastName, checkInDate) as a fuzzy fallback?
