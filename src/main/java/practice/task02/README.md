# Task 02 — Availability Range Merging

**Target time:** 30 minutes  
**Data volume:** 5 million availability slots per hotel

A hotel sends availability in date ranges. The same calendar period may arrive
as many overlapping or partially-overlapping ranges because different systems
push updates independently. You need to consolidate them into a minimal,
non-overlapping list so downstream consumers can query a single clean timeline.

## Input

`List<AvailabilityRange>` — unsorted; each range has a `start` and `end`
(`LocalDate`). The list may contain ranges that are fully contained within
another, partially overlapping, or completely separate.

## Output

`List<AvailabilityRange>` — sorted ascending by `start`, no overlaps, covering
exactly the same dates as the input union.

## Example

Input: `[Jan 1–15]`, `[Jan 10–20]`, `[Mar 1–10]`  
Output: `[Jan 1–20]`, `[Mar 1–10]`

## Constraints

- An O(n log n) solution is expected at the stated volume.
- The output list must be unmodifiable (caller will not mutate it).

## Not specified

Whether two ranges that share a boundary date — for example `[Jan 1–31]` and
`[Feb 1–28]` — should be merged into one is intentionally left open. This is
a common source of off-by-one defects in hotel date handling. Clarify before
coding.
