package practice.task05;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task05ReconcilerTest {

    private final Reconciler reconciler = new Reconciler();

    @Test
    void bothEmptyProducesEmptyResult() {
        var result = reconciler.reconcile(List.of(), List.of());
        assertNotNull(result.missingFromB());
        assertNotNull(result.phantomInB());
        assertTrue(result.missingFromB().isEmpty());
        assertTrue(result.phantomInB().isEmpty());
    }

    @Test
    void allOfAAbsentFromBAreMissing() {
        var a = List.of(
                new ReservationRef("RES-001", "system-a"),
                new ReservationRef("RES-002", "system-a")
        );
        var result = reconciler.reconcile(a, List.of());
        assertEquals(2, result.missingFromB().size());
        assertTrue(result.phantomInB().isEmpty());
    }

    @Test
    void allOfBAbsentFromAArePhantom() {
        var b = List.of(
                new ReservationRef("RES-001", "system-b"),
                new ReservationRef("RES-002", "system-b")
        );
        var result = reconciler.reconcile(List.of(), b);
        assertEquals(2, result.phantomInB().size());
        assertTrue(result.missingFromB().isEmpty());
    }

    @Test
    void partialOverlapIdentifiesBothDiscrepancies() {
        var a = List.of(
                new ReservationRef("RES-001", "a"),
                new ReservationRef("RES-002", "a"),
                new ReservationRef("RES-003", "a")
        );
        var b = List.of(
                new ReservationRef("RES-002", "b"),
                new ReservationRef("RES-004", "b")
        );
        var result = reconciler.reconcile(a, b);
        // RES-001, RES-003 in A but not B
        assertEquals(2, result.missingFromB().size());
        // RES-004 in B but not A
        assertEquals(1, result.phantomInB().size());
        assertEquals("RES-001", result.missingFromB().get(0).reservationId());
        assertEquals("RES-003", result.missingFromB().get(1).reservationId());
        assertEquals("RES-004", result.phantomInB().get(0).reservationId());
    }

    @Test
    void resultsAreSortedByReservationIdAscending() {
        var a = List.of(
                new ReservationRef("RES-ZZZ", "a"),
                new ReservationRef("RES-AAA", "a")
        );
        var result = reconciler.reconcile(a, List.of());
        assertEquals("RES-AAA", result.missingFromB().get(0).reservationId());
        assertEquals("RES-ZZZ", result.missingFromB().get(1).reservationId());
    }

    @Test
    void fiveMillionRecordsPerSideFinishInReasonableTime() {
        int perSide  = 5_000_000;
        int overlap  = perSide / 2;  // half the IDs are shared

        var listA = new ArrayList<ReservationRef>(perSide);
        var listB = new ArrayList<ReservationRef>(perSide);

        for (int i = 0; i < perSide; i++) {
            listA.add(new ReservationRef("RES-A-" + i, "system-a"));
        }
        // B contains the second half of A's IDs, plus its own unique half
        for (int i = overlap; i < perSide; i++) {
            listB.add(new ReservationRef("RES-A-" + i, "system-b"));
        }
        for (int i = 0; i < overlap; i++) {
            listB.add(new ReservationRef("RES-B-" + i, "system-b"));
        }

        var start   = System.currentTimeMillis();
        var result  = reconciler.reconcile(listA, listB);
        var elapsed = System.currentTimeMillis() - start;

        assertEquals(overlap, result.missingFromB().size());   // first half of A
        assertEquals(overlap, result.phantomInB().size());     // unique B records
        assertTrue(elapsed < 10000,
                "Expected 5M-per-side reconciliation in < 10s but took " + elapsed + "ms");
    }
}
