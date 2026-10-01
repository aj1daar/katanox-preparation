package practice.task11;

import java.util.List;

public class CommissionCalculator {

    /**
     * For each channel that has a defined commission rate, sum the commission
     * earned across all bookings for that channel.
     * Commission per booking = amount × (ratePercent / 100), rounded to 2 dp HALF_UP.
     * Bookings for channels with no defined rate are ignored.
     * Results are sorted ascending by channel name.
     *
     * @param bookings confirmed bookings
     * @param rates    commission rates, one per channel; may not cover all channels
     * @return one entry per channel that has bookings and a rate; sorted by channel
     */
    public List<ChannelCommission> calculate(List<BookingSummary> bookings, List<CommissionRate> rates) {
        throw new UnsupportedOperationException("not implemented");
    }
}
