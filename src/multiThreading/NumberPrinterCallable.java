package multiThreading;

import java.util.concurrent.Callable;

/**
 * Callable:
 * Suitable for tasks that need to return a result or throw a checked exception.
 * It is used when you need the task to provide a result or when the task might throw an exception that needs to be handled.
 */
public class NumberPrinterCallable implements Callable {

    private int number;

    public NumberPrinterCallable(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Callable Task " + number + " is running on Thread " + Thread.currentThread().getId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number*2;
    }
}
