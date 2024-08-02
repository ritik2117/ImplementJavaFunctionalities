package streams;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class StreamsFun {
    private List<Integer> nums = List.of(1,2,3,4,5,6);
    public void useConsumerInterface() {
//        Using forEach
//        nums.forEach(System.out::println);
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer n) {
                System.out.println(n);
            }
        };
//        nums.forEach(n -> System.out.println(n));
    }

    public void useStreamsInterface() {
        Stream<Integer> stream1 = nums.stream();
        Stream<Integer> stream2 = stream1.filter(n -> n % 2 == 0);
        Stream<Integer> stream3 = stream2.map(n -> n * 2);
        Integer result = stream3.reduce(1, (a,b) -> a + b);
//        stream3.forEach(n -> System.out.println(n));
        System.out.println(result);
    }
}
