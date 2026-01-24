package InterviewPrep.streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FrequencyOfEachWord {
    public static void main(String[] args) {
//        Given a list of strings, find the frequency of each word using Java streams

        /*
         * ==================== PROBLEM ====================
         * Count how many times each word appears in a list.
         * Input: ["apple", "banana", "apple", "cherry", "banana", "apple"]
         * Output: {apple=3, banana=2, cherry=1}
         */

        List<String> words = Arrays.asList("apple", "banana", "apple", "cherry", "banana", "apple");
        System.out.println("Original list: " + words);
        System.out.println("\n========== DETAILED EXPLANATION OF THE MAIN APPROACH ==========\n");

        /*
         * MAIN APPROACH: groupingBy + counting
         *
         * Structure breakdown:
         * words.stream()
         *     .collect(Collectors.groupingBy(
         *         Function.identity(),      ← classifier: groups by the word itself
         *         Collectors.counting()     ← downstream: counts elements in each group
         *     ))
         *
         * INTUITION:
         * Think of it like sorting physical items into labeled boxes:
         * 1. Create a box for each unique word (apple, banana, cherry)
         * 2. Put each word into its corresponding box
         * 3. Count how many items are in each box
         *
         * HOW IT WORKS STEP-BY-STEP:
         * Processing ["apple", "banana", "apple", "cherry", "banana", "apple"]
         *
         * Step 1: "apple" → Map: {apple: 1}
         * Step 2: "banana" → Map: {apple: 1, banana: 1}
         * Step 3: "apple" → Map: {apple: 2, banana: 1}  (increment apple)
         * Step 4: "cherry" → Map: {apple: 2, banana: 1, cherry: 1}
         * Step 5: "banana" → Map: {apple: 2, banana: 2, cherry: 1}  (increment banana)
         * Step 6: "apple" → Map: {apple: 3, banana: 2, cherry: 1}  (increment apple)
         *
         * RESULT: {apple=3, banana=2, cherry=1}
         */

        // ORIGINAL APPROACH
        Map<String, Long> wordFrequency = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),        // Use the word itself as the key
                        Collectors.counting()       // Count how many times each key appears
                ));
        System.out.println("Original approach result: " + wordFrequency);

        System.out.println("\n--- UNDERSTANDING Function.identity() ---");
        /*
         * Function.identity() explained:
         *
         * What it does: Returns a function that always returns its input unchanged.
         * In simple terms: input → output (same value)
         *
         * Definition: Function.identity() = x -> x
         *
         * Why use it?
         * - It's a shorthand for: word -> word
         * - More readable and conventional
         * - Shows intent: "use the element itself as the key"
         *
         * Examples:
         * Function.identity().apply("apple") → "apple"
         * Function.identity().apply(5) → 5
         * Function.identity().apply(anyObject) → anyObject
         *
         * In our context:
         * groupingBy(Function.identity()) means "group by the word itself"
         * - "apple" becomes the key for all "apple" entries
         * - "banana" becomes the key for all "banana" entries
         */

        // These are EQUIVALENT:
        Map<String, Long> withIdentity = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> withLambda = words.stream()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        System.out.println("Using Function.identity(): " + withIdentity);
        System.out.println("Using lambda (word -> word): " + withLambda);
        System.out.println("Both are exactly the same! ✓");

        System.out.println("\n--- UNDERSTANDING Collectors.counting() ---");
        /*
         * Collectors.counting() explained:
         *
         * What it does: Counts the number of elements in a group.
         * Return type: Long (not int, to handle very large counts)
         *
         * How it works internally:
         * - Starts with count = 0
         * - For each element in the group, count++
         * - Returns the final count
         *
         * In our context:
         * After groupingBy creates groups:
         * - Group "apple": ["apple", "apple", "apple"] → counting() → 3
         * - Group "banana": ["banana", "banana"] → counting() → 2
         * - Group "cherry": ["cherry"] → counting() → 1
         *
         * Result: {apple=3, banana=2, cherry=1}
         *
         * Why Long instead of Integer?
         * - To prevent overflow with very large datasets
         * - Long can hold up to 9,223,372,036,854,775,807
         * - Integer maxes out at 2,147,483,647
         */

        System.out.println("\n========== ALL POSSIBLE APPROACHES ==========\n");

        // APPROACH 1: groupingBy + counting (RECOMMENDED - Most idiomatic)
        // Intuition: Group words into buckets, count items in each bucket
        // Pros: Clean, readable, concise, standard Java streams pattern
        // Cons: Returns Long instead of Integer
        // Time: O(n), Space: O(unique words)
        Map<String, Long> freq1 = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        System.out.println("Approach 1 (groupingBy + counting): " + freq1);

        // APPROACH 2: toMap with merge function
        // Intuition: Build map directly, when duplicate key found, add 1 to count
        // Pros: Very explicit about what's happening
        // Cons: More verbose than groupingBy
        // Time: O(n), Space: O(unique words)
        Map<String, Long> freq2 = words.stream()
                .collect(Collectors.toMap(
                        word -> word,              // key: the word itself
                        word -> 1L,                // initial value: 1
                        (count1, count2) -> count1 + count2  // merge: add counts
                ));
        System.out.println("Approach 2 (toMap with merge): " + freq2);

        // APPROACH 3: toMap with merge - Integer version
        // Intuition: Same as approach 2, but returns Integer instead of Long
        // Pros: Integer type might be more intuitive for counts
        // Cons: Can overflow for very large counts (but unlikely for word frequency)
        // Time: O(n), Space: O(unique words)
        Map<String, Integer> freq3 = words.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        word -> 1,                 // initial value: 1 (Integer)
                        Integer::sum               // merge: Integer.sum
                ));
        System.out.println("Approach 3 (toMap with Integer): " + freq3);

        // APPROACH 4: forEach with Map.merge
        // Intuition: Manually iterate and update map using merge()
        // Pros: Very clear step-by-step logic, easy to understand
        // Cons: More imperative style, not pure functional
        // Time: O(n), Space: O(unique words)
        Map<String, Integer> freq4 = new HashMap<>();
        words.stream().forEach(word ->
                freq4.merge(word, 1, Integer::sum)  // if absent, put 1; if present, add 1
        );
        System.out.println("Approach 4 (forEach + merge): " + freq4);

        // APPROACH 5: forEach with Map.compute
        // Intuition: Use compute to calculate new value based on old value
        // Pros: Shows another way to update map entries
        // Cons: More verbose than merge
        // Time: O(n), Space: O(unique words)
        Map<String, Integer> freq5 = new HashMap<>();
        words.stream().forEach(word ->
                freq5.compute(word, (key, oldValue) ->
                        oldValue == null ? 1 : oldValue + 1
                )
        );
        System.out.println("Approach 5 (forEach + compute): " + freq5);

        // APPROACH 6: forEach with getOrDefault
        // Intuition: Get current count (or 0 if absent), then add 1
        // Pros: Most traditional approach, easy for beginners
        // Cons: More verbose, not as elegant
        // Time: O(n), Space: O(unique words)
        Map<String, Integer> freq6 = new HashMap<>();
        words.stream().forEach(word ->
                freq6.put(word, freq6.getOrDefault(word, 0) + 1)
        );
        System.out.println("Approach 6 (forEach + getOrDefault): " + freq6);

        // APPROACH 7: Classic for loop (non-stream)
        // Intuition: Traditional imperative approach
        // Pros: Familiar to all programmers, no streams overhead
        // Cons: Not using streams, more verbose
        // Time: O(n), Space: O(unique words)
        Map<String, Integer> freq7 = new HashMap<>();
        for (String word : words) {
            freq7.put(word, freq7.getOrDefault(word, 0) + 1);
        }
        System.out.println("Approach 7 (classic for loop): " + freq7);

        // APPROACH 8: Using Collectors.reducing
        // Intuition: Group words, then reduce each group to a count
        // Pros: Demonstrates reducing in grouping context
        // Cons: Overkill for this problem, less readable
        // Time: O(n), Space: O(unique words)
        Map<String, Long> freq8 = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.reducing(0L, word -> 1L, Long::sum)
                ));
        System.out.println("Approach 8 (groupingBy + reducing): " + freq8);

        // APPROACH 9: Parallel stream (for very large datasets)
        // Intuition: Same as approach 1, but processes in parallel
        // Pros: Faster for millions of words
        // Cons: Overhead for small datasets, non-deterministic ordering
        // Time: O(n) parallelized, Space: O(unique words)
        Map<String, Long> freq9 = words.parallelStream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        System.out.println("Approach 9 (parallel stream): " + freq9);

        // APPROACH 10: Using frequency map with Collections.frequency (hybrid)
        // Intuition: Get unique words first, then count each using Collections.frequency
        // Pros: Shows alternative Collection utility method
        // Cons: Less efficient (O(n²) in worst case), not pure stream solution
        // Time: O(n * unique words), Space: O(unique words)
        Map<String, Integer> freq10 = words.stream()
                .distinct()  // Get unique words
                .collect(Collectors.toMap(
                        Function.identity(),
                        word -> Collections.frequency(words, word)  // Count occurrences
                ));
        System.out.println("Approach 10 (Collections.frequency): " + freq10);

        // APPROACH 11: TreeMap for sorted output
        // Intuition: Same as approach 1, but results are alphabetically sorted
        // Pros: Automatic alphabetical ordering of words
        // Cons: Slightly slower (O(n log n) due to tree operations)
        // Time: O(n log n), Space: O(unique words)
        Map<String, Long> freq11 = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        TreeMap::new,  // Use TreeMap instead of HashMap
                        Collectors.counting()
                ));
        System.out.println("Approach 11 (TreeMap for sorted): " + freq11);

        // APPROACH 12: LinkedHashMap to preserve insertion order
        // Intuition: Results ordered by first appearance of each word
        // Pros: Predictable order based on when words first appear
        // Cons: Slightly more memory overhead
        // Time: O(n), Space: O(unique words)
        Map<String, Long> freq12 = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,  // Preserves encounter order
                        Collectors.counting()
                ));
        System.out.println("Approach 12 (LinkedHashMap for insertion order): " + freq12);

        // APPROACH 13: Custom collector (advanced)
        // Intuition: Build a custom collector that accumulates into a map
        // Pros: Shows low-level mechanics of collectors
        // Cons: Overkill, not recommended for production
        // Time: O(n), Space: O(unique words)
        Map<String, Integer> freq13 = words.stream()
                .collect(
                        HashMap::new,  // supplier: create new HashMap
                        (map, word) -> map.merge(word, 1, Integer::sum),  // accumulator
                        (map1, map2) -> map2.forEach((k, v) -> map1.merge(k, v, Integer::sum))  // combiner
                );
        System.out.println("Approach 13 (custom collector): " + freq13);

        System.out.println("\n========== SPECIAL VARIATIONS ==========\n");

        // VARIATION 1: Case-insensitive frequency
        // Treats "Apple" and "apple" as the same word
        List<String> mixedCase = Arrays.asList("Apple", "banana", "APPLE", "cherry", "Banana", "apple");
        Map<String, Long> caseInsensitive = mixedCase.stream()
                .collect(Collectors.groupingBy(
                        word -> word.toLowerCase(),  // Convert to lowercase for grouping
                        Collectors.counting()
                ));
        System.out.println("Case-insensitive: " + caseInsensitive);

        // VARIATION 2: Filter then count (e.g., only count long words)
        Map<String, Long> longWords = words.stream()
                .filter(word -> word.length() > 5)  // Only words longer than 5 chars
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        System.out.println("Long words (>5 chars) frequency: " + longWords);

        // VARIATION 3: Count with minimum threshold (only words appearing >= 2 times)
        Map<String, Long> frequentWords = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)  // Only keep words with count >= 2
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Words appearing >= 2 times: " + frequentWords);

        // VARIATION 4: Find most frequent word
        Map.Entry<String, Long> mostFrequent = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())  // Find entry with max count
                .orElse(null);
        System.out.println("Most frequent word: " + mostFrequent);

        // VARIATION 5: Sort by frequency (descending)
        Map<String, Long> sortedByFreq = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Preserve sorted order
                ));
        System.out.println("Sorted by frequency (descending): " + sortedByFreq);

        System.out.println("\n========== SUMMARY & RECOMMENDATIONS ==========\n");
        System.out.println("BEST APPROACH: #1 (groupingBy + counting)");
        System.out.println("  - Most idiomatic and readable");
        System.out.println("  - Standard Java streams pattern");
        System.out.println("  - Recommended for interviews and production");
        System.out.println("\nALTERNATIVES:");
        System.out.println("  - #3 (toMap + Integer::sum): If you prefer Integer over Long");
        System.out.println("  - #4 (forEach + merge): More imperative, easier for beginners");
        System.out.println("  - #11 (TreeMap): When you need alphabetically sorted output");
        System.out.println("  - #9 (parallel): Only for very large datasets (millions of words)");
        System.out.println("\nKEY CONCEPTS:");
        System.out.println("  - Function.identity() = x -> x (returns input unchanged)");
        System.out.println("  - Collectors.counting() = counts elements in each group");
        System.out.println("  - groupingBy = creates Map<Key, Result> where Key is from classifier");
        System.out.println("  - Returns Long to handle large counts without overflow");
        System.out.println("\nCOMPLEXITY:");
        System.out.println("  - Time: O(n) for most approaches");
        System.out.println("  - Space: O(unique words)");
        System.out.println("  - TreeMap approach: O(n log n) due to sorted insertions");
    }
}
