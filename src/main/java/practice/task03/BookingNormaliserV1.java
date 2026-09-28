package practice.task03;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Original normaliser — added 2022-11 for initial partner integrations.
 * Works for Partner A. Partner B support was added in 2023-03.
 *
 * TODO: KATX-341 — add Partner C (regional GDS); blocked on contract finalisation
 *
 * Do not delete this class. BookingNormaliser (the new class you are building)
 * should handle all three partners correctly, including the defect in this version.
 */
public class BookingNormaliserV1 {

    /**
     * @param partnerCode one of "PA" or "PB"
     * @param raw         raw key-value pairs from the partner payload
     * @return normalised Booking, or null if partnerCode is unrecognised
     */
    public Booking normalise(String partnerCode, Map<String, String> raw) {
        if (partnerCode == null) {
            throw new IllegalArgumentException("partnerCode must not be null");
        }

        if (partnerCode.equals("PA")) {
            String ref        = raw.get("partner_ref");
            LocalDate checkIn  = LocalDate.parse(raw.get("check_in"));
            LocalDate checkOut = LocalDate.parse(raw.get("check_out"));
            String room        = raw.get("room_code");
            BigDecimal amount  = new BigDecimal(raw.get("amount_eur"));
            return new Booking(ref, checkIn, checkOut, room, amount, "EUR");
        }

        if (partnerCode.equals("PB")) {
            String ref         = raw.get("booking_id");
            LocalDate checkIn  = LocalDate.parse(raw.get("arrival"));          // ← look carefully
            LocalDate checkOut = LocalDate.parse(raw.get("departure_date"));
            String room        = raw.get("room_type");
            BigDecimal amount  = new BigDecimal(raw.get("total_usd"));
            return new Booking(ref, checkIn, checkOut, room, amount, "USD");
        }

        return null;
    }
}
