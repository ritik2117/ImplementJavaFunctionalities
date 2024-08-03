package multiThreading;

/**
 * Runnable:
 * Suitable for tasks that do not return a result and do not throw checked exceptions.
 * It is typically used for tasks that perform some action without needing to provide feedback.
 */
public class NumberPrinterRunnable implements Runnable {

    private final int number;

    public NumberPrinterRunnable(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println("Runnable Task " + number + " is running on Thread " + Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
