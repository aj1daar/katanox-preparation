package practice.task03;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingNormaliser {

    /**
     * Convert any {@link PartnerBooking} variant into a canonical {@link Booking}.
     * All three partner types must be handled. See {@link BookingNormaliserV1}
     * for the previous implementation — there is at least one defect in it.
     *
     * @param booking typed partner booking (never null)
     * @return normalised booking
     */
    public Booking normalise(PartnerBooking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("PartnerBooking must not be null");
        }

        return switch(booking){
            case PartnerBooking.PartnerABooking a -> new Booking(
                    a.partnerRef(), LocalDate.parse(a.checkIn()),
                    LocalDate.parse(a.checkOut()), a.roomCode(),
                    BigDecimal.valueOf(Double.parseDouble(a.amountEur())), "EUR"
            );

            case PartnerBooking.PartnerBBooking b -> new Booking(
              b.bookingId(), LocalDate.parse(b.arrivalDate()), LocalDate.parse(b.departureDate()),
              b.roomType(), BigDecimal.valueOf(Double.parseDouble(b.totalUsd())), "USD"
            );

            case PartnerBooking.PartnerCBooking c -> new Booking(
                    c.confirmationCode(), c.stayStart(), c.stayEnd(),
                    c.roomCategory(), c.totalGross(), c.currency()
            );

            default -> throw new IllegalStateException("Unexpected value: " + booking);
        };

    }
}
