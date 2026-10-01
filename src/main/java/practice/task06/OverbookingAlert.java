package practice.task06;

import java.time.LocalDate;

public record OverbookingAlert(
        LocalDate date,
        String roomTypeId,
        int booked,
        int capacity
) {}
