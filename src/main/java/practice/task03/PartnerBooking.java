package practice.task03;

import java.math.BigDecimal;
import java.time.LocalDate;

public sealed interface PartnerBooking
        permits PartnerBooking.PartnerABooking,
                PartnerBooking.PartnerBBooking,
                PartnerBooking.PartnerCBooking {

    // Partner A: European OTA; dates as ISO strings; amount in EUR
    record PartnerABooking(
            String partnerRef,
            String checkIn,      // "yyyy-MM-dd"
            String checkOut,     // "yyyy-MM-dd"
            String roomCode,
            String amountEur
    ) implements PartnerBooking {}

    // Partner B: US wholesaler; dates as ISO strings; amount in USD
    record PartnerBBooking(
            String bookingId,
            String arrivalDate,    // "yyyy-MM-dd"
            String departureDate,  // "yyyy-MM-dd"
            String roomType,
            String totalUsd
    ) implements PartnerBooking {}

    // Partner C: regional GDS; dates already parsed; currency explicit
    record PartnerCBooking(
            String confirmationCode,
            LocalDate stayStart,
            LocalDate stayEnd,
            String roomCategory,
            BigDecimal totalGross,
            String currency
    ) implements PartnerBooking {}
}
