package practice.task17;

import java.util.List;

/**
 * Outcome of a batch status update operation.
 *
 * @param updated   reservation IDs that were successfully updated
 * @param notFound  reservation IDs from the update list that had no matching reservation
 */
public record UpdateResult(List<String> updated, List<String> notFound) {}
