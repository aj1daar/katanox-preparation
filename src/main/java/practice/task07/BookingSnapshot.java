package practice.task07;

import java.time.LocalDate;

public record BookingSnapshot(
        String reservationId,
        BookingStatus status,
        String guestName,
        LocalDate checkIn,
        LocalDate checkOut,
        String roomTypeId
) {}
