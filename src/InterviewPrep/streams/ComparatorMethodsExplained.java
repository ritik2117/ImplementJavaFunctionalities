package InterviewPrep.streams;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ==================================================================================
 * COMPREHENSIVE GUIDE: All Comparator Factory Methods and Use Cases
 * ==================================================================================
 *
 * WHY DIFFERENT COMPARATOR METHODS?
 * ----------------------------------
 * Different data types need different comparison strategies:
 * 1. Primitive types (int, long, double) - Use specialized methods for efficiency
 * 2. Objects (String, Date, etc.) - Use generic comparing() method
 * 3. Complex scenarios - Chain comparators, handle nulls, custom logic
 *
 * COMPARATOR FACTORY METHODS:
 * ---------------------------
 * 1. comparingInt(T -> int)     - For int values (no boxing overhead)
 * 2. comparingLong(T -> long)   - For long values (timestamps, file sizes)
 * 3. comparingDouble(T -> double) - For double values (prices, ratings)
 * 4. comparing(T -> U)          - For any Comparable objects (String, Date, etc.)
 * 5. Custom Comparator          - For complex business logic
 *
 * ADVANCED FEATURES:
 * ------------------
 * - thenComparing()    - Multiple field sorting
 * - reversed()         - Reverse order
 * - nullsFirst()       - Handle nulls at start
 * - nullsLast()        - Handle nulls at end
 * - naturalOrder()     - Use object's natural ordering
 * - reverseOrder()     - Reverse of natural ordering
 *
 * ==================================================================================
 */

// Helper Classes for Examples
class Person {
    private String name;
    private Integer age;  // Using Integer to demonstrate null handling
    private String city;

    public Person(String name, Integer age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%s, city='%s'}", name, age, city);
    }
}

class Employee {
    private String name;
    private double salary;
    private LocalDate joinDate;
    private String department;

    public Employee(String name, double salary, LocalDate joinDate, String department) {
        this.name = name;
        this.salary = salary;
        this.joinDate = joinDate;
        this.department = department;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
    public LocalDate getJoinDate() { return joinDate; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', salary=%.2f, joinDate=%s, dept='%s'}",
                name, salary, joinDate, department);
    }
}

class Product {
    private String name;
    private double price;
    private int stockQuantity;
    private double rating;

    public Product(String name, double price, int stockQuantity, double rating) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.rating = rating;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return String.format("Product{name='%s', price=$%.2f, stock=%d, rating=%.1f}",
                name, price, stockQuantity, rating);
    }
}

class Event {
    private String name;
    private LocalDateTime timestamp;
    private long attendees;

    public Event(String name, LocalDateTime timestamp, long attendees) {
        this.name = name;
        this.timestamp = timestamp;
        this.attendees = attendees;
    }

    public String getName() { return name; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public long getAttendees() { return attendees; }

    @Override
    public String toString() {
        return String.format("Event{name='%s', time=%s, attendees=%d}",
                name, timestamp, attendees);
    }
}

public class ComparatorMethodsExplained {
    public static void main(String[] args) {

        // ==================================================================================
        // SCENARIO 1: comparingInt() - For int values (Most Efficient)
        // ==================================================================================
        System.out.println("=== SCENARIO 1: comparingInt() - Integer Values ===");
        /*
         * Use Case: When comparing by int properties (age, length, count, etc.)
         * Advantage: No boxing/unboxing overhead (works with primitive int)
         */
        List<String> words = List.of("apple", "pie", "banana", "kiwi", "strawberry");

        String longest = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("Longest word: " + longest);

        String shortest = words.stream()
                .min(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("Shortest word: " + shortest);

        // ==================================================================================
        // SCENARIO 2: comparingLong() - For long values (Timestamps, File Sizes)
        // ==================================================================================
        System.out.println("\n=== SCENARIO 2: comparingLong() - Long Values ===");
        /*
         * Use Case: When comparing long values (timestamps, file sizes, IDs, etc.)
         * Advantage: Efficient for large numbers, no boxing overhead
         */
        List<Event> events = List.of(
                new Event("Conference", LocalDateTime.of(2024, 3, 15, 10, 0), 5000L),
                new Event("Workshop", LocalDateTime.of(2024, 2, 10, 14, 0), 150L),
                new Event("Meetup", LocalDateTime.of(2024, 4, 20, 18, 0), 800L),
                new Event("Webinar", LocalDateTime.of(2024, 1, 5, 9, 0), 10000L)
        );

        Event mostAttended = events.stream()
                .max(Comparator.comparingLong(Event::getAttendees))
                .orElse(null);
        System.out.println("Most attended event: " + mostAttended);

        Event leastAttended = events.stream()
                .min(Comparator.comparingLong(Event::getAttendees))
                .orElse(null);
        System.out.println("Least attended event: " + leastAttended);

        // ==================================================================================
        // SCENARIO 3: comparingDouble() - For double values (Prices, Ratings)
        // ==================================================================================
        System.out.println("\n=== SCENARIO 3: comparingDouble() - Double Values ===");
        /*
         * Use Case: When comparing double/float values (prices, ratings, percentages)
         * Advantage: Efficient for floating-point comparisons, no boxing overhead
         */
        List<Product> products = List.of(
                new Product("Laptop", 999.99, 50, 4.5),
                new Product("Mouse", 25.50, 200, 4.2),
                new Product("Keyboard", 75.00, 100, 4.8),
                new Product("Monitor", 350.00, 75, 4.6)
        );

        Product mostExpensive = products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
        System.out.println("Most expensive: " + mostExpensive);

        Product highestRated = products.stream()
                .max(Comparator.comparingDouble(Product::getRating))
                .orElse(null);
        System.out.println("Highest rated: " + highestRated);

        Product cheapest = products.stream()
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
        System.out.println("Cheapest: " + cheapest);

        // ==================================================================================
        // SCENARIO 4: comparing() - For Objects (String, Date, Any Comparable)
        // ==================================================================================
        System.out.println("\n=== SCENARIO 4: comparing() - Object Values ===");
        /*
         * Use Case: When comparing objects that implement Comparable (String, Date, etc.)
         * Works with: String, LocalDate, LocalDateTime, Integer, BigDecimal, etc.
         * Note: Has boxing overhead if comparing wrapper types like Integer
         */
        List<Employee> employees = List.of(
                new Employee("Alice", 75000, LocalDate.of(2020, 5, 15), "Engineering"),
                new Employee("Bob", 65000, LocalDate.of(2019, 3, 10), "Marketing"),
                new Employee("Charlie", 85000, LocalDate.of(2021, 8, 20), "Engineering"),
                new Employee("Diana", 70000, LocalDate.of(2018, 1, 5), "HR")
        );

        // Compare by String (alphabetically)
        Employee firstAlphabetically = employees.stream()
                .min(Comparator.comparing(Employee::getName))
                .orElse(null);
        System.out.println("First alphabetically: " + firstAlphabetically);

        // Compare by Date (earliest join date)
        Employee oldestEmployee = employees.stream()
                .min(Comparator.comparing(Employee::getJoinDate))
                .orElse(null);
        System.out.println("Oldest employee: " + oldestEmployee);

        // Compare by Date (latest join date)
        Employee newestEmployee = employees.stream()
                .max(Comparator.comparing(Employee::getJoinDate))
                .orElse(null);
        System.out.println("Newest employee: " + newestEmployee);

        // ==================================================================================
        // SCENARIO 5: Multiple Field Sorting with thenComparing()
        // ==================================================================================
        System.out.println("\n=== SCENARIO 5: Multiple Field Sorting (thenComparing) ===");
        /*
         * Use Case: Sort by multiple criteria (primary, secondary, tertiary, etc.)
         * Example: Sort by department, then by salary DESC, then by name
         */

        System.out.println("\nSort by: Department ASC, then Salary DESC, then Name ASC");
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getDepartment)
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                        .thenComparing(Employee::getName))
                .collect(Collectors.toList());
        sortedEmployees.forEach(System.out::println);

        System.out.println("\nSort Products by: Rating DESC, then Price ASC");
        List<Product> sortedProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getRating).reversed()
                        .thenComparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        sortedProducts.forEach(System.out::println);

        // ==================================================================================
        // SCENARIO 6: Reversed Order
        // ==================================================================================
        System.out.println("\n=== SCENARIO 6: Reversed Order ===");
        /*
         * Use Case: Get opposite order (descending instead of ascending)
         * Two ways: 1) Use reversed() 2) Use min() instead of max()
         */

        // Method 1: Using reversed()
        String shortestWord1 = words.stream()
                .max(Comparator.comparingInt(String::length).reversed())
                .orElse(null);
        System.out.println("Shortest word (using reversed): " + shortestWord1);

        // Method 2: Using min() instead of max()
        String shortestWord2 = words.stream()
                .min(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("Shortest word (using min): " + shortestWord2);

        // Descending salary order
        List<Employee> descendingSalary = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());
        System.out.println("\nEmployees by salary (DESC):");
        descendingSalary.forEach(System.out::println);

        // ==================================================================================
        // SCENARIO 7: Null-Safe Comparison
        // ==================================================================================
        System.out.println("\n=== SCENARIO 7: Null-Safe Comparison ===");
        /*
         * Use Case: When data might contain null values
         * Options: nullsFirst() - nulls appear first
         *         nullsLast() - nulls appear last
         */
        List<Person> people = List.of(
                new Person("Alice", 30, "New York"),
                new Person("Bob", null, "London"),  // null age
                new Person("Charlie", 25, "Paris"),
                new Person("Diana", null, "Tokyo"),  // null age
                new Person("Eve", 35, "Berlin")
        );

        System.out.println("\nSort by age (nulls last):");
        List<Person> sortedNullsLast = people.stream()
                .sorted(Comparator.comparing(Person::getAge,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        sortedNullsLast.forEach(System.out::println);

        System.out.println("\nSort by age (nulls first):");
        List<Person> sortedNullsFirst = people.stream()
                .sorted(Comparator.comparing(Person::getAge,
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        sortedNullsFirst.forEach(System.out::println);

        // ==================================================================================
        // SCENARIO 8: Custom Comparator with Complex Logic
        // ==================================================================================
        System.out.println("\n=== SCENARIO 8: Custom Comparator Logic ===");
        /*
         * Use Case: When you need custom business logic that doesn't fit standard patterns
         * Example: Compare strings by number of vowels
         */

        Comparator<String> vowelCountComparator = (s1, s2) -> {
            int vowels1 = countVowels(s1);
            int vowels2 = countVowels(s2);
            return Integer.compare(vowels1, vowels2);
        };

        String mostVowels = words.stream()
                .max(vowelCountComparator)
                .orElse(null);
        System.out.println("Word with most vowels: " + mostVowels +
                " (" + countVowels(mostVowels) + " vowels)");

        System.out.println("\nAll words sorted by vowel count (DESC):");
        words.stream()
                .sorted(vowelCountComparator.reversed())
                .forEach(word -> System.out.println(word + " - " + countVowels(word) + " vowels"));

        // Custom comparator: Compare products by value (rating / price ratio)
        Comparator<Product> valueComparator = (p1, p2) -> {
            double value1 = p1.getRating() / p1.getPrice();
            double value2 = p2.getRating() / p2.getPrice();
            return Double.compare(value1, value2);
        };

        Product bestValue = products.stream()
                .max(valueComparator)
                .orElse(null);
        System.out.println("\nBest value product: " + bestValue);

        // ==================================================================================
        // SCENARIO 9: Natural Order and Reverse Order
        // ==================================================================================
        System.out.println("\n=== SCENARIO 9: Natural Order & Reverse Order ===");
        /*
         * Use Case: Sort elements using their natural ordering (compareTo method)
         * Works with: String, Integer, Double, Date, etc. (anything that implements Comparable)
         */

        List<String> names = List.of("Zara", "Alice", "Mike", "Bob", "Charlie");

        // Natural order (A-Z)
        List<String> naturalOrder = names.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        System.out.println("Natural order (A-Z): " + naturalOrder);

        // Reverse natural order (Z-A)
        List<String> reverseOrder = names.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println("Reverse order (Z-A): " + reverseOrder);

        // ==================================================================================
        // SCENARIO 10: Comparing with Method Reference vs Lambda
        // ==================================================================================
        System.out.println("\n=== SCENARIO 10: Method Reference vs Lambda ===");
        /*
         * Both approaches work identically, choose based on readability
         */

        // Method Reference (Clean and concise)
        Employee highestPaid1 = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
        System.out.println("Highest paid (method ref): " + highestPaid1);

        // Lambda (More explicit)
        Employee highestPaid2 = employees.stream()
                .max(Comparator.comparingDouble(emp -> emp.getSalary()))
                .orElse(null);
        System.out.println("Highest paid (lambda): " + highestPaid2);

        // ==================================================================================
        // SUMMARY: When to Use Which Comparator
        // ==================================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY: Choosing the Right Comparator");
        System.out.println("=".repeat(80));
        System.out.println("1. comparingInt()    → Use for: int, length, count, age");
        System.out.println("2. comparingLong()   → Use for: long, timestamps, file sizes, large IDs");
        System.out.println("3. comparingDouble() → Use for: double, prices, ratings, percentages");
        System.out.println("4. comparing()       → Use for: String, Date, any Comparable object");
        System.out.println("5. Custom Comparator → Use for: Complex business logic");
        System.out.println("\nAdvanced:");
        System.out.println("- thenComparing()    → Multiple field sorting");
        System.out.println("- reversed()         → Reverse order");
        System.out.println("- nullsFirst/Last()  → Handle null values");
        System.out.println("- naturalOrder()     → Use object's natural ordering");
        System.out.println("=".repeat(80));
    }

    // Helper method to count vowels
    private static int countVowels(String str) {
        if (str == null) return 0;
        return (int) str.toLowerCase().chars()
                .filter(c -> "aeiou".indexOf(c) != -1)
                .count();
    }
}
