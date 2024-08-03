package multiThreading;

public class NumberPrinter implements Runnable {

    private final int number;

    public NumberPrinter(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i=0; i<number; i++) {
            System.out.println("Thread " + Thread.currentThread().getId()+ " is printing number: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
