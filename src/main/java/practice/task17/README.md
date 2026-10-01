# Task 17 — Batch Reservation Status Update

**Target time:** 20 minutes  
**Data volume:** up to 100,000 reservations, 10,000 updates per batch

The PMS sends periodic status patches — confirmations, cancellations,
no-shows — as batched messages. The connectivity layer applies each patch
and reports which IDs were recognised and which were unknown.

## Your task

Implement `ReservationUpdater.applyUpdates(List<Reservation>, List<StatusUpdate>)`.

Return an `UpdateResult` with:
- `updated` — IDs successfully matched
- `notFound` — IDs from the update batch with no matching reservation

## Performance hint

Iterating the reservation list for every update is O(n × m). Build a map
first for O(n + m) processing.
