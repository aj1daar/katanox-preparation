package practice.task09;

import java.time.LocalDate;
import java.util.List;

public class BookingValidator {

    /**
     * Validate a booking request against the following rules:
     * - guestId, hotelId, roomTypeId must not be null or blank
     * - checkIn must not be null and must not be in the past relative to {@code today}
     * - checkOut must not be null and must be strictly after checkIn
     *
     * All violations are collected and returned together. The method never throws.
     *
     * @param request the booking to validate
     * @param today   reference date for "today"; pass explicitly for testability
     * @return list of errors; empty if the request is valid
     */
    public List<ValidationError> validate(BookingRequest request, LocalDate today) {
        throw new UnsupportedOperationException("not implemented");
    }
}
