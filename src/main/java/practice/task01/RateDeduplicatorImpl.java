package practice.task01;

import java.util.List;
import java.util.Map;

public class RateDeduplicatorImpl {

    /**
     * Given a list of rate updates that may arrive out of order,
     * return a map keyed by {@code rateId} containing exactly one entry per rateId:
     * the update with the highest {@code timestampMs}.
     *
     * @param updates unsorted list of updates; may be large; must not be modified
     * @return map keyed by rateId → winning RateUpdate
     */
    public Map<String, RateUpdate> deduplicate(List<RateUpdate> updates) {
        throw new UnsupportedOperationException("not implemented");
    }
}
