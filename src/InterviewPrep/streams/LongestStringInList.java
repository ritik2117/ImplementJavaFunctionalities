package InterviewPrep.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROBLEM: Find the longest string in a list of strings using Java streams
 *
 * WHAT IS reduce()?
 * - reduce() is a terminal operation that combines all elements of a stream into a single result
 * - It repeatedly applies a binary operation (takes 2 inputs, returns 1 output)
 * - Purpose: Aggregate/combine stream elements, reduce many values to one
 *
 * HOW reduce() WORKS INTERNALLY:
 * Step-by-step execution for: .reduce((a, b) -> a.length() >= b.length() ? a : b)
 *
 * Step 1: a = "apple" (5),     b = "banana" (6)      -> 5 >= 6? NO  -> Result: "banana"
 * Step 2: a = "banana" (6),    b = "cherry" (6)      -> 6 >= 6? YES -> Result: "banana"
 * Step 3: a = "banana" (6),    b = "date" (4)        -> 6 >= 4? YES -> Result: "banana"
 * Step 4: a = "banana" (6),    b = "elderberry" (10) -> 6 >= 10? NO -> Result: "elderberry"
 * Final: "elderberry"
 *
 * WHEN TO USE reduce():
 * ✅ Combine all elements into ONE result (sum, product, max/min, concatenation)
 * ✅ Apply a binary operation repeatedly
 * ✅ Custom aggregation logic
 * ❌ DON'T use when: just transforming (use map), filtering, or simple max/min (use max())
 *
 * PERFORMANCE COMPARISON:
 * - max() + Comparator:        O(n) time, O(1) space - BEST READABILITY ⭐⭐⭐⭐⭐
 * - reduce():                  O(n) time, O(1) space - GOOD ⭐⭐⭐⭐
 * - sorted() + findFirst():    O(n log n) time, O(n) space - INEFFICIENT ⭐⭐⭐
 * - collect() + maxBy():       O(n) time, O(1) space - VERBOSE ⭐⭐⭐⭐
 */
public class LongestStringInList {
    public static void main(String[] args) {
        List<String> strings = List.of("apple", "banana", "cherry", "date", "elderberry");

        System.out.println("=== APPROACH 1: Using reduce() ===");
        String longest1 = strings.stream()
                .reduce((a, b) -> a.length() >= b.length() ? a : b)
                .orElse(null);
        System.out.println("Longest (reduce): " + longest1);

        /*
         * ==================================================================================
         * DEEP DIVE: max(Comparator.comparingInt(String::length))
         * ==================================================================================
         *
         * COMMON CONFUSION ADDRESSED FIRST:
         * ----------------------------------
         * Q: "comparingInt() takes only 1 argument, but comparison needs 2 values. How?"
         * A: TWO SEPARATE PHASES!
         *
         * Phase 1: CREATE the Comparator (happens ONCE)
         *   Comparator.comparingInt(String::length)  ← Takes 1 function
         *   Returns: A Comparator object
         *
         * Phase 2: USE the Comparator (happens MANY times)
         *   comparator.compare(string1, string2)  ← Takes 2 strings
         *   Inside: Applies the function to BOTH strings and compares results
         *
         * ==================================================================================
         *
         * ANSWERING YOUR SPECIFIC QUESTIONS:
         * ----------------------------------
         * Q1: Why does comparingInt need only 1 argument?
         * A1: Because it's a FACTORY METHOD that creates a Comparator. It needs to know:
         *     "What property should I extract for comparison?" → Answer: String::length
         *     The actual comparison of 2 values happens LATER when compare() is called.
         *
         * Q2: Are we passing the length of one string or all strings?
         * A2: Neither! We're passing a RECIPE (a function) that says:
         *     "When you need to compare any two strings, extract their lengths and compare."
         *     The function String::length is applied to EACH string individually during comparisons.
         *
         * ==================================================================================
         *
         * VISUAL BREAKDOWN:
         * -----------------
         *     Comparator.comparingInt(String::length)
         *             ↓
         *     Creates a Comparator object
         *             ↓
         *     That Comparator has a compare() method
         *             ↓
         *     compare(String s1, String s2) {
         *         int len1 = String::length applied to s1  ← Applies function to FIRST string
         *         int len2 = String::length applied to s2  ← Applies function to SECOND string
         *         return Integer.compare(len1, len2)        ← Compares the TWO extracted values
         *     }
         *
         * ==================================================================================
         *
         * THE PATTERN:
         * ------------
         * Generic pattern:
         *   Comparator.comparingInt(extractionFunction)
         *
         * What it means:
         *   "Create a comparator that, when given 2 objects,
         *    will apply extractionFunction to BOTH objects
         *    and compare the resulting int values"
         *
         * For our code:
         *   Comparator.comparingInt(String::length)
         *   means:
         *   "Create a comparator that, when given 2 strings,
         *    will get the length of BOTH strings
         *    and compare those 2 lengths"
         *
         * Summary Table:
         *   What                    | Arguments | When
         *   ------------------------|-----------|---------------------------
         *   comparingInt(...)       | 1 function| Creation time (once)
         *   compare(...)            | 2 objects | Execution time (many times)
         *   Extraction function     | 1 object  | Inside compare() (twice per comparison)
         *
         * ==================================================================================
         *
         * LAYER 1: Method Reference String::length
         * -----------------------------------------
         * String::length is a method reference equivalent to:
         *   (String s) -> s.length()
         * It's a function that takes ONE String and returns an int (the length).
         * This function will be applied to each string separately during comparison.
         *
         * ==================================================================================
         *
         * LAYER 2: Comparator.comparingInt() - The Factory Method
         * --------------------------------------------------------
         * Source code (simplified):
         *   public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
         *       return (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c1),
         *                                          keyExtractor.applyAsInt(c2));
         *   }
         *
         * What it does:
         *   1. Takes ONE key extractor function (String::length)
         *   2. Returns a Comparator that has a compare(T o1, T o2) method
         *   3. That compare() method:
         *      - Applies the extractor to o1 → gets int value
         *      - Applies the extractor to o2 → gets int value
         *      - Compares those TWO int values using Integer.compare()
         *
         * Expanded version:
         *   Comparator<String> lengthComparator = new Comparator<String>() {
         *       @Override
         *       public int compare(String s1, String s2) {  // NOW takes 2 arguments!
         *           int length1 = s1.length();  // Apply extraction to FIRST string
         *           int length2 = s2.length();  // Apply extraction to SECOND string
         *           return Integer.compare(length1, length2);  // Compare the 2 ints
         *       }
         *   };
         *
         * Comparator.compare() returns:
         *   - NEGATIVE if s1.length() < s2.length() → s1 is "smaller"
         *   - ZERO     if s1.length() == s2.length() → equal
         *   - POSITIVE if s1.length() > s2.length() → s1 is "larger"
         *
         * ==================================================================================
         *
         * COMPLETE EXAMPLE WITH MANUAL STEPS:
         * ------------------------------------
         * // Step 1: Create the comparator (using the extraction function)
         * Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
         * // This says: "When comparing, extract length from each string"
         *
         * // Step 2: Stream's max() internally does this:
         * String current = "apple";
         *
         * // Compare with "banana"
         * int result = lengthComparator.compare(current, "banana");
         * // Inside compare():
         * //   - Extract "apple".length() → 5
         * //   - Extract "banana".length() → 6
         * //   - Compare 5 vs 6 → returns -1
         * // Since -1 (negative), "banana" becomes the new current
         *
         * current = "banana";
         *
         * // Compare with "cherry"
         * result = lengthComparator.compare(current, "cherry");
         * // Inside compare():
         * //   - Extract "banana".length() → 6
         * //   - Extract "cherry".length() → 6
         * //   - Compare 6 vs 6 → returns 0
         * // Since 0 (equal), keep "banana" as current
         *
         * current = "banana";
         *
         * // Compare with "date"
         * result = lengthComparator.compare(current, "date");
         * // Inside compare():
         * //   - Extract "banana".length() → 6
         * //   - Extract "date".length() → 4
         * //   - Compare 6 vs 4 → returns 1
         * // Since 1 (positive), keep "banana" as current
         *
         * current = "banana";
         *
         * // Compare with "elderberry"
         * result = lengthComparator.compare(current, "elderberry");
         * // Inside compare():
         * //   - Extract "banana".length() → 6
         * //   - Extract "elderberry".length() → 10
         * //   - Compare 6 vs 10 → returns -1
         * // Since -1 (negative), "elderberry" becomes the new current
         *
         * // Final result: "elderberry"
         *
         * ==================================================================================
         *
         * LAYER 3: stream.max(Comparator)
         * --------------------------------
         * Source code (simplified):
         *   Optional<T> max(Comparator<? super T> comparator) {
         *       return reduce((a, b) -> comparator.compare(a, b) >= 0 ? a : b);
         *   }
         *
         * KEY INSIGHT: max() is implemented using reduce() internally!
         * It repeatedly calls comparator.compare(a, b) with different pairs.
         *
         * ==================================================================================
         *
         * LAYER 4: Step-by-Step Execution Summary
         * ----------------------------------------
         * Given: ["apple", "banana", "cherry", "date", "elderberry"]
         *
         * Step | Current Max   | Next Element    | compare() Call        | Result | Winner
         * -----|---------------|-----------------|----------------------|--------|---------------
         *  1   | "apple" (5)   | "banana" (6)    | compare(5,6) = -1    | 5 < 6  | "banana"
         *  2   | "banana" (6)  | "cherry" (6)    | compare(6,6) = 0     | 6 == 6 | "banana"
         *  3   | "banana" (6)  | "date" (4)      | compare(6,4) = +1    | 6 > 4  | "banana"
         *  4   | "banana" (6)  | "elderberry"(10)| compare(6,10) = -1   | 6 < 10 | "elderberry"
         *
         * Final Result: Optional.of("elderberry")
         *
         * ==================================================================================
         *
         * WHY comparingInt() is EFFICIENT
         * -------------------------------
         * ❌ Less Efficient: Comparator.comparing(String::length)
         *    - Uses Integer objects (boxing/unboxing overhead)
         *
         * ✅ More Efficient: Comparator.comparingInt(String::length)
         *    - Uses primitive int (no boxing overhead)
         *
         * ==================================================================================
         *
         * HOW Integer.compare() WORKS
         * ---------------------------
         *   public static int compare(int x, int y) {
         *       return (x < y) ? -1 : ((x == y) ? 0 : 1);
         *   }
         *
         * Examples:
         *   - Integer.compare(5, 10) → -1 (5 is less than 10)
         *   - Integer.compare(6, 6)  → 0  (equal)
         *   - Integer.compare(10, 6) → 1  (10 is greater than 6)
         *
         * ==================================================================================
         *
         * KEY TAKEAWAYS
         * -------------
         * 1. comparingInt() is a FACTORY that creates a Comparator (not doing the comparison itself)
         * 2. The created Comparator's compare() method takes 2 objects and extracts values from BOTH
         * 3. String::length is applied individually to each string during each comparison
         * 4. max() internally uses reduce() and calls compare() multiple times
         * 5. Time complexity: O(n) - single pass through stream, no sorting
         * 6. Space complexity: O(1) - just tracking current max
         *
         * Why recommended: Clean, readable, efficient, and shows clear intent!
         * ==================================================================================
         */
        System.out.println("\n=== APPROACH 2: Using max() with Comparator (RECOMMENDED) ===");
        String longest2 = strings.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("Longest (max): " + longest2);

        System.out.println("\n=== APPROACH 3: Using sorted() and findFirst() ===");
        // Less efficient O(n log n) - sorts entire list just to get one element
        String longest3 = strings.stream()
                .sorted((a, b) -> b.length() - a.length())  // Sort descending by length
                .findFirst()
                .orElse(null);
        System.out.println("Longest (sorted): " + longest3);

        System.out.println("\n=== APPROACH 4: Using reduce() with Identity ===");
        // No Optional handling needed but identity value "" affects logic
        String longest4 = strings.stream()
                .reduce("", (a, b) -> a.length() >= b.length() ? a : b);
        System.out.println("Longest (reduce with identity): " + longest4);

        System.out.println("\n=== APPROACH 5: Using collect() with maxBy() ===");
        // Works efficiently with parallel streams
        String longest5 = strings.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(String::length)))
                .orElse(null);
        System.out.println("Longest (collect): " + longest5);

        System.out.println("\n=== BONUS: Other reduce() Examples ===");

        // Sum of all lengths
        int totalLength = strings.stream()
                .map(String::length)
                .reduce(0, (a, b) -> a + b);  // Or use .reduce(0, Integer::sum)
        System.out.println("Total length of all strings: " + totalLength);

        // Concatenate all strings
        String concatenated = strings.stream()
                .reduce("", (a, b) -> a + b);
        System.out.println("Concatenated: " + concatenated);

        // Product of all lengths
        int product = strings.stream()
                .map(String::length)
                .reduce(1, (a, b) -> a * b);
        System.out.println("Product of lengths: " + product);
    }
}