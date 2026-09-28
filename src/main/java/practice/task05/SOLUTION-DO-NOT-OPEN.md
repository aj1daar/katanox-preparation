# SOLUTION — Task 05

---

## Approach

Build a `Set<String>` of IDs from each list. Then stream each list and filter
against the other set. O(n) average case — HashSet lookup is O(1) amortised.

```java
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public ReconciliationResult reconcile(List<ReservationRef> systemA, List<ReservationRef> systemB) {
    var idsInA = systemA.stream()
            .map(ReservationRef::reservationId)
            .collect(Collectors.toSet());

    var idsInB = systemB.stream()
            .map(ReservationRef::reservationId)
            .collect(Collectors.toSet());

    var missingFromB = systemA.stream()
            .filter(r -> !idsInB.contains(r.reservationId()))
            .distinct()
            .sorted(Comparator.comparing(ReservationRef::reservationId))
            .toList();

    var phantomInB = systemB.stream()
            .filter(r -> !idsInA.contains(r.reservationId()))
            .distinct()
            .sorted(Comparator.comparing(ReservationRef::reservationId))
            .toList();

    return new ReconciliationResult(missingFromB, phantomInB);
}
```

`.distinct()` handles the case where the same `reservationId` appears more
than once in a list — only one entry surfaces in the result. Candidates should
state this choice explicitly (see ambiguity below).

---

## The deliberate ambiguity — duplicates within one list

The README says "each list may contain the same reservationId more than once"
without specifying the output. Two defensible choices:

- **Deduplicate**: emit one entry per unique ID — clean output, easier to act on.
- **Preserve duplicates**: emit every entry that has no match — more faithful
  to the raw data, shows magnitude of the problem.

A candidate who does not ask and does not say what they chose has made a hidden
assumption. Follow-up: "your result has N entries for ID X which appeared 3
times in A — is that intentional?"

---

## The edge case the obvious solution gets wrong

Using `contains()` on a `List` instead of a `Set` for the membership check:

```java
// WRONG — O(n) per lookup, O(n²) total
var missingFromB = systemA.stream()
        .filter(r -> !systemB.contains(r))   // searches entire list each time
        .toList();
```

`systemB.contains(r)` checks structural equality of `ReservationRef`, which
includes `source`. A record from A with `source="system-a"` will never equal
one from B with `source="system-b"` even if `reservationId` matches. This
gives wrong answers AND is slow. The test `partialOverlapIdentifiesBothDiscrepancies`
catches the wrong-answer half.

---

## Follow-up questions

1. What is the time complexity? Where does the set build happen?
2. At 5 M records × 2 systems, how much heap do these two `Set`s consume?
   (Rough estimate is fine — they want to see you reason about memory.)
3. `.distinct()` uses `.equals()` on `ReservationRef`. What does `.equals()`
   compare on a record?
4. The lists are in memory. What if they came from two database tables with
   100 M rows each? How does your algorithm change?
5. You sorted both result lists. What is the cost of that sort relative to the
   set-build step?
6. A reservation can genuinely exist in both systems under different IDs (a
   re-booking). How would you handle that?
