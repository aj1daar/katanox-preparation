package practice.task08;

import java.util.List;

public class ParityChecker {

    public static final String DIRECT = "DIRECT";

    /**
     * Find all rate parity violations: cases where a non-DIRECT channel advertises
     * a lower price than the DIRECT channel for the same roomTypeId and date.
     *
     * If DIRECT has no rate for a given (roomTypeId, date), that combination is
     * not checked — a violation requires a DIRECT rate to compare against.
     *
     * Results are sorted ascending by date, then roomTypeId, then offendingChannel.
     *
     * @param rates all channel rates; may contain multiple entries per channel per date
     * @return list of parity violations; empty if none; never null
     */
    public List<ParityViolation> findViolations(List<ChannelRate> rates) {
        throw new UnsupportedOperationException("not implemented");
    }
}
