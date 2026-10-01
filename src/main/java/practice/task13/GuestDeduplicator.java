package practice.task13;

import java.util.List;

public class GuestDeduplicator {

    /**
     * Find groups of guest records that share the same email address.
     * Only groups with two or more records are returned.
     * Email comparison must be case-insensitive.
     * Records with null or blank email are excluded.
     *
     * Read {@link GuestDeduplicatorV1} before implementing — it has a defect
     * that caused incident KATX-489. Identify it and avoid it here.
     *
     * @param guests guest records from multiple partner systems
     * @return list of duplicate groups; each group has at least 2 records
     */
    public List<List<GuestRecord>> findDuplicates(List<GuestRecord> guests) {
        throw new UnsupportedOperationException("not implemented");
    }
}
