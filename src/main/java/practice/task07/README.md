# Task 07 — Booking State Machine

**Target time:** 35 minutes  
**Data volume:** 5 million events across 1 million reservations

A hotel PMS emits a lifecycle event every time a booking changes. Events can
arrive out of order (network retries, delayed webhooks). For each reservation,
apply the events in chronological order to compute the current state.

## Before you write anything

Read `BookingStateProcessorV1.java`. It is production code. Before touching
`BookingStateProcessor`, identify:

1. The **performance problem** (what is the time complexity, and why does it
   degrade with volume?)
2. The **behavioural issue** (one edge case it handles incorrectly)

## Event types

All implement `BookingEvent` (sealed). Each carries `reservationId` and
`occurredAt` (Instant).

| Event | Effect |
|-------|--------|
| `BookingCreated` | Creates the booking in ACTIVE state |
| `BookingModified` | Updates guest name and dates (only valid on ACTIVE) |
| `BookingCancelled` | Moves to CANCELLED |
| `BookingReinstated` | Moves CANCELLED back to ACTIVE |
| `BookingNoShow` | Moves ACTIVE to NO_SHOW |

## Output

`Map<String, BookingSnapshot>` keyed by `reservationId`. No null values.

## Not specified

What should happen when `BookingModified` arrives for a reservation that is
CANCELLED or NO_SHOW is intentionally left open. Decide and state your
reasoning.

## Constraints

- An O(n log n) solution is expected. The V1's approach will time out.
- Use a sealed switch — the compiler will catch missing cases.
