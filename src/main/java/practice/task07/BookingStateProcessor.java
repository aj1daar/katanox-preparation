package practice.task07;

import java.util.List;
import java.util.Map;

public class BookingStateProcessor {

    /**
     * Given a stream of booking lifecycle events (possibly out of order, possibly
     * for many reservations), return a map keyed by reservationId containing the
     * current {@link BookingSnapshot} for each reservation.
     *
     * Events must be applied in ascending {@code occurredAt} order per reservation.
     * See {@link BookingStateProcessorV1} for the previous implementation — read it
     * before writing your own. There is a performance problem and a behavioural issue
     * to identify.
     *
     * @param events unordered event log; may be large
     * @return map of reservationId → current snapshot; no null values
     */
    public Map<String, BookingSnapshot> process(List<BookingEvent> events) {
        throw new UnsupportedOperationException("not implemented");
    }
}
