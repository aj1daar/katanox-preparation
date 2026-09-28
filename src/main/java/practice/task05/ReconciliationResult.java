package practice.task05;

import java.util.List;

public record ReconciliationResult(
        List<ReservationRef> missingFromB,  // in A but not in B
        List<ReservationRef> phantomInB     // in B but not in A
) {}
