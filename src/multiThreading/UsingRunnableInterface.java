package multiThreading;

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread is running through runnable interface.");
    }
}

public class UsingRunnableInterface {
    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable);
        t1.start();
    }
}
