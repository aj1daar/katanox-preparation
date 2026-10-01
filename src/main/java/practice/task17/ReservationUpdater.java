package practice.task17;

import java.util.List;

public class ReservationUpdater {

    /**
     * Apply a batch of status updates to a list of reservations.
     *
     * For each {@link StatusUpdate}, if a reservation with the matching
     * {@code reservationId} exists, record it in {@code updated}; otherwise
     * record it in {@code notFound}.
     *
     * The method must not modify the input {@code reservations} list or the
     * {@link Reservation} objects themselves — records are immutable, so return
     * a new collection of updated reservations if the caller needs them.
     * (For this task, returning only the {@link UpdateResult} summary is sufficient.)
     *
     * @param reservations current reservations
     * @param updates      status changes to apply
     * @return summary of which updates succeeded and which did not
     */
    public UpdateResult applyUpdates(List<Reservation> reservations, List<StatusUpdate> updates) {
        throw new UnsupportedOperationException("not implemented");
    }
}
