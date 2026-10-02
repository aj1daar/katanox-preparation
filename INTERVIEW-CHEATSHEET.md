# Interview Cheatsheet — Katanox, 2 October, 13:00 CET

**Role:** Software Engineer – Connectivity, L1  
**Format:** Clone repo live → code in IntelliJ → follow-up questions  
**Stack they use:** Kotlin. The challenge is Java 25. Use modern idioms — they're testing the same instincts.

---

## The single most important habit

**Ask a clarifying question before you write a single line.**

This is the first thing they score. Under pressure you will skip it. Don't.

Good openers:
- "What should happen when two updates carry the same timestamp?"
- "Is the date range inclusive at both ends, or is check-out exclusive?"
- "Should duplicate IDs in the same list be deduplicated in the output?"
- "If the DIRECT channel has no rate for a date, should I skip it or flag it?"

The right answer is almost never as important as the fact that you asked.

---

## Domain vocabulary (know this cold)

| Term | Meaning |
|------|---------|
| ARI | Availability, Rates, Inventory — the feed hotels push |
| PMS | Property Management System — the hotel's core software |
| Channel manager | Aggregates and distributes ARI to multiple OTAs |
| OTA | Online Travel Agency (Booking.com, Expedia, etc.) |
| Rate plan | A named pricing rule (e.g. BAR, non-refundable, corporate) |
| Rate parity | Contract clause: OTA may not sell cheaper than direct |
| Reservation | A confirmed booking with a reservation ID |
| Reconciliation | Comparing two systems to find sync discrepancies |

**What Katanox does in one sentence:** hotels push ARI in on the left, travel buyers pull it and send bookings back on the right, contracts in the middle decide who sees what.

**Why correctness matters here:** dropped rate update → buyer sells wrong price. Undelivered reservation → guest arrives to no room. They are moving real money. Say this out loud if asked why edge cases matter.

---

## Java 25 idioms — use these naturally

```java
// Record instead of POJO
record RateUpdate(String rateId, BigDecimal price, long timestampMs) {}

// var for locals
var result = new ArrayList<String>();

// Collectors.toMap with merge (dedup, newest wins)
.collect(Collectors.toMap(
    RateUpdate::rateId,
    u -> u,
    (existing, incoming) -> incoming.timestampMs() > existing.timestampMs() ? incoming : existing
))

// stream().toList() not collect(Collectors.toList())
var sorted = list.stream().sorted(comparator).toList();

// Collectors.groupingBy
var byId = events.stream().collect(Collectors.groupingBy(BookingEvent::reservationId));

// Pattern matching switch on sealed type
return switch (booking) {
    case PartnerABooking a -> new Booking(a.partnerRef(), ...);
    case PartnerBBooking b -> new Booking(b.bookingId(), ...);
    // compiler errors if you miss a case — say this out loud
};

// LocalDate range (exclusive end — check-out is not a stay night)
checkIn.datesUntil(checkOut)  // [checkIn, checkOut)

// Instant for timestamps, not Date or long
var cutoff = now.minus(windowMinutes, ChronoUnit.MINUTES);
!e.createdAt().isBefore(cutoff) && !e.createdAt().isAfter(now)

// BigDecimal: never new BigDecimal(double)
new BigDecimal("120.00")          // correct
BigDecimal.valueOf(120L)          // correct for integers
new BigDecimal(120.0)             // WRONG — floating point imprecision
a.compareTo(b) == 0               // correct equality check
a.equals(b)                       // WRONG — 1.0 != 1.00 with equals
```

---

## Complexity — state it unprompted

The interviewer WILL ask. State it before they do.

### Big O names (say the full name out loud, not just the symbol)

| Notation | Full name | Plain English | Doubles input → runtime... |
|----------|-----------|---------------|---------------------------|
| O(1) | Constant time | Same speed regardless of input size | stays the same |
| O(log n) | Logarithmic time | Cuts the problem in half each step | grows by ~1 step |
| O(n) | Linear time | One pass through the data | doubles |
| O(n log n) | Linearithmic time | Linear with a sort on top | slightly more than doubles |
| O(n²) | Quadratic time | Nested loop over the same data | quadruples |
| O(n³) | Cubic time | Triple nested loop | 8× |
| O(2ⁿ) | Exponential time | Every subset/combination explored | catastrophic |

### Common operations in this codebase

| Operation | Complexity | Why |
|-----------|-----------|-----|
| `HashMap.get` / `HashMap.put` | O(1) average | Hash bucket lookup; O(n) worst-case (all collisions) |
| `HashSet.contains` | O(1) average | Same hash mechanism |
| `ArrayList.get(i)` | O(1) | Direct index into backing array |
| `ArrayList.contains(x)` | O(n) | Linear scan — **never use inside a loop** |
| Sort n elements (`Collections.sort`, `stream().sorted()`) | O(n log n) | TimSort |
| `Collectors.groupingBy` over n elements | O(n) | Single pass, O(1) map inserts |
| `Collectors.toMap` with merge function | O(n) | Single pass |
| Nested loop over same list | O(n²) | Always wrong at 1M+ records |
| `LocalDate.datesUntil(checkOut)` per booking × n bookings | O(n × s) | s = average stay length |
| Building a `HashSet` from n elements then k lookups | O(n + k) | Beats O(n × k) nested loop |
| `stream().filter().findFirst()` | O(n) worst | Stops at first match |
| `stream().allMatch()` / `anyMatch()` | O(n) worst | Short-circuits on first failure/match |

### How to say it in the interview

> "This runs in O(n log n) — linear time, because the sort dominates. The groupBy is a single pass, so O(n) there. Overall O(n log n). Space is O(n) for the map."

Always give **both** time **and** space complexity. Space is easy to forget and easy to score.

---

## Patterns that come up in this domain

**Dedup (newest timestamp wins):**
```java
Collectors.toMap(key, v -> v, (a, b) -> b.ts() > a.ts() ? b : a)
```

**Range merge (availability, blackout dates):**
```java
sort by start → linear sweep → extend or emit current range
```

**Event sourcing (booking state machine):**
```java
groupBy reservationId → sort each group by occurredAt → fold left with applyEvent()
```

**Set-based reconciliation (never a loop inside a loop):**
```java
var setB = systemB.stream().map(Ref::id).collect(Collectors.toSet());
systemA.stream().filter(r -> !setB.contains(r.id()))...
```

**Date expansion (overbooking, occupancy):**
```java
bookings.stream()
    .flatMap(b -> b.checkIn().datesUntil(b.checkOut()).map(date -> new Key(date, b.roomTypeId())))
    .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
```

---

## How to narrate (they score silence as a negative)

- **Before coding:** "The problem is X. The key constraint is Y. I'm going to clarify one thing first..."
- **While coding:** "I'm using a HashMap here because lookup needs to be O(1). I'll come back and add the edge case after the happy path compiles."
- **When stuck:** say "I'm thinking about..." not silence. Thirty seconds of silence feels like three minutes to the interviewer.
- **When done:** "Let me walk through what I'd test: empty input, single element, the boundary case where timestamp is equal..."

**Never:** start typing before you've said what you're building.

---

## After you say you're done — questions they will ask

1. What is the time and space complexity?
2. What breaks at 10 million records? (Memory? GC pressure? Sort cost?)
3. What would you change if this data came from Kafka instead of a list?
4. Why did you pick this data structure over [alternative]?
5. What happens if two events for the same ID have the same timestamp?
6. Is your solution thread-safe? Should it be?

Have an answer ready for all of these.

---

## Edge cases to mention proactively

These signal domain awareness, not just algorithm knowledge:

- **Empty input** — always check, always handle
- **Single element** — often breaks range/merge logic
- **All elements identical** — dedup/reconcile should handle
- **Boundary value** — is the endpoint inclusive or exclusive?
- **Clock skew** — two servers pushing updates, timestamps not reliable
- **Duplicate IDs within one list** — reconciliation, dedup
- **Missing data** — what if DIRECT has no rate? What if inventory is missing for a room type?
- **BigDecimal precision** — never use `double` for money

---

## OOP — four pillars (know the one-line definitions)

| Pillar | Definition | Example in this codebase |
|--------|-----------|--------------------------|
| **Encapsulation** | Bundle data and the methods that operate on it; hide internals | `record RateUpdate(...)` — fields are private, exposed only via accessors |
| **Abstraction** | Expose what something does, hide how | `Reconciler.reconcile()` — caller sees inputs/outputs, not the HashMap inside |
| **Inheritance** | A subtype inherits behaviour from a parent | `class PartnerABooking implements PartnerBooking` |
| **Polymorphism** | Same interface, different behaviour per type | Pattern-matching switch dispatches differently per `PartnerBooking` subtype |

---

## SOLID — one line each, then a concrete example

**S — Single Responsibility Principle**  
A class should have one reason to change.  
`RateDeduplicator` deduplicates. It does not parse, validate, or persist. If the dedup logic changes, only that class changes.

**O — Open/Closed Principle**  
Open for extension, closed for modification.  
Adding a new partner booking type (`PartnerDBooking`) means adding a new `permits` entry and a new `case` arm — the existing cases are untouched.

**L — Liskov Substitution Principle**  
A subtype must be usable wherever its parent is expected without breaking behaviour.  
Every `PartnerBooking` subtype can be passed to `normalise()` — the caller never needs to know which concrete type it received.

**I — Interface Segregation Principle**  
Don't force a class to implement methods it doesn't need.  
`StopSellChecker` takes a `List<StopSellRestriction>` — not a fat "HotelConfig" object with 30 fields it ignores.

**D — Dependency Inversion Principle**  
Depend on abstractions, not concretions.  
`BookingStateProcessor` takes `List<BookingEvent>` (an abstraction of the event stream), not a specific `KafkaConsumer` or `JdbcResultSet`.

---

## Things NOT to do

- `new BigDecimal(someDouble)` — imprecise
- `str1 == str2` for string equality
- Sorting with a raw `Comparator` when `.comparing()` chains are cleaner
- Calling `Instant.now()` inside a method that takes a `now` parameter — makes tests flaky
- Using `ArrayList.contains()` as a membership test in a loop — O(n²)
- Adding `default` to an exhaustive sealed switch — removes compiler safety
- Returning `null` when an empty `List.of()` or `Map.of()` is the right answer

---

## Questions to ask them at the end

These show you've read the JD and thought about the domain:

- "When a hotel pushes a rate update, what does the pipeline look like between their PMS and your platform? How do you handle retries?"
- "What's the most common production failure in the reservation delivery flow?"
- "How does the contracting layer decide what a buyer can see — is that rules-based, or is there a negotiation workflow?"
- "L1 mentions pairing with senior engineers. What does the first few weeks look like — is there a specific integration I'd start on?"
- "You mentioned financial regulation. What does that mean concretely for day-to-day engineering decisions?"

---

## Logistics

- **Time:** 13:00 CET, October 2nd
- **Duration:** ~1.5 hours
- **They'll share a repo link** — clone it immediately, have IntelliJ open and Maven imported
- Make sure `mvn test` runs clean before the call so you know your environment works
- IntelliJ: SDK = temurin-25, Language level = 25 (records and switch will look like errors otherwise)
- `mvn test -Dtest="Task01*"` to run a single task during the session

---

## One-line summaries of each task (in case they use similar problems)

| # | What it is | Key trick |
|---|-----------|-----------|
| 01 | Rate dedup, newest wins | `Collectors.toMap` 3-arg merge |
| 02 | Merge overlapping date ranges | Sort → linear sweep |
| 03 | Normalise partner bookings | Sealed switch; fix the V1 bug first |
| 04 | Count events in last N minutes | Inclusive boundary; injectable `now` |
| 05 | Reconcile two lists at scale | Build HashSet first, never nested loop |
| 06 | Find overbooked nights | `flatMap` + `datesUntil`; checkOut is exclusive |
| 07 | Booking state machine | GroupBy first → sort each group → fold |
| 08 | Rate parity violations | GroupBy (type, date); keep min price per channel |
