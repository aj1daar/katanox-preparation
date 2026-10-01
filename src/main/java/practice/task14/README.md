# Task 14 — Rate Plan Eligibility

**Target time:** 20 minutes  
**Data volume:** up to 500 plans per property

Hotels publish many rate plans — Early Bird, Non-Refundable, Long Stay, etc.
Not every plan is available for every booking request: each plan may have a
minimum stay length and a booking-window limit.

## Your task

Implement `RatePlanSelector.findEligible(BookingRequest, List<RatePlan>)`.

A plan is eligible when:
- Stay length ≥ `minStayNights` (if set)
- `advanceBookingDays` ≤ `maxAdvanceBookingDays` (if set)

## Ambiguity — ask before coding

When multiple plans match, what order should they appear in the result?
The spec is silent. Raise this with the interviewer before writing a line.
