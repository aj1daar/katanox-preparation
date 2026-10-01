# Task 06 — Overbooking Detection

**Target time:** 30 minutes  
**Data volume:** 2 million confirmed bookings, hotel with up to 500 room types

A hotel's PMS has accepted more reservations than there are rooms for certain
dates. Your job is to scan all confirmed bookings against the hotel's declared
room inventory and produce an alert for every (date, roomTypeId) pair where the
number of booked rooms exceeds the capacity.

## Input

- `List<Booking>` — each booking has `bookingId`, `roomTypeId`, `checkIn`,
  `checkOut` (both `LocalDate`)
- `List<RoomInventory>` — one entry per room type with `roomTypeId` and
  `capacity`

## Output

`List<OverbookingAlert>` sorted ascending by `date`, then by `roomTypeId`.
Each alert carries `date`, `roomTypeId`, `booked` (actual count) and
`capacity`. Empty list if no overbooking exists.

## Constraints

- A booking with checkIn Jan 1 and checkOut Jan 3 spans some number of nights.
  How many, and which dates are included, is intentionally not specified here.
  Clarify before coding. This is a real source of production bugs in hotel
  systems.
- An O(n · d) solution is acceptable, where n = number of bookings and d =
  average stay length. Nested loops over all bookings per date are not.
- A room type absent from the inventory list should be treated as having zero
  capacity.
