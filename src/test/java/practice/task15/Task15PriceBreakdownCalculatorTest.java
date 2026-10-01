package practice.task15;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task15PriceBreakdownCalculatorTest {

    private final PriceBreakdownCalculator calc = new PriceBreakdownCalculator();

    private static DailyRate rate(int day, String price) {
        return new DailyRate(LocalDate.of(2025, 1, day), "STD", new BigDecimal(price));
    }

    @Test
    void emptyListReturnsEmpty() {
        assertTrue(calc.calculate(List.of()).isEmpty());
    }

    @Test
    void singleRateSetsAllFieldsToThatPrice() {
        var result = calc.calculate(List.of(rate(1, "120.00"))).orElseThrow();
        assertEquals(new BigDecimal("120.00"), result.total());
        assertEquals(new BigDecimal("120.00"), result.average());
        assertEquals(new BigDecimal("120.00"), result.minNight());
        assertEquals(new BigDecimal("120.00"), result.maxNight());
    }

    @Test
    void sumAndMinMaxAreCorrect() {
        var rates = List.of(rate(1, "100.00"), rate(2, "150.00"), rate(3, "200.00"));
        var result = calc.calculate(rates).orElseThrow();
        assertEquals(new BigDecimal("450.00"), result.total());
        assertEquals(new BigDecimal("100.00"), result.minNight());
        assertEquals(new BigDecimal("200.00"), result.maxNight());
    }

    @Test
    void averageIsRoundedHalfUp() {
        // 100 + 100 + 101 = 301; 301 / 3 = 100.333... → rounds to 100.33
        var rates = List.of(rate(1, "100.00"), rate(2, "100.00"), rate(3, "101.00"));
        var result = calc.calculate(rates).orElseThrow();
        assertEquals(new BigDecimal("100.33"), result.average());
    }

    @Test
    void averageRoundsUpAtHalf() {
        // 100 + 101 = 201; 201 / 2 = 100.5 → rounds up to 100.50
        var rates = List.of(rate(1, "100.00"), rate(2, "101.00"));
        var result = calc.calculate(rates).orElseThrow();
        assertEquals(new BigDecimal("100.50"), result.average());
    }
}
