# Task 11 — Commission Calculation

**Target time:** 25 minutes  
**Data volume:** 500 000 bookings, up to 50 channels

At month-end, calculate how much commission is owed per channel. Each channel
has a fixed percentage rate. Bookings from channels with no defined rate are
out of scope and must be excluded.

Commission per booking = `amount × (ratePercent / 100)`, rounded to 2 decimal
places (HALF_UP). Results sorted ascending by channel name.

## Not specified

Whether a channel that has a rate but zero bookings should appear in the output
(with `0.00`) is not defined here. Clarify before coding.
