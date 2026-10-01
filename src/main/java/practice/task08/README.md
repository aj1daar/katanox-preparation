# Task 08 — Rate Parity Violation Detection

**Target time:** 30 minutes  
**Data volume:** 10 million rate entries across all channels

Hotels contractually guarantee rate parity: no OTA may sell a room cheaper
than the hotel's own direct channel for the same room type and date. Your job
is to scan the full rate feed and return every case where a non-DIRECT channel
undercuts the DIRECT price.

## Input

`List<ChannelRate>` — each entry has `roomTypeId`, `date`, `channel`
(e.g. `"DIRECT"`, `"OTA_A"`, `"OTA_B"`, `"WHOLESALER"`), and `price`.

All prices are in the same currency. Currency conversion is out of scope.

## Output

`List<ParityViolation>` sorted ascending by `date`, then `roomTypeId`, then
`offendingChannel`. Each violation carries `roomTypeId`, `date`,
`offendingChannel`, `directPrice`, and `channelPrice`.

## Constraints

- If DIRECT has no rate for a (roomTypeId, date), skip that combination —
  a violation can only exist when there is a DIRECT rate to compare against.
- A channel price **equal to** the DIRECT price is not a violation.
- An O(n) average solution is expected. A nested loop over all channels per
  date will exceed the time limit at the stated volume.
- A channel may have multiple rates for the same (roomTypeId, date) — for
  example when a correction was sent. Use the lowest price for that channel
  when checking for violations.
