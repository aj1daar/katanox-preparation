# SOLUTION — Task 01

---

## Approach

Use `Collectors.toMap` with a three-argument form: key extractor, value extractor,
and a merge function. The merge function compares `timestampMs` and keeps the winner.
Single pass over the list — O(n) average, O(1) extra space per unique key.

```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public Map<String, RateUpdate> deduplicate(List<RateUpdate> updates) {
    return updates.stream()
            .collect(Collectors.toMap(
                    RateUpdate::rateId,
                    u -> u,
                    (existing, incoming) ->
                            incoming.timestampMs() > existing.timestampMs() ? incoming : existing
            ));
}
```

---

## The deliberate ambiguity — tie-breaking

The README says tie behaviour is unspecified. A candidate who does not ask
before coding is assumed to have made an undocumented choice. Either policy is
defensible, but they must state it:

- `>` keeps the first-seen update when timestamps are equal (stable, predictable).
- `>=` keeps the last-seen, which is insertion-order-dependent (risky if the list
  was assembled from multiple sources).

A strong candidate says something like: "I'll use strict `>` so ties keep the
first update we saw, but I'd want to confirm with product that clock skew
across publishers isn't a concern."

---

## Follow-up questions

1. What is the time complexity? Space complexity?
2. Two rate publishers run on separate servers. Their clocks can drift by up to
   500 ms. What breaks in your solution?
3. This batch is 10 million rows. What if it doesn't fit in memory — how would
   you process it as a stream from a Kafka topic?
4. You used `Collectors.toMap`. How would `groupingBy` + `max` compare?
   What is the practical difference?
5. The key is currently `rateId`. The product team asks: "what if the same
   rateId appears under two different roomTypeIds?" How does your key change?
6. The return type is `Map<String, RateUpdate>`. What contract guarantees does
   the caller get about mutability and thread-safety?
