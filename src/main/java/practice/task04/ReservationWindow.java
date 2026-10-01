package practice.task04;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationWindow {

    /**
     * Count how many events in {@code events} were created within the window
     * [{@code now} minus {@code windowMinutes} minutes, {@code now}] inclusive.
     * Events with a {@code createdAt} strictly after {@code now} are excluded.
     *
     * @param events        list of reservation events; order is not guaranteed
     * @param windowMinutes length of the look-back window in whole minutes; must be &gt; 0
     * @param now           the reference point for "now"; pass explicitly for testability
     * @return count of events within the window
     */
    public long countInLastMinutes(List<ReservationEvent> events, long windowMinutes, Instant now) {
        long count = 0;

        for (ReservationEvent event: events){
            Instant lowerBoundary = now.minus(windowMinutes, ChronoUnit.MINUTES);
            if ((event.createdAt().isAfter(lowerBoundary) || event.createdAt().equals(lowerBoundary)) &&
                    (event.createdAt().isBefore(now) || event.createdAt().equals(now))){
                count++;
            }
        }
        return count;
    }
}
