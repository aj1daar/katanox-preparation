package practice.task06;

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
        throw new UnsupportedOperationException("not implemented");
    }
}
