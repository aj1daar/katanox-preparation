# Task 09 — Booking Request Validation

**Target time:** 20 minutes  
**Data volume:** up to 100 000 requests per batch

Before a booking request reaches the hotel system it must pass a set of
business rules. Return all validation errors at once — do not short-circuit
on the first failure.

## Rules

- `guestId`, `hotelId`, `roomTypeId` must not be null or blank
- `checkIn` must not be null and must not be in the past
- `checkOut` must not be null and must be strictly after `checkIn`

## Input / Output

`BookingRequest`, `LocalDate today` → `List<ValidationError>`.  
Empty list means the request is valid. Never return null.

## Not specified

Whether a booking where `checkIn` equals `checkOut` (a zero-night stay) is
valid is intentionally left open. Clarify before coding.
