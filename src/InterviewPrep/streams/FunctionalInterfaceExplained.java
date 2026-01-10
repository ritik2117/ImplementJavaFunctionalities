package InterviewPrep.streams;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ==================================================================================
 * QUESTION 1: Comparator is a functional interface, so how does it have so many methods?
 * ==================================================================================
 *
 * ANSWER: A functional interface can have:
 * -------------------------------
 * 1. EXACTLY ONE abstract method (SAM - Single Abstract Method)
 * 2. MULTIPLE default methods (with implementation)
 * 3. MULTIPLE static methods (with implementation)
 * 4. Methods inherited from Object class (equals, hashCode, toString)
 *
 * COMPARATOR INTERFACE BREAKDOWN:
 * -------------------------------
 * @FunctionalInterface
 * public interface Comparator<T> {
 *     // 1. ONE ABSTRACT METHOD (makes it functional)
 *     int compare(T o1, T o2);  ← This is the ONLY abstract method
 *
 *     // 2. DEFAULT METHODS (instance methods with implementation)
 *     default Comparator<T> reversed() { ... }
 *     default Comparator<T> thenComparing(Comparator<? super T> other) { ... }
 *     default Comparator<T> thenComparingInt(ToIntFunction<? super T> keyExtractor) { ... }
 *     // ... many more default methods
 *
 *     // 3. STATIC METHODS (factory methods)
 *     static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor) { ... }
 *     static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) { ... }
 *     static <T> Comparator<T> comparingLong(ToLongFunction<? super T> keyExtractor) { ... }
 *     static <T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> keyExtractor) { ... }
 *     static <T extends Comparable<? super T>> Comparator<T> naturalOrder() { ... }
 *     static <T extends Comparable<? super T>> Comparator<T> reverseOrder() { ... }
 *     static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) { ... }
 *     static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) { ... }
 *     // ... more static methods
 *
 *     // 4. METHODS FROM OBJECT CLASS (don't count towards abstract method count)
 *     boolean equals(Object obj);
 * }
 *
 * KEY INSIGHT:
 * -----------
 * - Only the compare() method needs to be implemented
 * - All other methods (default and static) already have implementations
 * - When you use a lambda like (a, b) -> a.compareTo(b), you're only implementing compare()
 * - The other methods (reversed, thenComparing, etc.) are available because they're already implemented
 *
 * ==================================================================================
 * QUESTION 2: Interface cannot be instantiated, so how are we creating objects?
 * ==================================================================================
 *
 * ANSWER: We're NOT directly instantiating the interface!
 * -------------------------------------------------------
 * We're creating IMPLEMENTATIONS of the interface using:
 * 1. Anonymous Class - Explicit implementation
 * 2. Lambda Expression - Syntactic sugar for anonymous class
 * 3. Method Reference - Syntactic sugar for lambda
 * 4. Concrete Class - Named implementation
 *
 * WHAT REALLY HAPPENS:
 * -------------------
 * When you write a lambda or method reference, the Java compiler:
 * 1. Creates a concrete class behind the scenes
 * 2. Implements the abstract method
 * 3. Creates an instance of that class
 * 4. Returns that instance
 *
 * You're getting an OBJECT that IMPLEMENTS the interface, not the interface itself!
 *
 * ==================================================================================
 */

public class FunctionalInterfaceExplained {

    public static void main(String[] args) {
        List<String> strings = List.of("apple", "banana", "cherry", "date");

        System.out.println("=".repeat(80));
        System.out.println("QUESTION 1: How does Comparator have so many methods?");
        System.out.println("=".repeat(80));

        // Let's see what methods are available on a Comparator instance
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);

        System.out.println("\nComparator has ONE abstract method:");
        System.out.println("  - compare(T o1, T o2)  ← Must be implemented\n");

        System.out.println("But also has many DEFAULT methods (already implemented):");
        System.out.println("  - reversed()");
        System.out.println("  - thenComparing()");
        System.out.println("  - thenComparingInt()");
        System.out.println("  - thenComparingLong()");
        System.out.println("  - thenComparingDouble()");
        System.out.println("  ... and more!\n");

        System.out.println("And many STATIC factory methods:");
        System.out.println("  - comparing()");
        System.out.println("  - comparingInt()");
        System.out.println("  - comparingLong()");
        System.out.println("  - comparingDouble()");
        System.out.println("  - naturalOrder()");
        System.out.println("  - reverseOrder()");
        System.out.println("  - nullsFirst()");
        System.out.println("  - nullsLast()");
        System.out.println("  ... and more!\n");

        // Demonstrating that default methods work
        System.out.println("Using default methods:");
        Comparator<String> reversedComparator = lengthComparator.reversed();
        System.out.println("  Original comparator: " + strings.stream().max(lengthComparator).orElse(null));
        System.out.println("  Reversed comparator: " + strings.stream().max(reversedComparator).orElse(null));

        Comparator<String> chainedComparator = lengthComparator.thenComparing(Comparator.naturalOrder());
        System.out.println("  Chained comparator (length then alphabetical): Works!");

        System.out.println("\n" + "=".repeat(80));
        System.out.println("QUESTION 2: How are we creating interface objects?");
        System.out.println("=".repeat(80));

        System.out.println("\nWe're NOT creating interface objects directly!");
        System.out.println("We're creating IMPLEMENTATIONS of the interface.\n");

        // METHOD 1: Anonymous Class (Most Explicit)
        System.out.println("METHOD 1: Anonymous Class");
        System.out.println("-".repeat(40));
        Comparator<String> comp1 = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        };
        System.out.println("Created: " + comp1.getClass().getName());
        System.out.println("Is it an interface? No, it's a concrete class!");
        System.out.println("Does it implement Comparator? " + (comp1 instanceof Comparator));
        System.out.println("Result: " + strings.stream().max(comp1).orElse(null));

        // METHOD 2: Lambda Expression (Syntactic Sugar)
        System.out.println("\nMETHOD 2: Lambda Expression");
        System.out.println("-".repeat(40));
        Comparator<String> comp2 = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        System.out.println("Created: " + comp2.getClass().getName());
        System.out.println("Is it an interface? No, it's a concrete class!");
        System.out.println("Does it implement Comparator? " + (comp2 instanceof Comparator));
        System.out.println("Lambda is just shorthand for anonymous class!");
        System.out.println("Result: " + strings.stream().max(comp2).orElse(null));

        // METHOD 3: Method Reference (Even More Syntactic Sugar)
        System.out.println("\nMETHOD 3: Method Reference");
        System.out.println("-".repeat(40));
        Comparator<String> comp3 = Comparator.comparingInt(String::length);
        System.out.println("Created: " + comp3.getClass().getName());
        System.out.println("Is it an interface? No, it's a concrete class!");
        System.out.println("Does it implement Comparator? " + (comp3 instanceof Comparator));
        System.out.println("Method reference is shorthand for lambda!");
        System.out.println("Result: " + strings.stream().max(comp3).orElse(null));

        // METHOD 4: Named Class (Traditional Way)
        System.out.println("\nMETHOD 4: Named Concrete Class");
        System.out.println("-".repeat(40));
        Comparator<String> comp4 = new LengthComparator();
        System.out.println("Created: " + comp4.getClass().getName());
        System.out.println("Is it an interface? No, it's a concrete class!");
        System.out.println("Does it implement Comparator? " + (comp4 instanceof Comparator));
        System.out.println("Result: " + strings.stream().max(comp4).orElse(null));

        System.out.println("\n" + "=".repeat(80));
        System.out.println("WHAT'S REALLY HAPPENING UNDER THE HOOD");
        System.out.println("=".repeat(80));

        System.out.println("\nWhen you write:");
        System.out.println("  Comparator<String> comp = (s1, s2) -> s1.length() - s2.length();");
        System.out.println("\nThe compiler generates something like:");
        System.out.println("  class Lambda$1 implements Comparator<String> {");
        System.out.println("      @Override");
        System.out.println("      public int compare(String s1, String s2) {");
        System.out.println("          return s1.length() - s2.length();");
        System.out.println("      }");
        System.out.println("  }");
        System.out.println("  Comparator<String> comp = new Lambda$1();");
        System.out.println("\nSo you're getting a CONCRETE OBJECT, not an interface!");

        System.out.println("\n" + "=".repeat(80));
        System.out.println("OTHER FUNCTIONAL INTERFACE EXAMPLES");
        System.out.println("=".repeat(80));

        // Predicate functional interface
        System.out.println("\nPredicate<T> - Has 1 abstract method: test(T t)");
        Predicate<String> startsWithA = s -> s.startsWith("a");
        System.out.println("  Abstract method: test()");
        System.out.println("  Default methods: and(), or(), negate()");
        System.out.println("  Static methods: isEqual(), not()");
        System.out.println("  Testing 'apple': " + startsWithA.test("apple"));
        System.out.println("  Using default method negate(): " + startsWithA.negate().test("apple"));

        // Function functional interface
        System.out.println("\nFunction<T, R> - Has 1 abstract method: apply(T t)");
        Function<String, Integer> stringLength = String::length;
        System.out.println("  Abstract method: apply()");
        System.out.println("  Default methods: andThen(), compose()");
        System.out.println("  Static methods: identity()");
        System.out.println("  Applying to 'banana': " + stringLength.apply("banana"));
        System.out.println("  Using default method andThen(): " + 
                stringLength.andThen(len -> len * 2).apply("banana"));

        System.out.println("\n" + "=".repeat(80));
        System.out.println("KEY TAKEAWAYS");
        System.out.println("=".repeat(80));
        System.out.println("\n1. Functional Interface Definition:");
        System.out.println("   - MUST have exactly ONE abstract method");
        System.out.println("   - CAN have multiple default methods (with implementation)");
        System.out.println("   - CAN have multiple static methods (with implementation)");
        System.out.println("   - Marked with @FunctionalInterface annotation (optional but recommended)");

        System.out.println("\n2. Comparator Breakdown:");
        System.out.println("   - Abstract: compare(T o1, T o2)  ← YOU implement this");
        System.out.println("   - Default: reversed(), thenComparing(), etc.  ← Already implemented");
        System.out.println("   - Static: comparingInt(), comparing(), etc.  ← Already implemented");

        System.out.println("\n3. Creating Interface Objects:");
        System.out.println("   - You CANNOT instantiate an interface directly");
        System.out.println("   - You CAN create objects that IMPLEMENT the interface");
        System.out.println("   - Lambda/Method Reference = Shorthand for implementation");
        System.out.println("   - Compiler creates concrete class behind the scenes");

        System.out.println("\n4. Why This Matters:");
        System.out.println("   - Lambdas are just syntactic sugar for anonymous classes");
        System.out.println("   - You're always getting a concrete object, never just an interface");
        System.out.println("   - Default methods allow interfaces to evolve without breaking existing code");
        System.out.println("   - Static methods provide utility functions related to the interface");

        System.out.println("\n" + "=".repeat(80));
    }

    // Example of a named class implementing Comparator
    static class LengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return Integer.compare(s1.length(), s2.length());
        }
    }
}

/*
 * ==================================================================================
 * ADDITIONAL EXAMPLES: Custom Functional Interface
 * ==================================================================================
 */

// Example 1: Simple functional interface with only abstract method
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);  // Abstract method - MUST be implemented
}

// Example 2: Functional interface with default and static methods
@FunctionalInterface
interface AdvancedCalculator {
    // Abstract method - MUST be implemented
    int calculate(int a, int b);

    // Default method - Already implemented
    default int calculateAndDouble(int a, int b) {
        return calculate(a, b) * 2;
    }

    // Another default method
    default int calculateAndSquare(int a, int b) {
        int result = calculate(a, b);
        return result * result;
    }

    // Static method - Already implemented
    static int add(int a, int b) {
        return a + b;
    }

    // Another static method
    static int multiply(int a, int b) {
        return a * b;
    }
}

// Example usage class
class FunctionalInterfaceDemo {
    public static void demoCalculator() {
        // Using simple Calculator
        Calculator addition = (a, b) -> a + b;  // Implementing the abstract method
        System.out.println("5 + 3 = " + addition.calculate(5, 3));

        // Using AdvancedCalculator
        AdvancedCalculator calc = (a, b) -> a * b;  // Implementing the abstract method
        System.out.println("4 * 5 = " + calc.calculate(4, 5));
        System.out.println("4 * 5 doubled = " + calc.calculateAndDouble(4, 5));  // Using default method
        System.out.println("4 * 5 squared = " + calc.calculateAndSquare(4, 5));  // Using default method

        // Using static methods
        System.out.println("Static add: " + AdvancedCalculator.add(10, 20));
        System.out.println("Static multiply: " + AdvancedCalculator.multiply(10, 20));
    }
}
