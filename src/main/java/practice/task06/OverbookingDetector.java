package practice.task06;

import java.time.LocalDate;
import java.util.List;

public class OverbookingDetector {

    /**
     * Given confirmed bookings and hotel room inventory, find every (date, roomTypeId)
     * combination where the number of confirmed bookings exceeds the room capacity.
     * Results are sorted ascending by date, then by roomTypeId.
     *
     * @param bookings  confirmed bookings; may be large
     * @param inventory room type capacities; one entry per room type
     * @return list of overbooking alerts, empty if none; never null
     */
    public List<OverbookingAlert> detect(List<Booking> bookings, List<RoomInventory> inventory) {
        //roomIntentory -> id, capacity (the number of rooms that the hotel has)
        //bookings -> id, roomtypeid, checkin, checkout

        return null;
    }

    //list or overbooking alert () sorted by date then by roomTypeId
//    public record OverbookingAlert(
//            LocalDate date,
//            String roomTypeId,
//            int booked,
//            int capacity
//    ) {}
}
