package InterviewPrep.streams;

import java.util.List;
import java.util.function.Predicate;

public class CheckListContaninsPrimeNo {

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        System.out.println("========== Problem: Check if a list of integers contains a prime number using Java streams ==========\n");
        
        // =====================================================================================
        // SOLUTION 1: Using anyMatch() - MOST RECOMMENDED ✅
        // =====================================================================================
        // INTUITION: anyMatch() is specifically designed to check if at least one element
        // in the stream satisfies a condition. It SHORT-CIRCUITS (stops as soon as it finds
        // the first match), making it the most efficient approach.
        //
        // DETAILED EXPLANATION:
        // - anyMatch(Predicate<T>): Returns true if any element matches the predicate
        // - Short-circuiting: Stops immediately when it finds 2 (the first prime),
        //   doesn't check 3, 4, 5, etc.
        // - Time Complexity: O(n) worst case, but typically much faster
        // - When to use: When you just need to know "does at least one exist?"
        // =====================================================================================
        
        System.out.println("SOLUTION 1: Using anyMatch()");
        
        // Method 1a: Using method reference
        boolean hasPrime1a = integers.stream()
                                    .anyMatch(CheckListContaninsPrimeNo::isPrime);
        System.out.println("  Method 1a (Method Reference): " + hasPrime1a);
        
        // Method 1b: Using lambda
        boolean hasPrime1b = integers.stream()
                                    .anyMatch(n -> isPrime(n));
        System.out.println("  Method 1b (Lambda): " + hasPrime1b);
        
        // Method 1c: Using explicit Predicate
        Predicate<Integer> checkPrime = CheckListContaninsPrimeNo::isPrime;
        boolean hasPrime1c = integers.stream()
                                    .anyMatch(checkPrime);
        System.out.println("  Method 1c (Predicate variable): " + hasPrime1c);
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 2: Using filter() + findAny() - Returns Optional
        // =====================================================================================
        // INTUITION: First FILTER only the prime numbers, then check if ANY prime exists.
        // This gives you an Optional that you can use for additional operations.
        //
        // DETAILED EXPLANATION:
        // - filter(Predicate<T>): Creates a new stream with only primes
        // - findAny(): Returns an Optional with any element (short-circuits after finding one)
        // - Advantage: You get the actual prime number, not just true/false
        // - Use case: When you need the prime number itself for further operations
        // =====================================================================================
        
        System.out.println("SOLUTION 2: Using filter() + findAny()");
        
        // Method 2a: Just checking existence
        boolean hasPrime2a = integers.stream()
                                    .filter(CheckListContaninsPrimeNo::isPrime)
                                    .findAny()
                                    .isPresent();
        System.out.println("  Method 2a (isPresent): " + hasPrime2a);
        
        // Method 2b: Getting the actual prime number
        integers.stream()
               .filter(CheckListContaninsPrimeNo::isPrime)
               .findAny()
               .ifPresent(prime -> System.out.println("  Method 2b (ifPresent): Found prime: " + prime));
        
        // Method 2c: Using findFirst() instead of findAny()
        boolean hasPrime2c = integers.stream()
                                    .filter(CheckListContaninsPrimeNo::isPrime)
                                    .findFirst()
                                    .isPresent();
        System.out.println("  Method 2c (findFirst): " + hasPrime2c);
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 3: Using filter() + count() - LESS EFFICIENT ⚠️
        // =====================================================================================
        // INTUITION: Filter all primes and count them. If count > 0, list contains primes.
        //
        // DETAILED EXPLANATION:
        // - Problem: count() is a TERMINAL OPERATION that processes ALL elements
        // - No short-circuiting: Even after finding 2, it still checks 3, 4, 5, 6, 7, 8, 9
        // - Time Complexity: Always O(n)
        // - When NOT to use: When you just need existence check (use anyMatch instead)
        // - When to use: When you actually need the count of primes
        // =====================================================================================
        
        System.out.println("SOLUTION 3: Using filter() + count() [Less Efficient]");
        
        boolean hasPrime3 = integers.stream()
                                   .filter(CheckListContaninsPrimeNo::isPrime)
                                   .count() > 0;
        System.out.println("  Method 3 (count > 0): " + hasPrime3);
        
        // If you actually need the count, this is valid:
        long primeCount = integers.stream()
                                 .filter(CheckListContaninsPrimeNo::isPrime)
                                 .count();
        System.out.println("  Total primes found: " + primeCount);
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 4: Using reduce() - OVERKILL FOR THIS PROBLEM ⚠️
        // =====================================================================================
        // INTUITION: Reduce the stream to a single boolean value by OR-ing all the
        // isPrime checks together.
        //
        // DETAILED EXPLANATION:
        // - map(isPrime): Transforms each number to true/false
        // - reduce(false, (a, b) -> a || b): Combines all booleans with OR operation
        // - Problem: Processes ALL elements even after finding first prime
        // - Why it's overkill: anyMatch does exactly this but with short-circuiting
        // - Use case: Educational purposes, but not recommended for production
        // =====================================================================================
        
        System.out.println("SOLUTION 4: Using reduce() [Overkill]");
        
        boolean hasPrime4 = integers.stream()
                                   .map(CheckListContaninsPrimeNo::isPrime)
                                   .reduce(false, (a, b) -> a || b);
        System.out.println("  Method 4 (reduce with OR): " + hasPrime4);
        
        System.out.println();
        
        // =====================================================================================
        // SOLUTION 5: Using noneMatch() with Negation - CONFUSING ❌
        // =====================================================================================
        // INTUITION: Check if NONE of the numbers are prime, then negate the result.
        //
        // DETAILED EXPLANATION:
        // - noneMatch(): Returns true if NO elements match the predicate
        // - Double negation logic: !(none are prime) = at least one is prime
        // - Confusing: Less readable than anyMatch()
        // - When to use: Never! Use anyMatch() instead
        // =====================================================================================
        
        System.out.println("SOLUTION 5: Using noneMatch() with Negation [Confusing - Not Recommended]");
        
        // Double negation: "NOT (none are prime)" = "at least one is prime"
        boolean hasPrime5 = !integers.stream()
                                    .noneMatch(CheckListContaninsPrimeNo::isPrime);
        System.out.println("  Method 5 (!noneMatch): " + hasPrime5);
        
        System.out.println();
        
        // =====================================================================================
        // COMPARISON SUMMARY
        // =====================================================================================
        System.out.println("========== COMPARISON SUMMARY ==========");
        System.out.println("Method              | Short-circuits? | Returns   | Efficiency | Recommended?");
        System.out.println("-------------------|-----------------|-----------|------------|-------------");
        System.out.println("anyMatch()          | YES ✅          | boolean   | ⭐⭐⭐⭐⭐    | YES ✅");
        System.out.println("filter()+findAny()  | YES ✅          | Optional  | ⭐⭐⭐⭐⭐    | If need value");
        System.out.println("filter()+count()    | NO ❌           | boolean   | ⭐⭐        | Only for count");
        System.out.println("reduce()            | NO ❌           | boolean   | ⭐⭐        | Educational only");
        System.out.println("noneMatch()         | YES ✅          | boolean   | ⭐⭐⭐⭐     | NO ❌ (confusing)");
        System.out.println("\n🎯 RECOMMENDED: Use anyMatch() for this problem!");
    }
}
