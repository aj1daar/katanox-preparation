package practice.task05;

// Java 25 features used in this task:
//   records (ReservationRef, ReconciliationResult), var, stream(), Set.of / toSet(),
//   Collectors.toSet(), stream().filter(), stream().toList(), Comparator.comparing
// Java 8 equivalent:
//   POJOs with equals/hashCode, HashSet, Iterator-based filtering, manual sort

public record ReservationRef(String reservationId, String source) {}
