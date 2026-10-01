# SOLUTION — Task 12

```java
import java.util.Comparator;
import java.util.List;

public List<NightlyCharge> topN(List<NightlyCharge> charges, int n) {
    return charges.stream()
            .sorted(Comparator.comparing(NightlyCharge::price).reversed())
            .limit(n)
            .toList();
}
```

The tie ambiguity: `.limit(n)` takes the first n elements in sort order.
When two charges share a price, their relative order within the sort is
undefined (the sort is not stable for equal keys unless secondary keys are
defined). A candidate should ask whether ties should be broken by date,
reservationId, or simply accepted as non-deterministic.

## Follow-up questions

1. What is the time complexity? Is there a way to solve this in O(n log k)
   instead of O(n log n)?
2. `Comparator.comparing(...).reversed()` — what does `.reversed()` return?
   Can you chain more `.thenComparing()` calls after it?
3. The sort is not stable for equal prices. How would you make the result
   deterministic without changing the primary sort criterion?
4. At 10M nightly charges, what is the memory cost of this approach? How
   would a min-heap of size k improve it?
5. `BigDecimal.compareTo` is used internally by the Comparator. What would
   happen if you used `BigDecimal.equals` instead?
