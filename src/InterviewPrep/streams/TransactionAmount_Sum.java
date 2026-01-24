package InterviewPrep.streams;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionAmount_Sum {

    private static class Transaction {
        private final String date;
        private final int amount;

        private Transaction(String date, int amount) {
            this.date = date;
            this.amount = amount;
        }

        private String getDate() {
            return date;
        }

        private int getAmount() {
            return amount;
        }
    }

    public static void main(String[] args) {
//        Given a list of transactions, find the sum of transaction amounts for each day using Java streams
        List<Transaction> transactions = Arrays.asList(
                new Transaction("2022-01-01", 100),
                new Transaction("2022-01-01", 200),
                new Transaction("2022-01-02", 300),
                new Transaction("2022-01-02", 400),
                new Transaction("2022-01-03", 500)
        );

        // SOLUTION 1: groupingBy + summingInt (Most Common & Recommended)
        // Intuition: Group transactions by date into buckets, then sum the amounts in each bucket.
        // This is the most idiomatic and readable approach for "sum by group" problems.
        // Time: O(n), Space: O(unique_dates)
        Map<String, Integer> sumByDate1 = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.summingInt(Transaction::getAmount)
                ));
        System.out.println("Solution 1 (groupingBy + summingInt): " + sumByDate1);

        // SOLUTION 2: groupingBy + reducing
        // Intuition: Similar to Solution 1, but uses reduce instead of summingInt.
        // Reduce combines elements by repeatedly applying a binary operation (addition here).
        // Less concise than summingInt, but shows how reduce works under the hood.
        Map<String, Integer> sumByDate2 = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.reducing(0, Transaction::getAmount, Integer::sum)
                ));
        System.out.println("Solution 2 (groupingBy + reducing): " + sumByDate2);

        // SOLUTION 3: toMap with merge function
        // Intuition: Build a map directly where each date maps to its sum.
        // When a duplicate key (date) is found, the merge function adds the amounts.
        // This is more explicit about the map-building process than groupingBy.
        // Time: O(n), Space: O(unique_dates)
        Map<String, Integer> sumByDate3 = transactions.stream()
                .collect(Collectors.toMap(
                        Transaction::getDate,
                        Transaction::getAmount,
                        Integer::sum  // merge function: when key exists, sum old + new
                ));
        System.out.println("Solution 3 (toMap with merge): " + sumByDate3);

        // SOLUTION 4: Manual accumulation with forEach
        // Intuition: Iterate through transactions and manually update a map.
        // Use merge() which puts the value if absent, or applies the function if present.
        // More imperative style, but very clear about what's happening step-by-step.
        // Time: O(n), Space: O(unique_dates)
        Map<String, Integer> sumByDate4 = new HashMap<>();
        transactions.stream().forEach(t ->
                sumByDate4.merge(t.getDate(), t.getAmount(), Integer::sum)
        );
        System.out.println("Solution 4 (forEach + merge): " + sumByDate4);

        // SOLUTION 5: groupingBy then mapping values
        // Intuition: First group transactions by date (creates Map<String, List<Transaction>>),
        // then transform each List<Transaction> to its sum.
        // Two-step thinking: "group, then transform each group."
        // Time: O(n), Space: O(n) initially, then O(unique_dates)
        Map<String, Integer> sumByDate5 = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getDate))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(Transaction::getAmount).sum()
                ));
        System.out.println("Solution 5 (groupingBy then map values): " + sumByDate5);

        // SOLUTION 6: TreeMap for sorted dates
        // Intuition: Same as Solution 1, but results are automatically sorted by date.
        // TreeMap maintains keys in natural order (alphabetical for strings).
        // Useful when you need the output sorted by date.
        // Time: O(n log n) due to TreeMap, Space: O(unique_dates)
        Map<String, Integer> sumByDate6 = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        TreeMap::new,  // supplier for sorted map
                        Collectors.summingInt(Transaction::getAmount)
                ));
        System.out.println("Solution 6 (TreeMap for sorted): " + sumByDate6);

        // SOLUTION 7: LinkedHashMap to preserve encounter order
        // Intuition: Same as Solution 1, but preserves the order in which dates first appear.
        // If you process 2022-01-02 before 2022-01-01, the result reflects that order.
        // Useful when insertion order matters for output/display.
        // Time: O(n), Space: O(unique_dates)
        Map<String, Integer> sumByDate7 = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        LinkedHashMap::new,  // supplier for ordered map
                        Collectors.summingInt(Transaction::getAmount)
                ));
        System.out.println("Solution 7 (LinkedHashMap for insertion order): " + sumByDate7);

        // SOLUTION 8: Parallel stream for large datasets
        // Intuition: Same logic as Solution 1, but uses parallelStream for concurrent processing.
        // Multiple threads process different chunks of transactions simultaneously.
        // Only beneficial for large datasets (thousands+ transactions).
        // Time: O(n/p) where p = processors, Space: O(unique_dates)
        Map<String, Integer> sumByDate8 = transactions.parallelStream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.summingInt(Transaction::getAmount)
                ));
        System.out.println("Solution 8 (parallel stream): " + sumByDate8);

        // SOLUTION 9: Custom collector (Advanced)
        // Intuition: Build a custom collector that directly accumulates into a map.
        // Shows the low-level mechanics of how collectors work.
        // Educational but overkill for this problem; use built-in collectors instead.
        // Time: O(n), Space: O(unique_dates)
        Map<String, Integer> sumByDate9 = transactions.stream()
                .collect(HashMap::new,
                        (map, transaction) -> map.merge(transaction.getDate(), transaction.getAmount(), Integer::sum),
                        (map1, map2) -> map2.forEach((key, value) -> map1.merge(key, value, Integer::sum))
                );
        System.out.println("Solution 9 (custom collector): " + sumByDate9);

        // SOLUTION 10: Using compute for accumulation
        // Intuition: Stream through transactions, using compute() to update map entries.
        // compute() takes a key and a function that receives the old value (or null) and returns new value.
        // Similar to Solution 4, but using compute() instead of merge().
        // Time: O(n), Space: O(unique_dates)
        Map<String, Integer> sumByDate10 = new HashMap<>();
        transactions.stream().forEach(t ->
                sumByDate10.compute(t.getDate(),
                        (key, oldValue) -> oldValue == null ? t.getAmount() : oldValue + t.getAmount())
        );
        System.out.println("Solution 10 (compute): " + sumByDate10);

        System.out.println("\n--- RECOMMENDATIONS ---");
        System.out.println("Best for readability: Solution 1 (groupingBy + summingInt)");
        System.out.println("Best for performance: Solution 1 or 3 (similar performance)");
        System.out.println("When you need sorted output: Solution 6 (TreeMap)");
        System.out.println("When you need insertion order: Solution 7 (LinkedHashMap)");
        System.out.println("For very large datasets: Solution 8 (parallel stream)");

        System.out.println("\n\n========== DEEP DIVE: groupingBy + summingInt ==========");
        demonstrateGroupingByVariations();
    }

    private static void demonstrateGroupingByVariations() {
        /*
         * DEEP EXPLANATION OF: groupingBy + summingInt
         *
         * Structure: stream.collect(Collectors.groupingBy(classifier, downstream))
         *
         * How it works:
         * 1. classifier function: extracts the grouping key (date in our case)
         * 2. downstream collector: processes elements within each group (summingInt in our case)
         *
         * Internal process:
         * - Creates a Map<K, D> where K = key type, D = downstream result type
         * - For each element:
         *   a) Apply classifier to get the key
         *   b) Get or create the downstream accumulator for that key
         *   c) Feed the element to that downstream accumulator
         * - Finally, finish all downstream collectors and return the map
         *
         * Think of it as: "Put elements into buckets (groupingBy), then process each bucket (downstream)"
         */

        System.out.println("\n=== VARIATION 1: Different numeric types ===");

        // For LONG values - use summingLong
        List<TransactionLong> longTransactions = Arrays.asList(
                new TransactionLong("2022-01-01", 1000000000L),
                new TransactionLong("2022-01-01", 2000000000L),
                new TransactionLong("2022-01-02", 3000000000L)
        );
        Map<String, Long> sumByDateLong = longTransactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionLong::getDate,
                        Collectors.summingLong(TransactionLong::getAmount)  // summingLong for long
                ));
        System.out.println("Sum with Long: " + sumByDateLong);

        // For DOUBLE values - use summingDouble
        List<TransactionDouble> doubleTransactions = Arrays.asList(
                new TransactionDouble("2022-01-01", 100.50),
                new TransactionDouble("2022-01-01", 200.75),
                new TransactionDouble("2022-01-02", 300.25)
        );
        Map<String, Double> sumByDateDouble = doubleTransactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionDouble::getDate,
                        Collectors.summingDouble(TransactionDouble::getAmount)  // summingDouble for double
                ));
        System.out.println("Sum with Double: " + sumByDateDouble);

        // For BIGDECIMAL (financial precision) - use reducing or mapping
        List<TransactionBigDecimal> bdTransactions = Arrays.asList(
                new TransactionBigDecimal("2022-01-01", new java.math.BigDecimal("100.50")),
                new TransactionBigDecimal("2022-01-01", new java.math.BigDecimal("200.75")),
                new TransactionBigDecimal("2022-01-02", new java.math.BigDecimal("300.25"))
        );
        Map<String, java.math.BigDecimal> sumByDateBD = bdTransactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionBigDecimal::getDate,
                        Collectors.reducing(
                                java.math.BigDecimal.ZERO,
                                TransactionBigDecimal::getAmount,
                                java.math.BigDecimal::add  // BigDecimal::add for precise addition
                        )
                ));
        System.out.println("Sum with BigDecimal: " + sumByDateBD);

        System.out.println("\n=== VARIATION 2: Different aggregation operations ===");

        List<Transaction> txns = Arrays.asList(
                new Transaction("2022-01-01", 100),
                new Transaction("2022-01-01", 200),
                new Transaction("2022-01-02", 300),
                new Transaction("2022-01-02", 400),
                new Transaction("2022-01-03", 500)
        );

        // COUNTING: Count transactions per date
        Map<String, Long> countByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.counting()  // downstream: count elements
                ));
        System.out.println("Count per date: " + countByDate);

        // AVERAGING: Average transaction amount per date
        Map<String, Double> avgByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.averagingInt(Transaction::getAmount)  // downstream: average
                ));
        System.out.println("Average per date: " + avgByDate);

        // MAX: Maximum transaction amount per date
        Map<String, Optional<Integer>> maxByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.maxBy(Integer::compareTo)  // downstream: max
                        )
                ));
        System.out.println("Max per date: " + maxByDate);

        // MIN: Minimum transaction amount per date
        Map<String, Optional<Integer>> minByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.minBy(Integer::compareTo)  // downstream: min
                        )
                ));
        System.out.println("Min per date: " + minByDate);

        // COLLECT TO LIST: Collect all amounts per date into a list
        Map<String, List<Integer>> amountListByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.toList()  // downstream: collect to list
                        )
                ));
        System.out.println("Amount lists per date: " + amountListByDate);

        // COLLECT TO SET: Unique amounts per date
        Map<String, Set<Integer>> amountSetByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.toSet()  // downstream: collect to set
                        )
                ));
        System.out.println("Unique amounts per date: " + amountSetByDate);

        // JOINING: Concatenate amounts as string per date
        Map<String, String> joinedByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.mapping(
                                t -> String.valueOf(t.getAmount()),
                                Collectors.joining(", ", "[", "]")  // downstream: join strings
                        )
                ));
        System.out.println("Joined amounts per date: " + joinedByDate);

        System.out.println("\n=== VARIATION 3: Multi-level grouping ===");

        List<TransactionWithType> typedTxns = Arrays.asList(
                new TransactionWithType("2022-01-01", 100, "CREDIT"),
                new TransactionWithType("2022-01-01", 200, "DEBIT"),
                new TransactionWithType("2022-01-01", 150, "CREDIT"),
                new TransactionWithType("2022-01-02", 300, "DEBIT"),
                new TransactionWithType("2022-01-02", 400, "CREDIT")
        );

        // Group by date, then by type, then sum
        Map<String, Map<String, Integer>> sumByDateAndType = typedTxns.stream()
                .collect(Collectors.groupingBy(
                        TransactionWithType::getDate,  // first level: by date
                        Collectors.groupingBy(
                                TransactionWithType::getType,  // second level: by type
                                Collectors.summingInt(TransactionWithType::getAmount)  // then sum
                        )
                ));
        System.out.println("Sum by date and type: " + sumByDateAndType);

        System.out.println("\n=== VARIATION 4: Statistics summary ===");

        // Get all statistics at once (count, sum, min, max, average)
        Map<String, IntSummaryStatistics> statsByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.summarizingInt(Transaction::getAmount)  // downstream: full stats
                ));
        System.out.println("Statistics per date:");
        statsByDate.forEach((date, stats) ->
                System.out.println("  " + date + " -> count=" + stats.getCount() +
                        ", sum=" + stats.getSum() +
                        ", min=" + stats.getMin() +
                        ", max=" + stats.getMax() +
                        ", avg=" + stats.getAverage())
        );

        System.out.println("\n=== VARIATION 5: Partitioning (binary grouping) ===");

        // Split into two groups based on a predicate
        Map<Boolean, List<Transaction>> partitionedByAmount = txns.stream()
                .collect(Collectors.partitioningBy(
                        t -> t.getAmount() >= 300  // true if >= 300, false otherwise
                ));
        System.out.println("High amounts (>=300): " + partitionedByAmount.get(true));
        System.out.println("Low amounts (<300): " + partitionedByAmount.get(false));

        // Partition and sum within each partition
        Map<Boolean, Integer> sumByAmountRange = txns.stream()
                .collect(Collectors.partitioningBy(
                        t -> t.getAmount() >= 300,
                        Collectors.summingInt(Transaction::getAmount)  // downstream: sum
                ));
        System.out.println("Sum of high amounts: " + sumByAmountRange.get(true));
        System.out.println("Sum of low amounts: " + sumByAmountRange.get(false));

        System.out.println("\n=== VARIATION 6: Custom downstream collector ===");

        // Collect full transaction objects per date (not just amounts)
        Map<String, List<Transaction>> txnsByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate  // only classifier, uses toList() by default
                ));
        System.out.println("Transactions by date: " + txnsByDate);

        // Filter within groups: only transactions > 200
        Map<String, List<Transaction>> filteredTxnsByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.filtering(
                                t -> t.getAmount() > 200,  // filter within each group
                                Collectors.toList()
                        )
                ));
        System.out.println("Filtered (>200) transactions by date: " + filteredTxnsByDate);

        // FlatMapping: extract and flatten nested collections
        Map<String, List<String>> tagsByDate = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.flatMapping(
                                t -> Arrays.asList("tag1", "tag2").stream(),  // simulate tags per transaction
                                Collectors.toList()
                        )
                ));
        System.out.println("Flattened tags by date: " + tagsByDate);

        System.out.println("\n=== KEY TAKEAWAYS ===");
        System.out.println("1. groupingBy structure: Collectors.groupingBy(classifier, downstream)");
        System.out.println("2. Classifier: extracts the grouping key (date, category, etc.)");
        System.out.println("3. Downstream: processes elements within each group");
        System.out.println("4. Common downstreams:");
        System.out.println("   - summingInt/Long/Double: sum numeric values");
        System.out.println("   - counting(): count elements");
        System.out.println("   - averagingInt/Long/Double: calculate average");
        System.out.println("   - maxBy/minBy: find max/min");
        System.out.println("   - mapping: transform then collect");
        System.out.println("   - toList/toSet: collect to collection");
        System.out.println("   - joining: concatenate strings");
        System.out.println("   - summarizingInt: get all stats at once");
        System.out.println("   - reducing: custom reduction");
        System.out.println("   - filtering: filter within groups");
        System.out.println("5. Can nest groupingBy for multi-level grouping");
        System.out.println("6. Use partitioningBy for binary grouping (true/false)");
    }

    // Helper classes for variations
    private static class TransactionLong {
        private final String date;
        private final long amount;

        private TransactionLong(String date, long amount) {
            this.date = date;
            this.amount = amount;
        }

        private String getDate() { return date; }
        private long getAmount() { return amount; }
    }

    private static class TransactionDouble {
        private final String date;
        private final double amount;

        private TransactionDouble(String date, double amount) {
            this.date = date;
            this.amount = amount;
        }

        private String getDate() { return date; }
        private double getAmount() { return amount; }
    }

    private static class TransactionBigDecimal {
        private final String date;
        private final java.math.BigDecimal amount;

        private TransactionBigDecimal(String date, java.math.BigDecimal amount) {
            this.date = date;
            this.amount = amount;
        }

        private String getDate() { return date; }
        private java.math.BigDecimal getAmount() { return amount; }
    }

    private static class TransactionWithType {
        private final String date;
        private final int amount;
        private final String type;

        private TransactionWithType(String date, int amount, String type) {
            this.date = date;
            this.amount = amount;
            this.type = type;
        }

        private String getDate() { return date; }
        private int getAmount() { return amount; }
        private String getType() { return type; }
    }
}
