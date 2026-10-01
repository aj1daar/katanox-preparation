package practice.task16;

import java.util.List;

public class InventoryChecker {

    /**
     * Return {@code true} if and only if the requested rooms can be allocated
     * for every night of the stay.
     *
     * A night can be allocated when the available rooms on that date
     * (totalRooms − bookedRooms) is greater than or equal to {@code roomsNeeded}.
     *
     * If inventory data is missing for any night in the stay, treat availability
     * as 0 for that night (cannot allocate).
     *
     * @param request   the allocation request
     * @param inventory per-date inventory snapshots (may cover more dates/types)
     * @return true if allocation is possible for all nights
     */
    public boolean canAllocate(AllocationRequest request, List<RoomInventory> inventory) {
        throw new UnsupportedOperationException("not implemented");
    }
}
