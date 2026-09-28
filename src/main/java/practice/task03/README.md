# Task 03 — Booking Normalisation

**Target time:** 30 minutes  
**Data volume:** 500 000 bookings per ingestion batch

The platform receives bookings from three distribution partners. Each partner
uses different field names, different date types, and different currencies.
Your job is to implement `BookingNormaliser`, which maps any partner-specific
booking into a single canonical `Booking` record.

## Before you write anything

Read `BookingNormaliserV1.java`. It is production code that has been live for
two years. It has at least one defect. Find it before you start writing your
implementation — your version must not repeat the mistake.

## Input

A `PartnerBooking` — a sealed type with three permitted subtypes:

- `PartnerABooking` — European OTA; dates as ISO strings; amount in EUR
- `PartnerBBooking` — US wholesaler; dates as ISO strings; amount in USD
- `PartnerCBooking` — Regional GDS; dates already parsed; currency is explicit

## Output

A `Booking` record with fields: `reservationId`, `checkIn`, `checkOut`,
`roomCode`, `amount`, `currency`.

## Constraints

- Use a `switch` expression with pattern matching — no if-else chain.
- The compiler should tell you if you forget a partner type.
- An O(1) per-booking solution is expected; 500 K bookings should finish in
  well under a second.
