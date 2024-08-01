package streams;

import java.util.List;
import java.util.function.Consumer;

public class StreamsFun {
    public void useConsumerInterface() {
        List<Integer> nums = List.of(1,2,3,4,5,6);
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
}
