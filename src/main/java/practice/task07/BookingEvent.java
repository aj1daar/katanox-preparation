package practice.task07;

import java.time.Instant;
import java.time.LocalDate;

public sealed interface BookingEvent
        permits BookingEvent.BookingCreated,
                BookingEvent.BookingModified,
                BookingEvent.BookingCancelled,
                BookingEvent.BookingReinstated,
                BookingEvent.BookingNoShow {

    String reservationId();
    Instant occurredAt();

    record BookingCreated(
            String reservationId,
            Instant occurredAt,
            String guestName,
            LocalDate checkIn,
            LocalDate checkOut,
            String roomTypeId
    ) implements BookingEvent {}

    record BookingModified(
            String reservationId,
            Instant occurredAt,
            String guestName,
            LocalDate checkIn,
            LocalDate checkOut
    ) implements BookingEvent {}

    record BookingCancelled(
            String reservationId,
            Instant occurredAt
    ) implements BookingEvent {}

    record BookingReinstated(
            String reservationId,
            Instant occurredAt
    ) implements BookingEvent {}

    record BookingNoShow(
            String reservationId,
            Instant occurredAt
    ) implements BookingEvent {}
}
