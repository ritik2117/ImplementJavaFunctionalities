package multiThreading;

public class UsingAnonymousClass {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread is running in anonymous class");
            }
        });
        t1.start();
    }
}
