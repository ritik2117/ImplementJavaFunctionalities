import multiThreading.NumberPrinterCallable;
import multiThreading.NumberPrinterRunnable;
import streams.StreamsFun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

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
        /*NumberPrinter numberPrinter = new NumberPrinter(5);
        Thread t1 = new Thread(numberPrinter);
        t1.start();
        Thread t2 = new Thread(numberPrinter);
        t2.start();*/
//        Create a thread pool with a fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        Submit Runnable tasks
        for (int i=1; i<=10; i++) {
            Future<Integer> future = (Future<Integer>) executorService.submit(new NumberPrinterRunnable(i));
            /**
             * No need of the below code and also of the getting the result in future as
             *  The run method does not return any result and cannot throw a checked exception.
             */
            /*try {
                Integer result = future.get();
                System.out.println("Result of Runnable task: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }*/
        }
//        Shut down the executor service after all tasks are submitted
//        executorService.shutdown();

//        Submit Callable tasks
        /*Callable<Integer> callable = (Integer num) -> {
            System.out.println("Callable Task " + num + " is running on Thread " + Thread.currentThread().getId());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num*2;
        };*/
        for (int i=1; i<=10; i++) {
            Future<Integer> future = executorService.submit(new NumberPrinterCallable(i));
            try {
                Integer result = future.get();
                System.out.println("Result of Callable task: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        try {
//            Wait for the previously submitted tasks to complete
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
//            Force shutdown if interrupted during wait
            executorService.shutdownNow();
        }

        System.out.println("Executor service has been shut down.");
    }
}