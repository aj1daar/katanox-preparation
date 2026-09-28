package practice.task02;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Task02AvailabilityMergerTest {

    private final AvailabilityMerger merger = new AvailabilityMerger();

    private static LocalDate d(String iso) { return LocalDate.parse(iso); }

    @Test
    void emptyInputReturnsEmptyList() {
        var result = merger.merge(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void singleRangeReturnedUnchanged() {
        var range  = new AvailabilityRange(d("2024-01-01"), d("2024-01-31"));
        var result = merger.merge(List.of(range));
        assertEquals(1, result.size());
        assertEquals(range, result.get(0));
    }

    @Test
    void twoNonOverlappingRangesReturnedSortedAscending() {
        var later   = new AvailabilityRange(d("2024-03-01"), d("2024-03-10"));
        var earlier = new AvailabilityRange(d("2024-01-01"), d("2024-01-31"));
        var result  = merger.merge(List.of(later, earlier));
        assertEquals(2, result.size());
        assertEquals(d("2024-01-01"), result.get(0).start());
        assertEquals(d("2024-03-01"), result.get(1).start());
    }

    @Test
    void twoOverlappingRangesMergeIntoOne() {
        var a      = new AvailabilityRange(d("2024-01-01"), d("2024-01-20"));
        var b      = new AvailabilityRange(d("2024-01-10"), d("2024-01-31"));
        var result = merger.merge(List.of(a, b));
        assertEquals(1, result.size());
        assertEquals(d("2024-01-01"), result.get(0).start());
        assertEquals(d("2024-01-31"), result.get(0).end());
    }

    @Test
    void containedRangeDoesNotExtendOuterRange() {
        var outer  = new AvailabilityRange(d("2024-01-01"), d("2024-01-31"));
        var inner  = new AvailabilityRange(d("2024-01-10"), d("2024-01-20"));
        var result = merger.merge(List.of(outer, inner));
        assertEquals(1, result.size());
        assertEquals(d("2024-01-01"), result.get(0).start());
        assertEquals(d("2024-01-31"), result.get(0).end());
    }

    @Test
    void fiveMillionRangesProcessedInReasonableTime() {
        int total = 5_000_000;
        var ranges = new ArrayList<AvailabilityRange>(total);
        var rng    = new Random(7L);
        var epoch  = LocalDate.of(2024, 1, 1);

        for (int i = 0; i < total; i++) {
            int startOffset = rng.nextInt(3650);
            int length      = 1 + rng.nextInt(30);
            var start = epoch.plusDays(startOffset);
            var end   = start.plusDays(length);
            ranges.add(new AvailabilityRange(start, end));
        }

        var begin   = System.currentTimeMillis();
        var result  = merger.merge(ranges);
        var elapsed = System.currentTimeMillis() - begin;

        assertFalse(result.isEmpty());
        assertTrue(elapsed < 5000,
                "Expected 5M ranges in < 5s but took " + elapsed + "ms");
    }
}
