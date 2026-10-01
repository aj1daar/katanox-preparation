# Task 13 — Guest Email Deduplication

**Target time:** 25 minutes  
**Data volume:** 2 million guest records across partner systems

Guest records arrive from multiple hotel PMS integrations. The same person
often appears in more than one system with slightly different data but the same
email. Your job is to find those groups.

## Before you write anything

Read `GuestDeduplicatorV1.java`. A production incident (`KATX-489`) was filed
against it but the cause was never documented. Find the bug before writing
`GuestDeduplicator`.

## Output

`List<List<GuestRecord>>` — each inner list is a duplicate group (2 or more
records sharing an email). Records with null or blank email are excluded.
