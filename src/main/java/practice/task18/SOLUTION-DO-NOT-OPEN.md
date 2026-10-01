# SOLUTION — Task 18

```java
public boolean isBlocked(BookingAttempt attempt, List<StopSellRestriction> restrictions) {
    return restrictions.stream().anyMatch(r -> matches(attempt, r));
}

private boolean matches(BookingAttempt a, StopSellRestriction r) {
    return switch (r) {
        case ChannelRestriction  c -> c.channel().equals(a.channel());
        case RoomTypeRestriction t -> t.roomTypeId().equals(a.roomTypeId());
        case DateRangeRestriction d ->
                !a.date().isBefore(d.from()) && !a.date().isAfter(d.to());
    };
}
```

## Follow-up questions

1. The switch is exhaustive — no `default` arm. What happens if a fourth
   subtype is added to `StopSellRestriction` without updating this switch?
   (Compile error, or runtime error? Which is better?)
2. `anyMatch` is short-circuit. Does the ordering of restrictions in the
   list matter for correctness? For performance?
3. How would you represent a restriction that combines channel AND room type
   (block channel X for room type Y only)? What does that do to the sealed
   hierarchy?
4. Hotels sometimes lift restrictions mid-day. How would you add an expiry
   timestamp to a restriction without changing existing subtypes?
