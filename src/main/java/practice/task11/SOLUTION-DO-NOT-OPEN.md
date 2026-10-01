# SOLUTION — Task 11

```java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public List<ChannelCommission> calculate(List<BookingSummary> bookings, List<CommissionRate> rates) {
    var rateByChannel = rates.stream()
            .collect(Collectors.toMap(CommissionRate::channel, CommissionRate::ratePercent));

    return bookings.stream()
            .filter(b -> rateByChannel.containsKey(b.channel()))
            .collect(Collectors.groupingBy(BookingSummary::channel,
                    Collectors.reducing(BigDecimal.ZERO,
                            b -> b.amount()
                                    .multiply(rateByChannel.get(b.channel()))
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP),
                            BigDecimal::add)))
            .entrySet().stream()
            .map(e -> new ChannelCommission(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(ChannelCommission::channel))
            .toList();
}
```

The ambiguity: channels with a rate but zero matching bookings do not appear
in the output with this approach (the `groupingBy` only sees bookings that
passed the filter). If the spec required them, you'd iterate over rates instead.

## Follow-up questions

1. `new BigDecimal("10.00")` vs `BigDecimal.valueOf(10.0)` — which is safer for
   financial rounding and why?
2. `RoundingMode.HALF_UP` — what other rounding modes exist, and which would
   a financial regulator typically mandate?
3. `Collectors.reducing` is used here. Could you write this with
   `Collectors.summingDouble`? What is the risk?
4. The result is not deterministic if two channels sort identically — when
   could that happen with string channel names?
5. Commission totals are per-channel for the whole month. How would you extend
   this to produce a per-booking commission ledger for audit purposes?
