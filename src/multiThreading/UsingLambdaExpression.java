package multiThreading;

public class UsingLambdaExpression {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> System.out.println("Thread is running using lambda expression"));
        t1.start();
    }
}
