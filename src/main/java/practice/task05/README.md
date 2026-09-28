# Task 05 — Cross-System Reconciliation

**Target time:** 35 minutes  
**Data volume:** 5 million reservation records per system

Reservations are created in system A (the source of truth) and must sync to
system B (a downstream PMS). At the end of each day a reconciliation job runs
to find discrepancies:

- **Missing from B** — a reservation exists in A but not in B (sync failed)
- **Phantom in B** — a reservation exists in B but not in A (should not exist)

## Input

- `systemA` — `List<ReservationRef>` from the source of truth
- `systemB` — `List<ReservationRef>` from the downstream system

Each `ReservationRef` has a `reservationId` (the matching key) and a `source`
(metadata only; not used for matching).

## Output

`ReconciliationResult` with:
- `missingFromB` — sorted ascending by `reservationId`
- `phantomInB`   — sorted ascending by `reservationId`

## Constraints

- O(n) average-case solution expected. A nested loop is O(n²) and will time
  out at the stated volume.
- Both inner lists must be non-null even when they are empty.

## Not specified

Each input list may contain the same `reservationId` more than once. Whether
your result should include one entry or many for a repeated ID is not defined
here. Decide, and state your reasoning.
