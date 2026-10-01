package practice.task14;

import java.util.List;

public class RatePlanSelector {

    /**
     * Return every rate plan that the booking request is eligible for.
     *
     * A plan is eligible when ALL of the following hold:
     * <ul>
     *   <li>The stay is at least {@code minStayNights} long
     *       (ignore if {@code minStayNights} is null).</li>
     *   <li>The booking is made no more than {@code maxAdvanceBookingDays} days
     *       in advance (ignore if {@code maxAdvanceBookingDays} is null).</li>
     * </ul>
     *
     * <strong>Ambiguity to raise with the interviewer:</strong>
     * When several plans are eligible, should they be returned in any order,
     * sorted cheapest-first, or sorted by planId? The tests do not enforce order.
     *
     * @param request the guest's booking request
     * @param plans   all rate plans available for the room type
     * @return eligible plans (may be empty, never null)
     */
    public List<RatePlan> findEligible(BookingRequest request, List<RatePlan> plans) {
        throw new UnsupportedOperationException("not implemented");
    }
}
