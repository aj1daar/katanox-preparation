# SOLUTION — Task 15

```java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public Optional<PriceBreakdown> calculate(List<DailyRate> rates) {
    if (rates.isEmpty()) return Optional.empty();

    var total = rates.stream()
            .map(DailyRate::price)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    var average = total.divide(
            BigDecimal.valueOf(rates.size()), 2, RoundingMode.HALF_UP);

    var minNight = rates.stream()
            .map(DailyRate::price)
            .min(Comparator.naturalOrder())
            .orElseThrow();

    var maxNight = rates.stream()
            .map(DailyRate::price)
            .max(Comparator.naturalOrder())
            .orElseThrow();

    return Optional.of(new PriceBreakdown(total, average, minNight, maxNight));
}
```

## Follow-up questions

1. The spec is silent on `total` scale. Is `new BigDecimal("450.000")` equal
   to `new BigDecimal("450.00")` under `assertEquals`? (Hint: check
   `BigDecimal.compareTo` vs `equals`.)
2. We iterate the list four times (reduce, min, max, divide). Can you do it
   in one pass? What collection would you use?
3. If `rates` contains a null `price`, what happens? Should you validate at
   the start or let it propagate?
4. How would you extend this to also return the date of the cheapest night?
