package practice.task07;

import java.util.*;

/**
 * Original implementation shipped in 2023-Q1. Works correctly for small
 * event volumes but degrades badly as the event log grows.
 *
 * TODO: KATX-512 — performance regression reported at >100k events; rewrite
 * using groupingBy before the release-branch freeze.
 *
 * Do not delete this class. Read it and understand what it does before
 * implementing BookingStateProcessor. There is at least one behavioural
 * issue to find beyond the performance problem.
 */
public class BookingStateProcessorV1 {

    public Map<String, BookingSnapshot> process(List<BookingEvent> events) {
        // collect unique reservation IDs — requires a full scan
        Set<String> allIds = new HashSet<>();
        for (BookingEvent e : events) {
            allIds.add(e.reservationId());
        }

        Map<String, BookingSnapshot> result = new HashMap<>();

        for (String id : allIds) {
            // collect events for this ID — another full scan per ID = O(n * m)
            List<BookingEvent> forThisId = new ArrayList<>();
            for (BookingEvent e : events) {
                if (e.reservationId().equals(id)) {
                    forThisId.add(e);
                }
            }

            forThisId.sort(Comparator.comparing(BookingEvent::occurredAt));

            BookingSnapshot current = null;
            for (BookingEvent e : forThisId) {
                if (e instanceof BookingEvent.BookingCreated c) {
                    current = new BookingSnapshot(id, BookingStatus.ACTIVE,
                            c.guestName(), c.checkIn(), c.checkOut(), c.roomTypeId());

                } else if (e instanceof BookingEvent.BookingModified m) {
                    if (current != null && current.status() == BookingStatus.ACTIVE) {
                        current = new BookingSnapshot(id, BookingStatus.ACTIVE,
                                m.guestName(), m.checkIn(), m.checkOut(), current.roomTypeId());
                    }

                } else if (e instanceof BookingEvent.BookingCancelled) {
                    if (current != null) {
                        current = new BookingSnapshot(id, BookingStatus.CANCELLED,
                                current.guestName(), current.checkIn(), current.checkOut(), current.roomTypeId());
                    }

                } else if (e instanceof BookingEvent.BookingReinstated) {
                    if (current != null && current.status() == BookingStatus.CANCELLED) {
                        current = new BookingSnapshot(id, BookingStatus.ACTIVE,
                                current.guestName(), current.checkIn(), current.checkOut(), current.roomTypeId());
                    }

                } else if (e instanceof BookingEvent.BookingNoShow) {
                    if (current != null && current.status() == BookingStatus.ACTIVE) {
                        current = new BookingSnapshot(id, BookingStatus.NO_SHOW,
                                current.guestName(), current.checkIn(), current.checkOut(), current.roomTypeId());
                    }
                }
            }

            if (current != null) {
                result.put(id, current);
            }
        }

        return result;
    }
}
