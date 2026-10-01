package practice.task16;

import java.time.LocalDate;

/**
 * Inventory state for one room type on one specific date.
 *
 * @param roomTypeId  the room type
 * @param date        the calendar date
 * @param totalRooms  physical rooms of this type in the property
 * @param bookedRooms rooms already committed to reservations on this date
 */
public record RoomInventory(
        String roomTypeId,
        LocalDate date,
        int totalRooms,
        int bookedRooms
) {
    public int available() {
        return totalRooms - bookedRooms;
    }
}
