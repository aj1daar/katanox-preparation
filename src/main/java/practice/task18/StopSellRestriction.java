package practice.task18;

import java.time.LocalDate;

/**
 * A stop-sell rule that can block booking attempts.
 *
 * <ul>
 *   <li>{@link ChannelRestriction}   — blocks all bookings from one channel</li>
 *   <li>{@link RoomTypeRestriction}  — blocks all bookings for one room type</li>
 *   <li>{@link DateRangeRestriction} — blocks all bookings whose date falls
 *                                      within a closed date range [from, to]</li>
 * </ul>
 */
public sealed interface StopSellRestriction
        permits StopSellRestriction.ChannelRestriction,
                StopSellRestriction.RoomTypeRestriction,
                StopSellRestriction.DateRangeRestriction {

    /** Blocks all attempts that arrive via the given channel. */
    record ChannelRestriction(String channel) implements StopSellRestriction {}

    /** Blocks all attempts for the given room type, regardless of channel or date. */
    record RoomTypeRestriction(String roomTypeId) implements StopSellRestriction {}

    /**
     * Blocks all attempts whose booking date falls within [from, to] (inclusive
     * on both ends).
     */
    record DateRangeRestriction(LocalDate from, LocalDate to) implements StopSellRestriction {}
}
