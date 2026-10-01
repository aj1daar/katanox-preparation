# Task 20 — Partner Timestamp Normalisation

**Target time:** 25 minutes

Partners send booking events with timestamps, but they disagree on units:
some use Unix epoch seconds, others use milliseconds. A per-partner config
map records which unit each partner uses.

## Before you write anything

Read `TimestampNormaliserV1.java`. Incident KATX-612 was filed after events
from millisecond-unit partners were stored with timestamps 1000× in the
past (year 1970 instead of 2024). Find the bug before writing
`TimestampNormaliser`.

## Your task

Implement `TimestampNormaliser.normalise(List<PartnerEvent>, Map<String,String>)`.

- If a partner is absent from the map, default to `"SECONDS"`.
- Use `Instant.ofEpochSecond(s)` for seconds, `Instant.ofEpochMilli(ms)` for millis.
