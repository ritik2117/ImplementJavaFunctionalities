package InterviewPrep.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Merge2SortedList {
    public static void main(String[] args) {
        
        // Test with overlapping sorted lists to demonstrate general solutions
        // Each list is sorted, but they have overlapping ranges (not sorted relative to each other)
        List<Integer> list1 = Arrays.asList(1, 3, 8, 10, 15);
        List<Integer> list2 = Arrays.asList(2, 5, 7, 12, 14);
        
        System.out.println("========== Problem: Merge two sorted lists into a single sorted list using Java streams ==========\n");
        System.out.println("List 1: " + list1);
        System.out.println("List 2: " + list2);
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 1: Using Stream.concat() + sorted() - MOST RECOMMENDED ✅
        // =====================================================================================
        // INTUITION: Stream.concat() combines two streams into one, then sorted() ensures
        // the final list is sorted. This is the most readable and straightforward approach
        // that works for any input.
        //
        // DETAILED EXPLANATION:
        // - Stream.concat(stream1, stream2): Creates a lazily concatenated stream
        // - sorted(): Sorts elements in natural order (uses TimSort, O(n log n))
        // - Works for overlapping lists: e.g., [1,3,5] and [2,4,6] → [1,2,3,4,5,6]
        // - Time Complexity: O((n+m) log(n+m)) where n, m are list sizes
        // - Space Complexity: O(n+m) for the result
        // - When to use: Default choice - simple, readable, general-purpose
        // =====================================================================================
        
        System.out.println("SOLUTION 1: Using Stream.concat() + sorted() [RECOMMENDED]");
        
        List<Integer> merged1 = Stream.concat(list1.stream(), list2.stream())
                                     .sorted()
                                     .collect(Collectors.toList());
        System.out.println("  Result: " + merged1);
        System.out.println("  Time Complexity: O((n+m) log(n+m))");
        System.out.println("  Best for: General use, most readable");
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 2: Using Stream.of() + flatMap() + sorted() - SCALABLE FOR MULTIPLE LISTS
        // =====================================================================================
        // INTUITION: Instead of concat(), use flatMap() to flatten multiple streams.
        // This is more scalable when you have more than 2 lists.
        //
        // DETAILED EXPLANATION:
        // - Stream.of(list1, list2): Creates a stream of lists (Stream<List<Integer>>)
        // - flatMap(List::stream): Flattens each list into a single stream of integers
        // - sorted(): Sorts all elements together
        // - Advantage: Easy to extend: Stream.of(list1, list2, list3, list4, ...)
        // - Same complexity: O((n+m) log(n+m))
        // - When to use: When merging 3+ lists or want flexible, scalable code
        // =====================================================================================
        
        System.out.println("SOLUTION 2: Using Stream.of() + flatMap() + sorted()");
        
        List<Integer> merged2 = Stream.of(list1, list2)
                                     .flatMap(List::stream)
                                     .sorted()
                                     .collect(Collectors.toList());
        System.out.println("  Result: " + merged2);
        
        // Demonstrating scalability with 3 lists
        List<Integer> list3 = Arrays.asList(11, 12, 13);
        List<Integer> merged2Multiple = Stream.of(list1, list2, list3)
                                             .flatMap(List::stream)
                                             .sorted()
                                             .collect(Collectors.toList());
        System.out.println("  With 3 lists: " + merged2Multiple);
        System.out.println("  Time Complexity: O((n+m+k) log(n+m+k))");
        System.out.println("  Best for: Merging 3+ lists, scalable solution");
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 3: Using Stream.concat() with Custom Comparator
        // =====================================================================================
        // INTUITION: Similar to Solution 1, but with explicit comparator for more control
        // over sorting behavior.
        //
        // DETAILED EXPLANATION:
        // - Comparator.naturalOrder(): Explicit ascending order (same as default sorted())
        // - Comparator.reverseOrder(): For descending merge
        // - Comparator.comparing(): For custom sorting logic
        // - Flexibility: Easy to add complex sorting for custom objects
        // - When to use:
        //   * When you need descending order
        //   * When sorting custom objects
        //   * When you want explicit control over comparison logic
        // =====================================================================================
        
        System.out.println("SOLUTION 3: Using Stream.concat() with Custom Comparator");
        
        // 3a: Ascending order (explicit natural order)
        List<Integer> merged3a = Stream.concat(list1.stream(), list2.stream())
                                      .sorted(Comparator.naturalOrder())
                                      .collect(Collectors.toList());
        System.out.println("  Ascending (naturalOrder): " + merged3a);
        
        // 3b: Descending order
        List<Integer> merged3b = Stream.concat(list1.stream(), list2.stream())
                                      .sorted(Comparator.reverseOrder())
                                      .collect(Collectors.toList());
        System.out.println("  Descending (reverseOrder): " + merged3b);
        
        // 3c: Custom comparator example
        List<Integer> merged3c = Stream.concat(list1.stream(), list2.stream())
                                      .sorted(Comparator.comparing(Integer::intValue))
                                      .collect(Collectors.toList());
        System.out.println("  Custom comparator: " + merged3c);
        System.out.println("  Time Complexity: O((n+m) log(n+m))");
        System.out.println("  Best for: Custom sorting, descending order, explicit control");
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 4: Using Stream.concat() + sorted() + toList() (Java 16+)
        // =====================================================================================
        // INTUITION: Modern Java provides a simpler collector with toList() instead of
        // Collectors.toList().
        //
        // DETAILED EXPLANATION:
        // - .toList(): Shorter syntax (Java 16+), returns IMMUTABLE list
        // - Collectors.toCollection(ArrayList::new): For MUTABLE list
        // - Cleaner code: Less verbose than Collectors.toList()
        // - When to use: If using Java 16+ and don't need to modify result
        // =====================================================================================
        
        System.out.println("SOLUTION 4: Using Stream.concat() + sorted() + toList() [Java 16+]");
        
        // Note: .toList() returns immutable list in Java 16+
        // For compatibility, using Collectors.toList() here
        List<Integer> merged4a = Stream.concat(list1.stream(), list2.stream())
                                      .sorted()
                                      .collect(Collectors.toList());
        System.out.println("  Mutable list (Collectors.toList): " + merged4a);
        
        // For explicit mutable list with specific implementation
        List<Integer> merged4b = Stream.concat(list1.stream(), list2.stream())
                                      .sorted()
                                      .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("  Explicit ArrayList: " + merged4b);
        System.out.println("  Time Complexity: O((n+m) log(n+m))");
        System.out.println("  Best for: Modern Java, cleaner syntax");
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 5: Using Stream.concat() + distinct() + sorted() - REMOVE DUPLICATES
        // =====================================================================================
        // INTUITION: If both lists may contain duplicates and you want unique elements only.
        //
        // DETAILED EXPLANATION:
        // - distinct(): Removes duplicate elements (uses equals() and hashCode())
        // - Order: distinct() then sorted() - removes duplicates first, then sorts
        // - Use case: When merging lists with potential overlaps: [1,2,3] + [2,3,4] → [1,2,3,4]
        // - When to use: When you need a merged set (unique elements only)
        // =====================================================================================
        
        System.out.println("SOLUTION 5: Using Stream.concat() + distinct() + sorted()");
        
        // Create lists with duplicates
        List<Integer> listWithDups1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> listWithDups2 = Arrays.asList(3, 4, 5, 6, 7);
        
        List<Integer> merged5a = Stream.concat(listWithDups1.stream(), listWithDups2.stream())
                                      .sorted()
                                      .collect(Collectors.toList());
        System.out.println("  Without distinct: " + merged5a);
        
        List<Integer> merged5b = Stream.concat(listWithDups1.stream(), listWithDups2.stream())
                                      .distinct()
                                      .sorted()
                                      .collect(Collectors.toList());
        System.out.println("  With distinct: " + merged5b);
        System.out.println("  Time Complexity: O((n+m) log(n+m))");
        System.out.println("  Best for: Removing duplicates, creating merged set");
        
        System.out.println();
        
        // =====================================================================================
        // COMPARISON SUMMARY
        // =====================================================================================
        System.out.println("========== COMPARISON SUMMARY ==========");
        System.out.println("Method                    | Time Complexity      | Scalability | Best for");
        System.out.println("--------------------------|---------------------|-------------|---------------------------");
        System.out.println("concat() + sorted()       | O((n+m) log(n+m))   | 2 lists     | ✅ General use (RECOMMENDED)");
        System.out.println("flatMap() + sorted()      | O((n+m) log(n+m))   | 3+ lists    | Multiple lists");
        System.out.println("Custom comparator         | O((n+m) log(n+m))   | 2 lists     | Custom/descending sort");
        System.out.println("toList() [Java 16+]       | O((n+m) log(n+m))   | 2 lists     | Modern Java syntax");
        System.out.println("distinct() + sorted()     | O((n+m) log(n+m))   | 2 lists     | Unique elements only");
        System.out.println("\n🎯 RECOMMENDED: Use Stream.concat() + sorted() for general purpose!");
        
        // =====================================================================================
        // PRACTICAL EXAMPLES WITH DIFFERENT SCENARIOS
        // =====================================================================================
        System.out.println("\n========== PRACTICAL EXAMPLES ==========");
        
        // Scenario 1: Non-overlapping ranges
        List<Integer> scenario1List1 = Arrays.asList(1, 2, 3);
        List<Integer> scenario1List2 = Arrays.asList(4, 5, 6);
        List<Integer> scenario1Result = Stream.concat(scenario1List1.stream(), scenario1List2.stream())
                                             .sorted()
                                             .collect(Collectors.toList());
        System.out.println("Scenario 1 (Non-overlapping): " + scenario1List1 + " + " + scenario1List2 + " = " + scenario1Result);
        
        // Scenario 2: Completely overlapping ranges
        List<Integer> scenario2List1 = Arrays.asList(1, 3, 5);
        List<Integer> scenario2List2 = Arrays.asList(2, 4, 6);
        List<Integer> scenario2Result = Stream.concat(scenario2List1.stream(), scenario2List2.stream())
                                             .sorted()
                                             .collect(Collectors.toList());
        System.out.println("Scenario 2 (Overlapping): " + scenario2List1 + " + " + scenario2List2 + " = " + scenario2Result);
        
        // Scenario 3: With duplicates
        List<Integer> scenario3List1 = Arrays.asList(1, 2, 3, 3, 4);
        List<Integer> scenario3List2 = Arrays.asList(3, 4, 5, 5, 6);
        List<Integer> scenario3Result = Stream.concat(scenario3List1.stream(), scenario3List2.stream())
                                             .distinct()
                                             .sorted()
                                             .collect(Collectors.toList());
        System.out.println("Scenario 3 (With duplicates): " + scenario3List1 + " + " + scenario3List2 + " = " + scenario3Result);
        
        // Scenario 4: Empty lists
        List<Integer> scenario4List1 = Arrays.asList();
        List<Integer> scenario4List2 = Arrays.asList(1, 2, 3);
        List<Integer> scenario4Result = Stream.concat(scenario4List1.stream(), scenario4List2.stream())
                                             .sorted()
                                             .collect(Collectors.toList());
        System.out.println("Scenario 4 (Empty list): " + scenario4List1 + " + " + scenario4List2 + " = " + scenario4Result);
    }
}
