package InterviewPrep.streams;

import java.util.*;
import java.util.stream.Collectors;

public class KthSmallestElement {
    public static void main(String[] args) {
//        Find the kth smallest element in an array using Java streams

        /*
         * IMPORTANT: There are TWO interpretations of "kth smallest":
         * 1. kth smallest UNIQUE element (ignore duplicates)
         * 2. kth element in sorted order (with duplicates)
         *
         * Example: [1, 2, 3, 2, 4, 1, 5, 6, 5], k=3
         * - Sorted: [1, 1, 2, 2, 3, 4, 5, 5, 6]
         * - Interpretation 1 (unique): 3rd unique smallest = 3 (from [1, 2, 3, 4, 5, 6])
         * - Interpretation 2 (with duplicates): 3rd element in sorted = 2 (from [1, 1, 2, ...])
         */

        List<Integer> numbers = List.of(1, 2, 3, 2, 4, 1, 5, 6, 5);
        int k = 3;

        System.out.println("Original list: " + numbers);
        System.out.println("k = " + k);
        System.out.println("\n========== FINDING KTH SMALLEST UNIQUE ELEMENT ==========\n");

        // APPROACH 1: sorted + distinct + collect + get (Kth UNIQUE smallest)
        // Intuition: Sort the list, remove duplicates, then pick the kth element.
        // Why: distinct() ensures we only look at unique values.
        // Pros: Clear intent, easy to understand
        // Cons: Collects entire sorted list to memory, not optimal for large data
        // Time: O(n log n) for sort, Space: O(n) for intermediate list
        int kthSmallest1 = numbers.stream()
                .sorted()                          // [1, 1, 2, 2, 3, 4, 5, 5, 6]
                .distinct()                        // [1, 2, 3, 4, 5, 6]
                .collect(Collectors.toList())      // materialize to list
                .get(k - 1);                       // get 3rd element (index 2) = 3
        System.out.println("Approach 1 (sorted+distinct+collect+get): " + kthSmallest1);

        // APPROACH 2: TreeSet + toArray + get (Kth UNIQUE smallest - alternative)
        // Intuition: TreeSet automatically sorts and removes duplicates.
        // Why: TreeSet maintains sorted unique elements, perfect for this use case.
        // Pros: Cleaner than sorted+distinct, leverages TreeSet properties
        // Cons: Requires conversion to array or list to access by index
        // Time: O(n log n), Space: O(unique elements)
        Integer[] sortedUnique = new TreeSet<>(numbers).toArray(new Integer[0]);
        int kthSmallest2 = sortedUnique[k - 1];
        System.out.println("Approach 2 (TreeSet+toArray): " + kthSmallest2);

        // APPROACH 3: sorted + distinct + skip + findFirst (Kth UNIQUE smallest - streaming)
        // Intuition: Sort, remove duplicates, skip k-1 elements, take the first remaining.
        // Why: skip() is more efficient than collecting entire list if k is small.
        // Pros: More memory efficient, doesn't collect entire list
        // Cons: Still needs to sort entire list
        // Time: O(n log n), Space: O(1) for stream pipeline (excluding sort)
        int kthSmallest3 = numbers.stream()
                .sorted()                          // [1, 1, 2, 2, 3, 4, 5, 5, 6]
                .distinct()                        // [1, 2, 3, 4, 5, 6]
                .skip(k - 1)                       // skip first 2 elements [3, 4, 5, 6]
                .findFirst()                       // take first = 3
                .orElse(-1);                       // default if not found
        System.out.println("Approach 3 (sorted+distinct+skip+findFirst): " + kthSmallest3);

        // APPROACH 4: sorted + distinct + limit + reduce to last (Kth UNIQUE smallest)
        // Intuition: Take first k unique elements, then get the last one.
        // Why: Shows how to use reduce to get last element.
        // Pros: Demonstrates reduce usage
        // Cons: Less intuitive, not recommended for production
        // Time: O(n log n), Space: O(1)
        int kthSmallest4 = numbers.stream()
                .sorted()
                .distinct()
                .limit(k)                          // take first k elements [1, 2, 3]
                .reduce((first, second) -> second) // keep reducing to get last
                .orElse(-1);
        System.out.println("Approach 4 (sorted+distinct+limit+reduce): " + kthSmallest4);

        // APPROACH 5: Using stream with dropWhile (Java 9+) - custom logic
        // Intuition: Use TreeSet to get unique sorted, convert to stream, skip k-1, take first.
        // Why: Alternative streaming approach
        // Pros: Clean streaming style
        // Cons: Requires TreeSet intermediate step
        // Time: O(n log n), Space: O(unique elements)
        int kthSmallest5 = new TreeSet<>(numbers).stream()
                .skip(k - 1)
                .findFirst()
                .orElse(-1);
        System.out.println("Approach 5 (TreeSet.stream+skip): " + kthSmallest5);

        System.out.println("\n========== FINDING KTH ELEMENT IN SORTED ORDER (WITH DUPLICATES) ==========\n");

        // APPROACH 6: sorted + skip + findFirst (Kth element WITH duplicates)
        // Intuition: Sort the list (keeping duplicates), skip k-1 elements, take the next.
        // Why: This finds the kth position in sorted order, NOT kth unique.
        // Pros: Simple, efficient for this interpretation
        // Cons: Gives different result than "kth smallest unique"
        // Time: O(n log n), Space: O(1)
        // For [1, 1, 2, 2, 3, ...], k=3 gives 2 (the 3rd element)
        int kthSmallest6 = numbers.stream()
                .sorted()                          // [1, 1, 2, 2, 3, 4, 5, 5, 6]
                .skip(k - 1)                       // skip first 2: [2, 2, 3, 4, 5, 5, 6]
                .findFirst()                       // first = 2
                .orElse(-1);
        System.out.println("Approach 6 (sorted+skip+findFirst - WITH duplicates): " + kthSmallest6);

        // APPROACH 7: sorted + collect + get (Kth element WITH duplicates)
        // Intuition: Sort and collect all, then access by index.
        // Why: Most straightforward for getting kth element with duplicates.
        // Pros: Very clear
        // Cons: Stores entire sorted list
        // Time: O(n log n), Space: O(n)
        int kthSmallest7 = numbers.stream()
                .sorted()
                .collect(Collectors.toList())
                .get(k - 1);
        System.out.println("Approach 7 (sorted+collect+get - WITH duplicates): " + kthSmallest7);

        // APPROACH 8: sorted + limit + reduce to last (Kth element WITH duplicates)
        // Intuition: Take first k elements, return the last one.
        // Why: Demonstrates limit + reduce pattern
        // Pros: Slightly more efficient than collecting all
        // Cons: Not as clear as skip approach
        // Time: O(n log n), Space: O(1)
        int kthSmallest8 = numbers.stream()
                .sorted()
                .limit(k)                          // [1, 1, 2]
                .reduce((first, second) -> second) // last = 2
                .orElse(-1);
        System.out.println("Approach 8 (sorted+limit+reduce - WITH duplicates): " + kthSmallest8);

        System.out.println("\n========== OPTIMIZED APPROACHES (PARTIAL SORT) ==========\n");

        // APPROACH 9: Using PriorityQueue (Min Heap) - most efficient for kth smallest unique
        // Intuition: Use a min heap to efficiently get the kth smallest unique element.
        // Why: We only need to extract k elements, not sort the entire array.
        // Pros: More efficient when k << n, O(n + k log n) vs O(n log n)
        // Cons: Requires manual heap management, not pure streams
        // Time: O(n + k log n), Space: O(unique elements)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(new TreeSet<>(numbers));
        int kthSmallest9 = -1;
        for (int i = 0; i < k && !minHeap.isEmpty(); i++) {
            kthSmallest9 = minHeap.poll(); // extract min k times
        }
        System.out.println("Approach 9 (PriorityQueue min heap - unique): " + kthSmallest9);

        // APPROACH 10: Using PriorityQueue with all elements (WITH duplicates)
        // Intuition: Min heap with all elements, extract k times.
        // Why: Efficient for kth element with duplicates when k is small.
        // Pros: Efficient for small k
        // Cons: Not as efficient as partial sort for large k
        // Time: O(n + k log n), Space: O(n)
        PriorityQueue<Integer> minHeap2 = new PriorityQueue<>(numbers);
        int kthSmallest10 = -1;
        for (int i = 0; i < k && !minHeap2.isEmpty(); i++) {
            kthSmallest10 = minHeap2.poll();
        }
        System.out.println("Approach 10 (PriorityQueue min heap - with duplicates): " + kthSmallest10);

        // APPROACH 11: Using Max Heap of size k (for kth smallest) - QuickSelect alternative
        // Intuition: Maintain a max heap of size k. The root is the kth smallest.
        // Why: When we want kth smallest, keep the k smallest elements in a max heap.
        // Pros: O(n log k) time, very efficient when k << n
        // Cons: More complex logic, finds kth smallest element WITH duplicates
        // Time: O(n log k), Space: O(k)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int num : numbers) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // remove largest, keeping only k smallest
            }
        }
        int kthSmallest11 = maxHeap.peek(); // root of max heap = kth smallest
        System.out.println("Approach 11 (Max heap of size k): " + kthSmallest11);

        System.out.println("\n========== ADVANCED APPROACHES ==========\n");

        // APPROACH 12: Parallel stream (for very large datasets)
        // Intuition: Same as basic approaches but uses parallel processing.
        // Why: Can speed up sorting for very large datasets.
        // Pros: Faster for large data (millions of elements)
        // Cons: Overhead for small datasets, non-deterministic for equal elements
        // Time: O(n log n) but parallelized, Space: O(1)
        int kthSmallest12 = numbers.parallelStream()
                .sorted()
                .distinct()
                .skip(k - 1)
                .findFirst()
                .orElse(-1);
        System.out.println("Approach 12 (parallel stream - unique): " + kthSmallest12);

        // APPROACH 13: Using Collectors.collectingAndThen
        // Intuition: Collect to sorted set, then transform to get kth element.
        // Why: Demonstrates collectingAndThen pattern.
        // Pros: Shows functional composition
        // Cons: More verbose than simpler approaches
        // Time: O(n log n), Space: O(unique elements)
        int kthSmallest13 = numbers.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(TreeSet::new),
                        set -> {
                            Iterator<Integer> iter = set.iterator();
                            int result = -1;
                            for (int i = 0; i < k && iter.hasNext(); i++) {
                                result = iter.next();
                            }
                            return result;
                        }
                ));
        System.out.println("Approach 13 (collectingAndThen with TreeSet): " + kthSmallest13);

        // APPROACH 14: Using sorted with custom comparator (for complex objects)
        // Intuition: Shows how to extend to custom objects with comparators.
        // Why: Real-world scenarios often need custom comparison logic.
        // Pros: Extensible to any comparable type
        // Cons: Overkill for integers
        // Time: O(n log n), Space: O(1)
        int kthSmallest14 = numbers.stream()
                .sorted(Comparator.naturalOrder())
                .distinct()
                .skip(k - 1)
                .findFirst()
                .orElse(-1);
        System.out.println("Approach 14 (sorted with Comparator): " + kthSmallest14);

        System.out.println("\n========== SUMMARY & RECOMMENDATIONS ==========\n");
        System.out.println("FOR KTH SMALLEST UNIQUE:");
        System.out.println("  Best: Approach 3 (sorted+distinct+skip+findFirst) - clean & memory efficient");
        System.out.println("  Alternative: Approach 2 (TreeSet) - very clean, leverages data structure");
        System.out.println("\nFOR KTH ELEMENT WITH DUPLICATES:");
        System.out.println("  Best: Approach 6 (sorted+skip+findFirst) - simplest & most efficient");
        System.out.println("  Alternative: Approach 11 (Max heap of size k) - best for k << n");
        System.out.println("\nFOR VERY LARGE DATASETS:");
        System.out.println("  Use: Approach 11 (Max heap) or Approach 9 (Min heap with early exit)");
        System.out.println("\nKEY INSIGHT:");
        System.out.println("  sorted+distinct+skip+findFirst -> kth unique smallest");
        System.out.println("  sorted+skip+findFirst -> kth element in sorted order");
        System.out.println("\nCOMPLEXITY COMPARISON:");
        System.out.println("  Full sort: O(n log n) time, O(1) space (stream) or O(n) space (collect)");
        System.out.println("  TreeSet: O(n log n) time, O(unique) space");
        System.out.println("  Max heap (size k): O(n log k) time, O(k) space - BEST for small k");
        System.out.println("  Min heap: O(n + k log n) time, O(n) space - BEST for very small k");
    }
}
