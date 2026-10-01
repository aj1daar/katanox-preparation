package practice.task11;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task11CommissionCalculatorTest {

    private final CommissionCalculator calc = new CommissionCalculator();

    @Test
    void emptyBookingsProducesNoEntries() {
        var rates = List.of(new CommissionRate("OTA_A", new BigDecimal("15.00")));
        assertTrue(calc.calculate(List.of(), rates).isEmpty());
    }

    @Test
    void singleBookingSingleChannel() {
        var booking = new BookingSummary("R-1", "OTA_A", new BigDecimal("200.00"));
        var rate    = new CommissionRate("OTA_A", new BigDecimal("10.00"));
        var result  = calc.calculate(List.of(booking), List.of(rate));
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("20.00"), result.get(0).totalCommission());
    }

    @Test
    void multipleBookingsSameChannelAreSummed() {
        var b1   = new BookingSummary("R-1", "OTA_A", new BigDecimal("100.00"));
        var b2   = new BookingSummary("R-2", "OTA_A", new BigDecimal("200.00"));
        var rate = new CommissionRate("OTA_A", new BigDecimal("10.00"));
        var result = calc.calculate(List.of(b1, b2), List.of(rate));
        assertEquals(new BigDecimal("30.00"), result.get(0).totalCommission());
    }

    @Test
    void bookingForChannelWithNoRateIsExcluded() {
        var booking = new BookingSummary("R-1", "UNKNOWN", new BigDecimal("500.00"));
        var rate    = new CommissionRate("OTA_A", new BigDecimal("10.00"));
        assertTrue(calc.calculate(List.of(booking), List.of(rate)).isEmpty());
    }

    @Test
    void resultSortedByChannelNameAscending() {
        var b1 = new BookingSummary("R-1", "OTA_B", new BigDecimal("100.00"));
        var b2 = new BookingSummary("R-2", "OTA_A", new BigDecimal("100.00"));
        var r1 = new CommissionRate("OTA_B", new BigDecimal("10.00"));
        var r2 = new CommissionRate("OTA_A", new BigDecimal("10.00"));
        var result = calc.calculate(List.of(b1, b2), List.of(r1, r2));
        assertEquals("OTA_A", result.get(0).channel());
        assertEquals("OTA_B", result.get(1).channel());
    }
}
