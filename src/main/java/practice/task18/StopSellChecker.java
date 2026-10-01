package practice.task18;

import java.util.List;

public class StopSellChecker {

    /**
     * Return {@code true} if the booking attempt is blocked by at least one
     * stop-sell restriction; {@code false} if none apply.
     *
     * Use a pattern-matching switch over {@link StopSellRestriction} subtypes.
     *
     * @param attempt      the incoming booking attempt
     * @param restrictions active stop-sell rules for this property
     * @return true if blocked
     */
    public boolean isBlocked(BookingAttempt attempt, List<StopSellRestriction> restrictions) {
        throw new UnsupportedOperationException("not implemented");
    }
}
