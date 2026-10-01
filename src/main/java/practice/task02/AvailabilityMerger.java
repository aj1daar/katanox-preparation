package practice.task02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AvailabilityMerger {

    /**
     * Given a list of date ranges (possibly overlapping, possibly out of order),
     * return a minimal list of non-overlapping ranges that cover exactly the same
     * set of dates, sorted ascending by start date.
     *
     * @param ranges input ranges; must not be modified
     * @return sorted, non-overlapping ranges with no gaps between merged spans
     */
    public List<AvailabilityRange> merge(List<AvailabilityRange> ranges) {
        if (ranges.isEmpty()) return List.of();
        if (ranges.size()==1) return List.copyOf(ranges);

        List <AvailabilityRange> rangesSorted = ranges
                .stream()
                .sorted(Comparator.comparing(AvailabilityRange::start).thenComparing(AvailabilityRange::end)).toList();

        List<AvailabilityRange> rangesMerged = new ArrayList<>();
        AvailabilityRange current = rangesSorted.getFirst(); //initial one to compare to

        for (int i = 1; i < rangesSorted.size(); i++) {

            if (isOverlappingDates(current, rangesSorted.get(i))){
                current = mergeRanges(current, rangesSorted.get(i));
            } else {
                rangesMerged.add(current);
                current = rangesSorted.get(i);
            }
        }
        rangesMerged.add(current);


        return List.copyOf(rangesMerged);

    }

    private AvailabilityRange mergeRanges(AvailabilityRange range1, AvailabilityRange range2) {
        return new AvailabilityRange(range1.start().isBefore(range2.start()) ? range1.start() : range2.start(),
                range1.end().isAfter(range2.end()) ? range1.end() : range2.end());
    }

    private boolean isOverlappingDates(AvailabilityRange range1, AvailabilityRange range2){
        return (range1.end().isAfter(range2.start()) ||
                range1.end().isEqual(range2.start()));
    }
}
