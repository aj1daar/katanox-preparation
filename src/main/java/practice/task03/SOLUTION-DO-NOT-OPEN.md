# SOLUTION — Task 03

---

## The defect in BookingNormaliserV1

```java
LocalDate checkIn = LocalDate.parse(raw.get("arrival"));   // ← wrong key
```

Partner B sends `"arrival_date"` not `"arrival"`. `raw.get("arrival")` returns
`null`, and `LocalDate.parse(null)` throws `NullPointerException` at runtime.
The V1 tests passed because all test fixtures included the `"arrival"` key by
mistake (a copy-paste from the test for a different field).

A candidate who spots this unprompted and names it before coding earns full
marks for the "reads unfamiliar code" criterion.

---

## Solution

```java
import java.math.BigDecimal;
import java.time.LocalDate;

public Booking normalise(PartnerBooking booking) {
    return switch (booking) {
        case PartnerBooking.PartnerABooking a -> new Booking(
                a.partnerRef(),
                LocalDate.parse(a.checkIn()),
                LocalDate.parse(a.checkOut()),
                a.roomCode(),
                new BigDecimal(a.amountEur()),
                "EUR"
        );
        case PartnerBooking.PartnerBBooking b -> new Booking(
                b.bookingId(),
                LocalDate.parse(b.arrivalDate()),   // correct field — not "arrival"
                LocalDate.parse(b.departureDate()),
                b.roomType(),
                new BigDecimal(b.totalUsd()),
                "USD"
        );
        case PartnerBooking.PartnerCBooking c -> new Booking(
                c.confirmationCode(),
                c.stayStart(),
                c.stayEnd(),
                c.roomCategory(),
                c.totalGross(),
                c.currency()
        );
    };
}
```

The sealed type makes the switch exhaustive: the compiler errors if a new
`PartnerBooking` subtype is added and `normalise` is not updated — which is
exactly what was missing from V1.

---

## Follow-up questions

1. How does a sealed switch help with future partner additions compared to V1?
2. `new BigDecimal("420.00")` vs `new BigDecimal(420.00)` — what is the
   difference, and which one would you use here?
3. What happens if `a.checkIn()` is `"2024-13-01"` (month 13)?
   Where should that error be caught, and how?
4. Partner B amounts are in USD, Partner A in EUR. The platform must store a
   single currency. How would you handle conversion?
5. A fourth partner sends dates in `"dd/MM/yyyy"` format. Walk me through what
   changes.
6. `BookingNormaliserV1` returns `null` for unknown partners. Your version
   throws at compile time. Which is better, and why?
