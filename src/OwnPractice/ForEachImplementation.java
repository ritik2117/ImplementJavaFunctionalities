package OwnPractice;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ForEachImplementation {
    /**
     * Print 10 random numbers using forEach
     */
    public static void main(String[] args) {
//        Using traditional method
        Random random = new Random();
        /*for (int i=0; i<10; i++) {
            System.out.println(random.nextInt());
        }*/

//        Generate a stream of 10 random numbers and print them using forEach
        /**
         * Stream<Integer>: Less memory-efficient due to the creation of Integer objects
         * for each int value.
         */
        System.out.println("Using Stream.generate: ");
        Stream.generate(random::nextInt) // Instance Method Reference of a Particular Object (Bound Method Reference):
                .limit(10) // Limit the stream to 10 numbers
                .forEach(System.out::println);

//        Another method using stream
        /**
         * IntStream: More memory-efficient
         * because it deals with raw int values without creating extra Integer objects.
         */
        System.out.println("Using IntStream: ");
        IntStream intStream = random.ints().limit(10);
//        intStream.forEach(System.out::println);

        /**
         * If you want to limit the random numbers to a specific upper bound
         * (e.g., 0 to 99), you can specify the bound directly:
         */
        System.out.println("Printing random numbers between 0 to 20");
        Stream<Integer> stream = Stream.generate(() -> random.nextInt(20))
                                                            .limit(10);
        random.ints(10, 0, 20)
                .limit(10)
                .forEach(System.out::println);
//        stream.forEach(System.out::println);
        /**
         * In parallel streams, if the order of processing is important,
         * forEachOrdered ensures that the results are consistent with the order of the original stream.
         */
        System.out.println("Using forEach");
        IntStream.range(1, 10).parallel().forEach(System.out::println);
        System.out.println("Using forEachOrdered");
        IntStream.range(1, 10).parallel().forEachOrdered(System.out::println);
    }
}
