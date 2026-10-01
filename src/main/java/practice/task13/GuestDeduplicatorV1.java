package practice.task13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * First version — shipped 2023-Q3 for the guest-profile sync job.
 * Groups guests by email to find duplicates across partner systems.
 *
 * A production incident (KATX-489) was filed after the job missed thousands
 * of duplicates. The cause was never documented. Read carefully before
 * implementing GuestDeduplicator.
 */
public class GuestDeduplicatorV1 {

    public List<List<GuestRecord>> findDuplicates(List<GuestRecord> guests) {
        Map<String, List<GuestRecord>> byEmail = new HashMap<>();
        for (GuestRecord g : guests) {
            if (g.email() == null || g.email().isBlank()) continue;
            byEmail.computeIfAbsent(g.email(), k -> new ArrayList<>()).add(g);
        }
        List<List<GuestRecord>> result = new ArrayList<>();
        for (List<GuestRecord> group : byEmail.values()) {
            if (group.size() > 1) result.add(group);
        }
        return result;
    }
}
