package practice.task19;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task19RevenueCalculatorTest {

    private final RevenueCalculator calc = new RevenueCalculator();

    private static final LocalDate JAN1  = LocalDate.of(2025, 1, 1);
    private static final LocalDate JAN5  = LocalDate.of(2025, 1, 5);
    private static final LocalDate JAN10 = LocalDate.of(2025, 1, 10);
    private static final LocalDate JAN15 = LocalDate.of(2025, 1, 15);

    @Test
    void bookingFullyInsideWindowContributesAllNights() {
        // Jan 2 → Jan 4 (2 nights), window Jan 1–10, price 100/night → 200
        var booking = new StayBooking("B-1",
                LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 4), new BigDecimal("100.00"));
        var result = calc.calculate(List.of(booking), JAN1, JAN10);
        assertEquals(new BigDecimal("200.00"), result.totalRevenue());
        assertEquals(1, result.bookingCount());
    }

    @Test
    void bookingPartiallyOverlappingWindowContributesPartialNights() {
        // booking Jan 8 → Jan 12 (4 nights), window Jan 1–10
        // overlap: Jan 8, 9, 10 = 3 nights at 50/night → 150
        var booking = new StayBooking("B-1",
                LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 12), new BigDecimal("50.00"));
        var result = calc.calculate(List.of(booking), JAN1, JAN10);
        assertEquals(new BigDecimal("150.00"), result.totalRevenue());
        assertEquals(1, result.bookingCount());
    }

    @Test
    void bookingOutsideWindowContributesNothing() {
        var booking = new StayBooking("B-1", JAN10, JAN15, new BigDecimal("100.00"));
        var result = calc.calculate(List.of(booking), JAN1, JAN5);
        assertEquals(BigDecimal.ZERO, result.totalRevenue());
        assertEquals(0, result.bookingCount());
    }

    @Test
    void multipleBookingsSumCorrectly() {
        var b1 = new StayBooking("B-1", JAN1, JAN5, new BigDecimal("100.00")); // 4 nights
        var b2 = new StayBooking("B-2", JAN1, JAN5, new BigDecimal("200.00")); // 4 nights
        var result = calc.calculate(List.of(b1, b2), JAN1, JAN10);
        assertEquals(new BigDecimal("1200.00"), result.totalRevenue());
        assertEquals(2, result.bookingCount());
    }

    @Test
    void emptyBookingsReturnsZero() {
        var result = calc.calculate(List.of(), JAN1, JAN10);
        assertEquals(BigDecimal.ZERO, result.totalRevenue());
        assertEquals(0, result.bookingCount());
    }
}
