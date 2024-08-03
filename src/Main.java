import multiThreading.NumberPrinter;
import overrideMethods.Person;
import streams.StreamsFun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
//        System.out.println(integerList.indexOf(4));

        Iterator<Integer> iterator = integerList.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

        List<Integer> nums = List.of(4, 6, 8);
//        System.out.println(nums);
        StreamsFun streamsFun = new StreamsFun();
//        streamsFun.useConsumerInterface();
        /*streamsFun.useStreamsInterface();
        Person person1 = new Person(1, "Ritik", 27);
        Person person2 = new Person(2, "Naman", 30);
        Person person3 = new Person(1, "Ritik", 27);

        System.out.println(person1.toString());
        System.out.println(person2.toString());
        System.out.println(person3.toString());

        System.out.println(person1.equals(person2));
        System.out.println(person1.equals(person3));

        int person1HashCode = person1.hashCode();
        int person2HashCode = person2.hashCode();
        int person3HashCode = person3.hashCode();

        System.out.println(person1HashCode == person2HashCode);
        System.out.println(person1HashCode == person3HashCode);*/

        /**
         * Implementing multi-threading
         */
        NumberPrinter numberPrinter = new NumberPrinter(5);
        Thread t1 = new Thread(numberPrinter);
        t1.start();
        Thread t2 = new Thread(numberPrinter);
        t2.start();
    }
}