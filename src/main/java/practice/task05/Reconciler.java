package practice.task05;

import java.util.List;

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
        throw new UnsupportedOperationException("not implemented");
    }
}
