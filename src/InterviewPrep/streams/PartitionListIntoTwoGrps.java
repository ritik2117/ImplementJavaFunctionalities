package InterviewPrep.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PartitionListIntoTwoGrps {
    public static void main(String[] args) {
        /*
         * ==================== PROBLEM ====================
         * Partition (split) a list into TWO groups based on a condition.
         * One group contains elements that satisfy the condition (true).
         * Other group contains elements that don't satisfy the condition (false).
         *
         * Input: [1, 2, 3, 4, 5, 6, 7, 8, 9]
         * Condition: Is the number even? (n % 2 == 0)
         * Output:
         *   - TRUE group: [2, 4, 6, 8]  (even numbers)
         *   - FALSE group: [1, 3, 5, 7, 9]  (odd numbers)
         */

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("Original list: " + numbers);
        System.out.println("\n========== DETAILED EXPLANATION OF THE MAIN APPROACH ==========\n");

        /*
         * MAIN APPROACH: Collectors.partitioningBy()
         *
         * Structure:
         * stream.collect(Collectors.partitioningBy(predicate))
         *
         * INTUITION:
         * Think of it like a sorting machine with TWO bins:
         * - For each item, test the condition
         * - If TRUE → put in the TRUE bin
         * - If FALSE → put in the FALSE bin
         *
         * VISUAL EXAMPLE:
         * Numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9]
         * Predicate: n % 2 == 0 (is even?)
         *
         * Processing:
         * 1 → even? NO → FALSE bin: [1]
         * 2 → even? YES → TRUE bin: [2]
         * 3 → even? NO → FALSE bin: [1, 3]
         * 4 → even? YES → TRUE bin: [2, 4]
         * 5 → even? NO → FALSE bin: [1, 3, 5]
         * 6 → even? YES → TRUE bin: [2, 4, 6]
         * 7 → even? NO → FALSE bin: [1, 3, 5, 7]
         * 8 → even? YES → TRUE bin: [2, 4, 6, 8]
         * 9 → even? NO → FALSE bin: [1, 3, 5, 7, 9]
         *
         * Result: Map<Boolean, List<Integer>>
         * {
         *   true: [2, 4, 6, 8],
         *   false: [1, 3, 5, 7, 9]
         * }
         *
         * KEY DIFFERENCES: partitioningBy vs groupingBy
         * - partitioningBy: ALWAYS 2 groups (true/false), even if one is empty
         * - groupingBy: N groups (as many as unique keys exist)
         *
         * Example:
         * partitioningBy(n -> n % 2 == 0) → {true: [...], false: [...]}  (always 2 keys)
         * groupingBy(n -> n % 3) → {0: [...], 1: [...], 2: [...]}  (3 keys for mod 3)
         */

        // ORIGINAL APPROACH
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Partitioned result: " + partitioned);
        System.out.println("Even numbers (true): " + partitioned.get(true));
        System.out.println("Odd numbers (false): " + partitioned.get(false));

        System.out.println("\n========== ALL POSSIBLE APPROACHES ==========\n");

        // APPROACH 1: partitioningBy (RECOMMENDED - Most idiomatic)
        // Intuition: Built-in collector specifically designed for binary splitting
        // Pros: Clean, concise, purpose-built for this exact task
        // Cons: None - this is the best approach
        // Time: O(n), Space: O(n)
        Map<Boolean, List<Integer>> approach1 = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Approach 1 (partitioningBy): " + approach1);

        // APPROACH 2: partitioningBy with method reference
        // Intuition: Extract predicate logic to a separate method for reusability
        // Pros: More readable when predicate is complex, reusable
        // Cons: Slightly more verbose for simple predicates
        // Time: O(n), Space: O(n)
        Map<Boolean, List<Integer>> approach2 = numbers.stream()
                .collect(Collectors.partitioningBy(PartitionListIntoTwoGrps::isEven));
        System.out.println("Approach 2 (method reference): " + approach2);

        // APPROACH 3: partitioningBy with Predicate variable
        // Intuition: Store predicate in a variable for clarity or reuse
        // Pros: Easy to change predicate, good for parameterized methods
        // Cons: Slightly more lines of code
        // Time: O(n), Space: O(n)
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Map<Boolean, List<Integer>> approach3 = numbers.stream()
                .collect(Collectors.partitioningBy(isEven));
        System.out.println("Approach 3 (Predicate variable): " + approach3);

        // APPROACH 4: groupingBy with boolean classifier (equivalent to partitioningBy)
        // Intuition: Use groupingBy but with a boolean key instead of partitioningBy
        // Pros: Shows relationship between groupingBy and partitioningBy
        // Cons: Less clear intent than partitioningBy, may not guarantee both keys exist
        // Time: O(n), Space: O(n)
        /*
         * CLASSIFIER AND DOWNSTREAM BREAKDOWN:
         * 
         * Collectors.groupingBy(n -> n % 2 == 0)
         *                       ^^^^^^^^^^^^^^^
         *                       This is the CLASSIFIER
         * 
         * CLASSIFIER: n -> n % 2 == 0
         *   - Extracts the grouping key (Boolean: true or false)
         *   - Returns true for even numbers, false for odd numbers
         *   - Example: 2 → true, 3 → false, 4 → true
         * 
         * DOWNSTREAM: Collectors.toList() (IMPLICIT - not shown but used by default)
         *   - Collects elements in each group into a List
         *   - Full form would be:
         *     Collectors.groupingBy(n -> n % 2 == 0, Collectors.toList())
         *                                             ^^^^^^^^^^^^^^^^^^^^
         *                                             This is the DOWNSTREAM
         * 
         * When downstream is omitted, toList() is used by default.
         * You could replace it with other collectors:
         *   - Collectors.counting() → count elements instead of collecting them
         *   - Collectors.summingInt() → sum elements instead of collecting them
         *   - Collectors.toSet() → collect to Set instead of List
         */
        Map<Boolean, List<Integer>> approach4 = numbers.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0));
        System.out.println("Approach 4 (groupingBy boolean): " + approach4);

        // APPROACH 5: Manual partition with filter (two separate streams)
        // Intuition: Filter for true group, filter for false group separately
        // Pros: Very explicit, easy to understand
        // Cons: Iterates through list TWICE, less efficient
        // Time: O(2n) = O(n), Space: O(n)
        List<Integer> trueGroup = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        List<Integer> falseGroup = numbers.stream()
                .filter(n -> n % 2 != 0)  // negate the predicate
                .collect(Collectors.toList());
        Map<Boolean, List<Integer>> approach5 = new HashMap<>();
        approach5.put(true, trueGroup);
        approach5.put(false, falseGroup);
        System.out.println("Approach 5 (two filters): " + approach5);

        // APPROACH 6: forEach with conditional accumulation
        // Intuition: Manually iterate and add to appropriate list
        // Pros: Very explicit, easy for beginners to understand
        // Cons: More imperative, not pure functional style
        // Time: O(n), Space: O(n)
        List<Integer> evenList = new ArrayList<>();
        List<Integer> oddList = new ArrayList<>();
        numbers.stream().forEach(n -> {
            if (n % 2 == 0) {
                evenList.add(n);
            } else {
                oddList.add(n);
            }
        });
        Map<Boolean, List<Integer>> approach6 = new HashMap<>();
        approach6.put(true, evenList);
        approach6.put(false, oddList);
        System.out.println("Approach 6 (forEach conditional): " + approach6);

        // APPROACH 7: Classic for loop (non-stream)
        // Intuition: Traditional imperative approach
        // Pros: Familiar to all programmers, no streams overhead
        // Cons: More verbose, not using streams
        // Time: O(n), Space: O(n)
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();
        for (Integer n : numbers) {
            if (n % 2 == 0) {
                evens.add(n);
            } else {
                odds.add(n);
            }
        }
        Map<Boolean, List<Integer>> approach7 = new HashMap<>();
        approach7.put(true, evens);
        approach7.put(false, odds);
        System.out.println("Approach 7 (for loop): " + approach7);

        // APPROACH 8: Using toMap to build partition manually
        // Intuition: Create entries for each element, merge into map
        // Pros: Demonstrates advanced toMap usage
        // Cons: Complex, not recommended for this use case
        // Time: O(n), Space: O(n)
        Map<Boolean, List<Integer>> approach8 = numbers.stream()
                .collect(Collectors.toMap(
                        n -> n % 2 == 0,  // key: true/false
                        Arrays::asList,   // value: wrap in list
                        (list1, list2) -> {  // merge: combine lists
                            List<Integer> merged = new ArrayList<>(list1);
                            merged.addAll(list2);
                            return merged;
                        }
                ));
        System.out.println("Approach 8 (toMap manual): " + approach8);

        // APPROACH 9: Parallel stream (for large datasets)
        // Intuition: Same as approach 1, but processes in parallel
        // Pros: Faster for millions of elements
        // Cons: Overhead for small datasets
        // Time: O(n) parallelized, Space: O(n)
        Map<Boolean, List<Integer>> approach9 = numbers.parallelStream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Approach 9 (parallel stream): " + approach9);

        // APPROACH 10: partitioningBy with downstream collector (count)
        // Intuition: Partition, but instead of collecting items, count them
        // Pros: Efficient when you only need counts, not actual elements
        // Cons: Loses the actual elements
        // Time: O(n), Space: O(1)
        Map<Boolean, Long> approach10 = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.counting()  // downstream: count instead of collect
                ));
        System.out.println("Approach 10 (partition + count): " + approach10);

        // APPROACH 11: partitioningBy with downstream collector (sum)
        // Intuition: Partition, then sum each group
        // Pros: Gets aggregated result directly
        // Cons: Loses individual elements
        // Time: O(n), Space: O(1)
        Map<Boolean, Integer> approach11 = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.summingInt(Integer::intValue)  // downstream: sum
                ));
        System.out.println("Approach 11 (partition + sum): " + approach11);

        // APPROACH 12: partitioningBy with downstream collector (toSet)
        // Intuition: Partition into sets instead of lists (removes duplicates)
        // Pros: Automatic deduplication
        // Cons: Loses order
        // Time: O(n), Space: O(n)
        Map<Boolean, Set<Integer>> approach12 = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.toSet()  // downstream: collect to set
                ));
        System.out.println("Approach 12 (partition to sets): " + approach12);

        // APPROACH 13: Custom collector (advanced)
        // Intuition: Build custom collector that accumulates into map
        // Pros: Shows low-level mechanics
        // Cons: Overkill, not recommended
        // Time: O(n), Space: O(n)
        Map<Boolean, List<Integer>> approach13 = numbers.stream()
                .collect(
                        () -> {  // supplier: create new map with empty lists
                            Map<Boolean, List<Integer>> map = new HashMap<>();
                            map.put(true, new ArrayList<>());
                            map.put(false, new ArrayList<>());
                            return map;
                        },
                        (map, n) -> map.get(n % 2 == 0).add(n),  // accumulator
                        (map1, map2) -> {  // combiner
                            map1.get(true).addAll(map2.get(true));
                            map1.get(false).addAll(map2.get(false));
                        }
                );
        System.out.println("Approach 13 (custom collector): " + approach13);

        System.out.println("\n========== DIFFERENT PREDICATES & USE CASES ==========\n");

        // Example 1: Partition by size/threshold
        List<Integer> nums = Arrays.asList(5, 15, 25, 35, 45, 55);
        Map<Boolean, List<Integer>> byThreshold = nums.stream()
                .collect(Collectors.partitioningBy(n -> n >= 30));
        System.out.println("Numbers >= 30: " + byThreshold);

        // Example 2: Partition strings by length
        List<String> words = Arrays.asList("cat", "elephant", "dog", "butterfly", "ant");
        Map<Boolean, List<String>> byLength = words.stream()
                .collect(Collectors.partitioningBy(word -> word.length() > 5));
        System.out.println("Long words (>5 chars): " + byLength);

        // Example 3: Partition by multiple conditions (AND logic)
        Map<Boolean, List<Integer>> byMultipleConditions = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n > 3 && n < 7));
        System.out.println("Numbers between 3 and 7: " + byMultipleConditions);

        // Example 4: Partition by complex condition
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        Map<Boolean, List<String>> byFirstLetter = names.stream()
                .collect(Collectors.partitioningBy(name -> name.charAt(0) <= 'C'));
        System.out.println("Names starting with A-C: " + byFirstLetter);

        // Example 5: Partition with null handling
        List<Integer> withNulls = Arrays.asList(1, null, 2, 3, null, 4);
        Map<Boolean, List<Integer>> byNull = withNulls.stream()
                .collect(Collectors.partitioningBy(Objects::isNull));
        System.out.println("Partition by null: " + byNull);

        System.out.println("\n========== ADVANCED VARIATIONS ==========\n");

        // VARIATION 1: Nested partitioning (partition, then partition again)
        // Split by even/odd, then each group by > 5 or <= 5
        Map<Boolean, Map<Boolean, List<Integer>>> nestedPartition = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,  // first level: even/odd
                        Collectors.partitioningBy(n -> n > 5)  // second level: > 5 or not
                ));
        System.out.println("Nested partition (even/odd, then >5): " + nestedPartition);

        // VARIATION 2: Partition with transformation
        // Partition, but square the numbers in the result
        Map<Boolean, List<Integer>> partitionWithTransform = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.mapping(
                                n -> n * n,  // square the number
                                Collectors.toList()
                        )
                ));
        System.out.println("Partition with squared values: " + partitionWithTransform);

        // VARIATION 3: Partition with filtering
        // Partition, but only include numbers > 3
        Map<Boolean, List<Integer>> partitionWithFilter = numbers.stream()
                .filter(n -> n > 3)  // pre-filter
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Partition of numbers > 3: " + partitionWithFilter);

        // VARIATION 4: Partition with statistics
        // Partition and get statistics for each group
        Map<Boolean, IntSummaryStatistics> partitionWithStats = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.summarizingInt(Integer::intValue)
                ));
        System.out.println("Partition with statistics:");
        System.out.println("  Even: " + partitionWithStats.get(true));
        System.out.println("  Odd: " + partitionWithStats.get(false));

        // VARIATION 5: Partition with joining
        // Partition and concatenate each group as string
        Map<Boolean, String> partitionJoined = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.mapping(
                                String::valueOf,
                                Collectors.joining(", ", "[", "]")
                        )
                ));
        System.out.println("Partition joined as strings: " + partitionJoined);

        // VARIATION 6: Get only one partition
        // Sometimes you only need the TRUE or FALSE group
        List<Integer> onlyEvens = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0))
                .get(true);  // Get only the TRUE group
        System.out.println("Only evens (using partition): " + onlyEvens);
        // Note: This is less efficient than just using filter()

        // VARIATION 7: Partition with max/min
        // Find max in each partition
        Map<Boolean, Optional<Integer>> partitionMax = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.maxBy(Integer::compareTo)
                ));
        System.out.println("Max in each partition: " + partitionMax);

        System.out.println("\n========== PRACTICAL EXAMPLES ==========\n");

        // Example: Age-based partitioning
        List<Person> people = Arrays.asList(
                new Person("Alice", 17),
                new Person("Bob", 25),
                new Person("Charlie", 16),
                new Person("David", 30),
                new Person("Eve", 19)
        );
        Map<Boolean, List<Person>> adultsAndMinors = people.stream()
                .collect(Collectors.partitioningBy(p -> p.age >= 18));
        System.out.println("Adults (>=18): " + adultsAndMinors.get(true));
        System.out.println("Minors (<18): " + adultsAndMinors.get(false));

        // Example: Grade-based partitioning
        List<Student> students = Arrays.asList(
                new Student("Alice", 85),
                new Student("Bob", 45),
                new Student("Charlie", 90),
                new Student("David", 40)
        );
        Map<Boolean, List<Student>> passedAndFailed = students.stream()
                .collect(Collectors.partitioningBy(s -> s.grade >= 50));
        System.out.println("Passed (>=50): " + passedAndFailed.get(true));
        System.out.println("Failed (<50): " + passedAndFailed.get(false));

        // Example: Price-based partitioning
        List<Product> products = Arrays.asList(
                new Product("Laptop", 999.99),
                new Product("Mouse", 29.99),
                new Product("Phone", 699.99),
                new Product("Keyboard", 89.99)
        );
        Map<Boolean, List<Product>> expensiveAndCheap = products.stream()
                .collect(Collectors.partitioningBy(p -> p.price > 100));
        System.out.println("Expensive (>100): " + expensiveAndCheap.get(true));
        System.out.println("Cheap (<=100): " + expensiveAndCheap.get(false));

        System.out.println("\n========== KEY INSIGHTS ==========\n");
        System.out.println("1. partitioningBy ALWAYS creates TWO groups (true/false)");
        System.out.println("2. Even if one group is empty, both keys exist in the map");
        System.out.println("3. groupingBy creates N groups (as many as unique keys)");
        System.out.println("4. partitioningBy is optimized for binary splits");
        System.out.println("5. Can combine with downstream collectors (counting, summing, etc.)");

        System.out.println("\n========== SUMMARY & RECOMMENDATIONS ==========\n");
        System.out.println("BEST APPROACH: #1 (partitioningBy)");
        System.out.println("  - Purpose-built for binary splitting");
        System.out.println("  - Most readable and idiomatic");
        System.out.println("  - Recommended for interviews and production");
        System.out.println("\nALTERNATIVES:");
        System.out.println("  - #2 (method reference): For complex predicates");
        System.out.println("  - #3 (Predicate variable): For reusable/parameterized logic");
        System.out.println("  - #5 (two filters): When you only need one group");
        System.out.println("  - #10-12 (downstream collectors): When you need aggregations");
        System.out.println("\nWHEN NOT TO USE partitioningBy:");
        System.out.println("  - If you only need one group → use filter() instead");
        System.out.println("  - If you need more than 2 groups → use groupingBy() instead");
        System.out.println("\nCOMPLEXITY:");
        System.out.println("  - Time: O(n) for single pass");
        System.out.println("  - Space: O(n) to store both groups");
        System.out.println("  - Two-filter approach: O(2n) = O(n), but less efficient");
    }

    // Helper method for method reference
    private static boolean isEven(int n) {
        return n % 2 == 0;
    }

    // Helper classes for practical examples
    private static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }

    private static class Student {
        String name;
        int grade;

        Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return name + "(" + grade + ")";
        }
    }

    private static class Product {
        String name;
        double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + "($" + price + ")";
        }
    }
}
