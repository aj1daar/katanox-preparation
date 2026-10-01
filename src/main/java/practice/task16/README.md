# Task 16 — Inventory Allocation Check

**Target time:** 20 minutes  
**Data volume:** up to 365 inventory rows per room type per hotel

Before confirming a booking, the connectivity layer must verify that enough
rooms are physically available on every night of the stay. This check is the
last guard before a reservation is written to the PMS.

## Your task

Implement `InventoryChecker.canAllocate(AllocationRequest, List<RoomInventory>)`.

- Check each night from `checkIn` (inclusive) to `checkOut` (exclusive).
- If inventory is missing for a night, treat it as 0 available.
- Return `true` only if every night passes.

## Hint

`LocalDate.datesUntil(checkOut)` produces the stream of nights to check.
Build a lookup map first to avoid O(n²) behaviour.
