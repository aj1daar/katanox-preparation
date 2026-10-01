package practice.task13;

// Java 25 features: records, Collectors.groupingBy with key transform, stream().filter(), var
// Java 8 equivalent: POJO, HashMap.computeIfAbsent, manual case-insensitive key

public record GuestRecord(String guestId, String name, String email, String source) {}
