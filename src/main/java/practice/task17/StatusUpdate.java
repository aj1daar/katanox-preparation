package practice.task17;

/**
 * An instruction to change a reservation's status.
 *
 * @param reservationId the reservation to update
 * @param newStatus     the desired new status
 */
public record StatusUpdate(String reservationId, String newStatus) {}
