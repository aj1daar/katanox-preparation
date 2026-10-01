package practice.task12;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task12TopNightsFinderTest {

    private final TopNightsFinder finder = new TopNightsFinder();
    private static final LocalDate D = LocalDate.of(2024, 1, 1);

    private static NightlyCharge charge(String id, String price) {
        return new NightlyCharge(id, D, new BigDecimal(price));
    }

    @Test
    void emptyInputReturnsEmptyList() {
        assertTrue(finder.topN(List.of(), 5).isEmpty());
    }

    @Test
    void nZeroReturnsEmptyList() {
        var charges = List.of(charge("R-1", "100.00"));
        assertTrue(finder.topN(charges, 0).isEmpty());
    }

    @Test
    void nLargerThanListReturnsAllSortedDescending() {
        var charges = List.of(charge("R-1", "50.00"), charge("R-2", "200.00"), charge("R-3", "100.00"));
        var result  = finder.topN(charges, 10);
        assertEquals(3, result.size());
        assertEquals(new BigDecimal("200.00"), result.get(0).price());
    }

    @Test
    void topNReturnsMostExpensive() {
        var charges = List.of(
                charge("R-1", "50.00"),
                charge("R-2", "200.00"),
                charge("R-3", "150.00"),
                charge("R-4", "75.00")
        );
        var result = finder.topN(charges, 2);
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("200.00"), result.get(0).price());
        assertEquals(new BigDecimal("150.00"), result.get(1).price());
    }

    @Test
    void resultIsSortedByPriceDescending() {
        var charges = List.of(
                charge("R-1", "100.00"),
                charge("R-2", "300.00"),
                charge("R-3", "200.00")
        );
        var result = finder.topN(charges, 3);
        assertTrue(result.get(0).price().compareTo(result.get(1).price()) >= 0);
        assertTrue(result.get(1).price().compareTo(result.get(2).price()) >= 0);
    }
}
