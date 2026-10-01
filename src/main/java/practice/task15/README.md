# Task 15 — Daily Price Breakdown

**Target time:** 20 minutes

OTAs display a price breakdown to guests: total stay cost, average nightly
rate, cheapest night, and most expensive night. This data feeds both the
booking widget and the confirmation email.

## Your task

Implement `PriceBreakdownCalculator.calculate(List<DailyRate>)`.

- Return `Optional.empty()` if the list is empty.
- Average must be rounded to **2 decimal places, HALF_UP**.
- Use `BigDecimal` arithmetic throughout — never `double`.

## Ambiguity — ask before coding

What rounding scale should `total` have? The spec says nothing about it.
Ask the interviewer whether to return the raw sum or also round to 2 dp.
