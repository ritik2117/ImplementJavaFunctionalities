package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Student {
    private int id;
    private String name;
    private int age;
    private int score;

    public Student(int id, String name, int age, int score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getScore() {
        return score;
    }

    public static void main(String[] args) {
        /**
         * We have a class Student with fileds id, name, age, score.
         * Find list of students with 2nd highest score using stream api
         */
        List<Student> students = Arrays.asList(
                new Student(1, "Alice", 20, 85),
                new Student(2, "Bob", 22, 92),
                new Student(3, "Charlie", 21, 85),
                new Student(4, "David", 23, 95),
                new Student(5, "Eve", 20, 90)
        );

//        Optional<Integer> secondHighestScore = students.stream()
//                .map(Student::getScore)// Extract scores
//                .distinct() // Ensure distinct scores
//                .sorted((a,b) -> b - a) // Sort in descending order
//                .skip(1) // Skip the highest score
//                .findFirst(); // Get the second highest score
//        secondHighestScore.isPresent(score -> System.out.println("The 2nd highest score is: " + score));

        Consumer<Integer> integerConsumer = (n) -> System.out.println(n);

        Function<Student, Integer> integerFunction = new Function<Student, Integer>() {
            @Override
            public Integer apply(Student student) {
                return student.getScore();
            }
        };

        Comparator<Integer> integerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer n1, Integer n2) {
                return n2-n1;
            }
        };

        Stream<Student> studentStreams = students.stream();
        Stream<Integer> score = studentStreams.map(student -> student.getScore()).distinct();
        Stream<Integer> sortedScores = score.sorted((n1, n2) -> n2 - n1);
        Stream<Integer> sortedScoresSkippedTop = sortedScores.skip(1);
        Optional<Integer> maxScore = sortedScoresSkippedTop.findFirst();
        maxScore.ifPresent((n) -> System.out.println(n));

        /**
         * Additional Questions:
         * Get the first Character of all the Names of the student and
         * return the result in form of a stream.
         *
         * Achieve this by using the map function in the stream
         * to transform each student's name into its first character
         * and return the result as a stream.
         */

        Stream<Character> studentNameFirstChar = students.stream().map((Student student) -> student.getName().charAt(0));
        studentNameFirstChar.forEach(System.out::println);
    }
}
