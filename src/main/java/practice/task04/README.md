# Task 04 — Time-Windowed Reservation Query

**Target time:** 20 minutes  
**Data volume:** 1 million reservation events in memory

The ops dashboard needs a live metric: how many reservations were received in
the last N minutes? The method receives the event log, the window length, and a
reference timestamp for "now".

## Input

- `List<ReservationEvent>` — unordered; each event has `reservationId`,
  `hotelId`, and `createdAt` (`Instant`)
- `windowMinutes` — look-back window length in whole minutes; always positive
- `now` — the reference point; do not call `Instant.now()` inside the method

## Output

`long` — count of events whose `createdAt` falls inside `[now − windowMinutes, now]`.

## Note on testability

Hardcoding `Instant.now()` inside the implementation makes the tests
time-dependent and flaky. The `now` parameter exists precisely to avoid this.
If you are tempted to ignore it, ask why it is there.

## Constraints

- Linear scan is acceptable; O(n) is fine at the stated volume.
- Events in the future relative to `now` must be excluded.
