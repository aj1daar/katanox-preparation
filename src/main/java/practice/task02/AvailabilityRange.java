package practice.task02;

// Java 25 features used in this task:
//   record (AvailabilityRange), var, stream().sorted(), stream().toList(), Comparator.comparing
// Java 8 equivalent:
//   POJO with getters, Collections.sort with anonymous Comparator, new ArrayList<>()

import java.time.LocalDate;

public record AvailabilityRange(LocalDate start, LocalDate end) {

    public AvailabilityRange {
        if (start == null || end == null) throw new IllegalArgumentException("dates must not be null");
        if (end.isBefore(start)) throw new IllegalArgumentException("end must not be before start");
    }
}
