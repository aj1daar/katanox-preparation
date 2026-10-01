package practice.task08;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task08ParityCheckerTest {

    private final ParityChecker checker = new ParityChecker();

    private static LocalDate d(String iso) { return LocalDate.parse(iso); }
    private static BigDecimal p(String v)  { return new BigDecimal(v); }

    @Test
    void noViolationWhenAllChannelsMatchDirect() {
        var rates = List.of(
                new ChannelRate("STD", d("2024-06-01"), "DIRECT", p("100.00")),
                new ChannelRate("STD", d("2024-06-01"), "OTA_A",  p("100.00")),
                new ChannelRate("STD", d("2024-06-01"), "OTA_B",  p("105.00"))
        );
        assertTrue(checker.findViolations(rates).isEmpty());
    }

    @Test
    void channelLowerThanDirectIsAViolation() {
        var rates = List.of(
                new ChannelRate("STD", d("2024-06-01"), "DIRECT", p("100.00")),
                new ChannelRate("STD", d("2024-06-01"), "OTA_A",  p("89.99"))
        );
        var result = checker.findViolations(rates);
        assertEquals(1, result.size());
        assertEquals("OTA_A",        result.get(0).offendingChannel());
        assertEquals(p("100.00"),    result.get(0).directPrice());
        assertEquals(p("89.99"),     result.get(0).channelPrice());
    }

    @Test
    void noViolationWhenDirectHasNoRateForThatDate() {
        // OTA_A has a rate but DIRECT does not — no baseline to compare against
        var rates = List.of(
                new ChannelRate("STD", d("2024-06-01"), "OTA_A", p("89.99"))
        );
        assertTrue(checker.findViolations(rates).isEmpty());
    }

    @Test
    void multipleChannelsProduceOneViolationEach() {
        var rates = List.of(
                new ChannelRate("STD", d("2024-06-01"), "DIRECT",     p("100.00")),
                new ChannelRate("STD", d("2024-06-01"), "OTA_A",      p("95.00")),
                new ChannelRate("STD", d("2024-06-01"), "WHOLESALER", p("80.00"))
        );
        var result = checker.findViolations(rates);
        assertEquals(2, result.size());
    }

    @Test
    void whenChannelHasMultipleRatesLowestIsUsedForComparison() {
        // OTA_A sent two rates; the lower one (90.00) is the operative price
        var rates = List.of(
                new ChannelRate("STD", d("2024-06-01"), "DIRECT", p("100.00")),
                new ChannelRate("STD", d("2024-06-01"), "OTA_A",  p("110.00")),
                new ChannelRate("STD", d("2024-06-01"), "OTA_A",  p("90.00"))
        );
        var result = checker.findViolations(rates);
        assertEquals(1, result.size());
        assertEquals(p("90.00"), result.get(0).channelPrice());
    }

    @Test
    void tenMillionRatesProcessedInReasonableTime() {
        int total = 10_000_000;
        var rates = new ArrayList<ChannelRate>(total);
        var base  = d("2024-01-01");
        String[] channels = {"DIRECT", "OTA_A", "OTA_B", "WHOLESALER"};
        String[] types    = {"STD", "DLX", "STE"};

        for (int i = 0; i < total; i++) {
            var date    = base.plusDays(i % 365);
            var channel = channels[i % channels.length];
            var type    = types[i % types.length];
            var price   = new BigDecimal(80 + (i % 50));
            rates.add(new ChannelRate(type, date, channel, price));
        }

        var start   = System.currentTimeMillis();
        var result  = checker.findViolations(rates);
        var elapsed = System.currentTimeMillis() - start;

        assertNotNull(result);
        assertTrue(elapsed < 10000,
                "Expected 10M rates in < 10s but took " + elapsed + "ms");
    }
}
