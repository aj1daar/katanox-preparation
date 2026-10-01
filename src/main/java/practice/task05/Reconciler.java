package practice.task05;

import java.util.*;

public class Reconciler {

    /**
     * Given two lists of reservation references, identify discrepancies:
     * <ul>
     *   <li>{@code missingFromB} — IDs present in A but absent from B (not synced)</li>
     *   <li>{@code phantomInB}   — IDs present in B but absent from A (should not exist)</li>
     * </ul>
     * Both result lists must be sorted ascending by {@code reservationId}.
     * Matching is by {@code reservationId} only; {@code source} is metadata.
     *
     * @param systemA records from the source-of-truth system
     * @param systemB records from the downstream system under audit
     * @return reconciliation result; neither inner list is null
     */
    public ReconciliationResult reconcile(List<ReservationRef> systemA, List<ReservationRef> systemB) {
        Map<String, ReservationRef> mapB = HashMap.newHashMap(systemB.size());
        for (var ref : systemB) {
            mapB.putIfAbsent(ref.reservationId(), ref);
        }

        List<ReservationRef> missingFromB = new ArrayList<>();
        Set<String> seenMissingInA = HashSet.newHashSet(Math.min(systemA.size() / 2, 1_000_000));

        for (var ref : systemA) {
            String id = ref.reservationId();
            ReservationRef removed = mapB.remove(id);

            if (removed == null && seenMissingInA.add(id)) {
                missingFromB.add(ref);
            }
        }

        List<ReservationRef> phantomInB = new ArrayList<>(mapB.values());

        Comparator<ReservationRef> idComparator = (r1, r2) -> r1.reservationId().compareTo(r2.reservationId());
        missingFromB.sort(idComparator);
        phantomInB.sort(idComparator);

        return new ReconciliationResult(missingFromB, phantomInB);
    }
}
