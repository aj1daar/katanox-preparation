package practice.task03;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Task03BookingNormaliserTest {

    private final BookingNormaliser normaliser = new BookingNormaliser();

    @Test
    void partnerAReservationIdMappedFromPartnerRef() {
        var input  = new PartnerBooking.PartnerABooking("PA-999", "2024-06-01", "2024-06-07", "DLX", "420.00");
        var result = normaliser.normalise(input);
        assertEquals("PA-999", result.reservationId());
    }

    @Test
    void partnerACheckInDateParsedCorrectly() {
        var input  = new PartnerBooking.PartnerABooking("PA-999", "2024-06-01", "2024-06-07", "DLX", "420.00");
        var result = normaliser.normalise(input);
        assertEquals(LocalDate.of(2024, 6, 1), result.checkIn());
    }

    @Test
    void partnerBCheckInDateComesFromArrivalDateField() {
        // The legacy V1 normaliser reads "arrival" — Partner B actually sends "arrivalDate".
        // PartnerBBooking.arrivalDate() is the correct field. Verify the mapping is right.
        var input  = new PartnerBooking.PartnerBBooking("PB-777", "2024-09-15", "2024-09-20", "STD", "310.00");
        var result = normaliser.normalise(input);
        assertEquals(LocalDate.of(2024, 9, 15), result.checkIn());
    }

    @Test
    void partnerBCurrencyIsUsd() {
        var input  = new PartnerBooking.PartnerBBooking("PB-777", "2024-09-15", "2024-09-20", "STD", "310.00");
        var result = normaliser.normalise(input);
        assertEquals("USD", result.currency());
    }

    @Test
    void partnerCDatesArePreservedWithoutParsing() {
        var start  = LocalDate.of(2024, 12, 24);
        var end    = LocalDate.of(2024, 12, 31);
        var input  = new PartnerBooking.PartnerCBooking("PC-123", start, end, "STE", new BigDecimal("980.00"), "CHF");
        var result = normaliser.normalise(input);
        assertEquals(start, result.checkIn());
        assertEquals(end,   result.checkOut());
    }

    @Test
    void partnerCCurrencyPreservedVerbatim() {
        var input  = new PartnerBooking.PartnerCBooking("PC-123",
                LocalDate.of(2024, 12, 24), LocalDate.of(2024, 12, 31),
                "STE", new BigDecimal("980.00"), "CHF");
        var result = normaliser.normalise(input);
        assertEquals("CHF", result.currency());
    }

    @Test
    void fiveHundredThousandMixedBookingsFinishInReasonableTime() {
        int total   = 500_000;
        var bookings = new ArrayList<PartnerBooking>(total);
        for (int i = 0; i < total; i++) {
            int mod = i % 3;
            if (mod == 0) {
                bookings.add(new PartnerBooking.PartnerABooking(
                        "PA-" + i, "2024-01-01", "2024-01-07", "DLX", "100.00"));
            } else if (mod == 1) {
                bookings.add(new PartnerBooking.PartnerBBooking(
                        "PB-" + i, "2024-02-01", "2024-02-05", "STD", "200.00"));
            } else {
                bookings.add(new PartnerBooking.PartnerCBooking(
                        "PC-" + i,
                        LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10),
                        "STE", new BigDecimal("300.00"), "EUR"));
            }
        }

        var start   = System.currentTimeMillis();
        for (var b : bookings) { normaliser.normalise(b); }
        var elapsed = System.currentTimeMillis() - start;

        assertTrue(elapsed < 3000,
                "Expected 500K normalisations in < 3s but took " + elapsed + "ms");
    }
}
