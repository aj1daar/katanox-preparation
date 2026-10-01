# Task 19 — Revenue by Date Range

**Target time:** 25 minutes

Finance needs a weekly revenue report: total income from confirmed bookings
for nights that fall within a given date window. A booking that spans the
window boundary partially — only the nights inside the window are counted.

## Your task

Implement `RevenueCalculator.calculate(List<StayBooking>, LocalDate from, LocalDate to)`.

- `from` and `to` are both inclusive.
- A booking that crosses the window edge contributes only its nights inside.
- `bookingCount` = distinct bookings with ≥ 1 night in the window.

## Approach hint

For each booking, compute the overlap:
```
overlapStart = max(checkIn, from)
overlapEnd   = min(checkOut, to.plusDays(1))   // checkOut is exclusive
```
If `overlapStart < overlapEnd`, the booking contributes.
