package multiThreading;

class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("Thread is running...");
    }
}
public class UsingThreadClass {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        /**
         * t1.start(): starts the thread t1 by calling its start() method.
         * The start() method internally calls the run() method defined in MyThread,
         * but on a new call stack. This starts a new thread of execution.
         * When start() is called,
         * the JVM creates a new thread, and the run() method is executed in this new thread.
         *
         * start() vs. run():
         * Calling start() is crucial for multithreading because it initiates a new thread.
         * Directly calling run() would execute the method on the main thread, not on a separate thread.
         */
        t1.start();
    }
}
