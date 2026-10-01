# SOLUTION — Task 20

## The bug in V1

`Instant.ofEpochSecond(e.rawTimestamp())` is called unconditionally.
The `unit` variable is read from the config but never used.
For partners sending milliseconds (e.g. `1705320000000`), the result is
year ~55933 — far in the future. KATX-612 was actually worse: an old
partner sent seconds-range values like `1705320000`, but the code was
treating a *different* partner's millis-format value as seconds, placing
events in year 1970.

The fix: branch on `unit` and call the right factory method.

```java
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public List<NormalisedEvent> normalise(
        List<PartnerEvent> events,
        Map<String, String> partnerTimestampUnit) {

    return events.stream().map(e -> {
        var unit = partnerTimestampUnit.getOrDefault(e.partnerId(), "SECONDS");
        Instant occurredAt = "MILLIS".equals(unit)
                ? Instant.ofEpochMilli(e.rawTimestamp())
                : Instant.ofEpochSecond(e.rawTimestamp());
        return new NormalisedEvent(e.eventId(), e.partnerId(), occurredAt, e.eventType());
    }).toList();
}
```

## Follow-up questions

1. What if a partner sends epoch **microseconds** (some systems do)?
   `Instant.ofEpochSecond(s, adjustNanos)` can handle this — how?
2. A new partner is onboarded and their unit is unknown. What should the
   system do: default to SECONDS, reject the events, or auto-detect?
3. How would you write a unit test that catches the V1 bug without knowing
   the expected `Instant` value in advance?
4. The config map is passed per-call. In production, partner config is read
   from a database. How would you restructure the class to inject that
   dependency without breaking existing tests?
