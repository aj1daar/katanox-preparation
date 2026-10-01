# SOLUTION — Task 09

```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public List<ValidationError> validate(BookingRequest r, LocalDate today) {
    var errors = new ArrayList<ValidationError>();
    if (r.guestId()    == null || r.guestId().isBlank())    errors.add(new ValidationError("guestId",    "must not be blank"));
    if (r.hotelId()    == null || r.hotelId().isBlank())    errors.add(new ValidationError("hotelId",    "must not be blank"));
    if (r.roomTypeId() == null || r.roomTypeId().isBlank()) errors.add(new ValidationError("roomTypeId", "must not be blank"));
    if (r.checkIn() == null) {
        errors.add(new ValidationError("checkIn", "must not be null"));
    } else if (r.checkIn().isBefore(today)) {
        errors.add(new ValidationError("checkIn", "must not be in the past"));
    }
    if (r.checkOut() == null) {
        errors.add(new ValidationError("checkOut", "must not be null"));
    } else if (r.checkIn() != null && !r.checkOut().isAfter(r.checkIn())) {
        errors.add(new ValidationError("checkOut", "must be after checkIn"));
    }
    return List.copyOf(errors);
}
```

The ambiguity: `!isAfter(checkIn)` catches both `checkOut == checkIn` and
`checkOut < checkIn`. If zero-night stays should be valid, use `isBefore`
instead. The candidate should ask.

## Follow-up questions

1. The method never throws — is that always the right contract for a validator?
2. What happens if the caller passes `null` as the `request` itself?
3. You return `List.copyOf` — what guarantee does that give and why does it matter?
4. Should error messages be user-facing or machine-readable codes? What changes?
5. How would you test that the error messages themselves are correct (not just that errors exist)?
