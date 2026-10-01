package practice.task07;

// Java 25 features used in this task:
//   sealed interface with record subtypes, enhanced switch (pattern matching),
//   records, Collectors.groupingBy, var, stream().sorted(), stream().toList()
// Java 8 equivalent:
//   abstract class hierarchy, instanceof chains, HashMap with manual grouping,
//   POJO mutable state objects

public enum BookingStatus { ACTIVE, CANCELLED, NO_SHOW }
