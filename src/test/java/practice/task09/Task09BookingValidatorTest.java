package practice.task09;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Task09BookingValidatorTest {

    private final BookingValidator validator = new BookingValidator();
    private static final LocalDate TODAY = LocalDate.of(2024, 6, 1);

    @Test
    void validRequestProducesNoErrors() {
        var request = new BookingRequest("G-1", "H-1", "STD",
                TODAY.plusDays(1), TODAY.plusDays(4));
        assertTrue(validator.validate(request, TODAY).isEmpty());
    }

    @Test
    void checkInInThePastProducesError() {
        var request = new BookingRequest("G-1", "H-1", "STD",
                TODAY.minusDays(1), TODAY.plusDays(2));
        var errors = validator.validate(request, TODAY);
        assertTrue(errors.stream().anyMatch(e -> e.field().equals("checkIn")));
    }

    @Test
    void checkOutBeforeCheckInProducesError() {
        var request = new BookingRequest("G-1", "H-1", "STD",
                TODAY.plusDays(5), TODAY.plusDays(2));
        var errors = validator.validate(request, TODAY);
        assertTrue(errors.stream().anyMatch(e -> e.field().equals("checkOut")));
    }

    @Test
    void blankGuestIdProducesError() {
        var request = new BookingRequest("  ", "H-1", "STD",
                TODAY.plusDays(1), TODAY.plusDays(3));
        var errors = validator.validate(request, TODAY);
        assertTrue(errors.stream().anyMatch(e -> e.field().equals("guestId")));
    }

    @Test
    void allViolationsAreReportedTogether() {
        var request = new BookingRequest(null, null, null, null, null);
        var errors = validator.validate(request, TODAY);
        assertTrue(errors.size() >= 3, "Expected at least 3 errors but got " + errors.size());
    }
}
