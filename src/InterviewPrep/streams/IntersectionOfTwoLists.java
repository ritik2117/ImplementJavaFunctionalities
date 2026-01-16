package InterviewPrep.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntersectionOfTwoLists {
    public static void main(String[] args) {
//        Find the intersection of two lists using Java streams.
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 6, 7);

        // 1) filter + contains (basic): keep elements from list1 that appear in list2.
        List<Integer> intersectionBasic = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());

        // 2) filter + HashSet.contains (optimized): same logic, but O(1) lookups.
        Set<Integer> set2 = new HashSet<>(list2);
        List<Integer> intersectionWithSet = list1.stream()
                .filter(set2::contains)
                .collect(Collectors.toList());

        // 3) distinct after filtering: remove duplicates if list1 has repeats.
        List<Integer> intersectionDistinct = list1.stream()
                .filter(list2::contains)
                .distinct()
                .collect(Collectors.toList());

        // 4) collect to Set: directly produce unique intersection.
        Set<Integer> intersectionAsSet = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toSet());

        // 5) Set lookup + Set output: fastest lookup + unique results.
        Set<Integer> intersectionFastSet = list1.stream()
                .filter(set2::contains)
                .collect(Collectors.toSet());

        // 6) flatMap nested stream: models nested loops, less efficient.
        List<Integer> intersectionFlatMap = list1.stream()
                .flatMap(x -> list2.stream()
                        .filter(y -> y.equals(x))
                        .limit(1))
                .collect(Collectors.toList());

        // 7) Collectors.filtering (Java 9+): collector-style filtering.
        List<Integer> intersectionFilteringCollector = list1.stream()
                .collect(Collectors.filtering(set2::contains, Collectors.toList()));

        // 8) parallel stream: same logic, parallelized for large datasets.
        List<Integer> intersectionParallel = list1.parallelStream()
                .filter(set2::contains)
                .collect(Collectors.toList());

        // 9) preserve order + unique: keep list1 order, remove duplicates.
        List<Integer> intersectionOrderedDistinct = list1.stream()
                .filter(set2::contains)
                .distinct()
                .collect(Collectors.toList());

        // 10) reduce-based: demonstrates reduce, not recommended for clarity.
        List<Integer> intersectionReduce = list1.stream()
                .filter(set2::contains)
                .reduce(new ArrayList<>(),
                        (acc, item) -> {
                            acc.add(item);
                            return acc;
                        },
                        (left, right) -> {
                            left.addAll(right);
                            return left;
                        });

        System.out.println("Basic: " + intersectionBasic);
        System.out.println("With Set lookup: " + intersectionWithSet);
        System.out.println("Distinct: " + intersectionDistinct);
        System.out.println("As Set: " + intersectionAsSet);
        System.out.println("Fast Set output: " + intersectionFastSet);
        System.out.println("FlatMap: " + intersectionFlatMap);
        System.out.println("Filtering collector: " + intersectionFilteringCollector);
        System.out.println("Parallel: " + intersectionParallel);
        System.out.println("Ordered distinct: " + intersectionOrderedDistinct);
        System.out.println("Reduce: " + intersectionReduce);
    }
}
