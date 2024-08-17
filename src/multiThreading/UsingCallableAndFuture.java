package multiThreading;

import java.util.concurrent.*;

/**
 * Unlike `Runnable`, the `Callable` interface allows tasks to return a result and throw checked exceptions.
 */
public class UsingCallableAndFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Callable<Integer> callable = () -> 5*10;
        Future<Integer> future = executorService.submit(callable);
        try {
            Integer result = future.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Code is executed!");
        }
    }
}
