# Task 18 — Stop-Sell Restriction Check

**Target time:** 25 minutes

Hotels can close availability to certain channels or room types — a
"stop-sell". These restrictions fire in real-time as bookings arrive.
Three restriction shapes exist: block by channel, by room type, or by
date range.

## Your task

Implement `StopSellChecker.isBlocked(BookingAttempt, List<StopSellRestriction>)`.

The restrictions use a **sealed interface** with three nested record types.
Use a **pattern-matching switch** (`switch (restriction) { case ChannelRestriction c -> ... }`)
to dispatch per subtype.

Return `true` if any single restriction blocks the attempt.

## Sealed interfaces — quick reminder

A sealed interface limits which classes can implement it. The compiler knows
every permitted subtype, so a switch can be exhaustive without a default arm.
