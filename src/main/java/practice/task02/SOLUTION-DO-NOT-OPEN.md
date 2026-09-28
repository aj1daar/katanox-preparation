# SOLUTION — Task 02

---

## Approach

Sort by start date, then do one linear pass. Track a "current" range. For each
subsequent range, either extend current (if it overlaps or is adjacent) or emit
current and start a new one.

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public List<AvailabilityRange> merge(List<AvailabilityRange> ranges) {
    if (ranges.isEmpty()) return List.of();

    var sorted = ranges.stream()
            .sorted(Comparator.comparing(AvailabilityRange::start))
            .toList();

    var result  = new ArrayList<AvailabilityRange>();
    var current = sorted.get(0);

    for (int i = 1; i < sorted.size(); i++) {
        var next = sorted.get(i);
        // Two ranges overlap when next.start <= current.end.
        // Adjacent ranges (next.start == current.end.plusDays(1)) also merge:
        // a checkout on Feb 1 and a checkin on Feb 1 is one continuous stay.
        if (!next.start().isAfter(current.end().plusDays(1))) {
            var newEnd = current.end().isAfter(next.end()) ? current.end() : next.end();
            current = new AvailabilityRange(current.start(), newEnd);
        } else {
            result.add(current);
            current = next;
        }
    }
    result.add(current);
    return List.copyOf(result);
}
```

---

## The deliberate ambiguity — adjacent ranges

Ranges `[Jan 1–31]` and `[Feb 1–28]` share a boundary. In hotel domain:
- If the range is **inclusive** on both ends, Jan 31 and Feb 1 are separate
  days with no gap, so they should merge.
- If the range is **[start, end)** (check-out exclusive), they don't overlap.

The canonical hotel convention is *inclusive check-in, exclusive check-out*
(like `LocalDate` half-open intervals). But availability windows are often
**inclusive on both ends** because a room is either available or not on a
given night. The candidate should ask which model is in use before writing any
range comparison.

A candidate who writes the test and gets it wrong here is working from
assumption. The follow-up is: "what assumption did you make, and how does it
affect correctness at the boundaries?"

---

## Follow-up questions

1. What is the time complexity? Where does the log n come from?
2. Walk me through what happens with three ranges: `[1–5]`, `[3–8]`, `[6–10]`.
3. What happens if the input contains a zero-length range (start == end)?
   Is that valid? Should it be filtered out?
4. A hotel pushes 5 million slots but 90% of them are duplicates. Does your
   algorithm still work correctly? Is it still fast?
5. How would you adapt this if each range carried an `availableRooms` count
   and overlapping ranges had different counts?
6. The output is `List.copyOf(result)`. What guarantee does that give callers?
   What would happen without it?
