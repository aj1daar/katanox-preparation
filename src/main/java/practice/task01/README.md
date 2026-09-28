# Task 01 — Rate Update Deduplication

**Target time:** 25 minutes  
**Data volume:** 10 million rate updates per batch

A hotel channel manager streams rate updates to the platform. The channel can
push the same `rateId` many times — revisions, corrections, re-sends. Updates
arrive out of order. For each `rateId`, retain only the update that carries the
highest `timestampMs`.

## Input

`List<RateUpdate>` — unsorted, may contain many updates for the same `rateId`.

Fields: `roomTypeId`, `rateId`, `price`, `timestampMs`.

## Output

`Map<String, RateUpdate>` keyed by `rateId`, one entry per distinct `rateId`.

## Constraints

- An O(n) average-case solution is expected at the stated volume.
- Do not sort the input.

## Not specified

What should happen when two updates for the same `rateId` carry identical
`timestampMs` values is not defined here. Decide, and be ready to justify your
choice.
