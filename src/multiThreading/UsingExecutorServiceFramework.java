package multiThreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsingExecutorServiceFramework {
    public static void main(String[] args) {
        /**
         * ExecutorService:
         * This is an interface that provides methods to manage termination
         * and track progress of asynchronous tasks (runnables).
         *
         * newFixedThreadPool(10):
         * Creates a thread pool with a fixed number (n) of threads.
         * The ExecutorService will use up to n threads to execute submitted tasks.
         *
         * submit(): This method is used to submit tasks to the ExecutorService.
         *
         * The ExecutorService assigns these tasks to threads from the thread pool.
         * Since the pool has 10 threads, both tasks will likely run concurrently on separate threads.
         */
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable runnable1 = () -> System.out.println("Thread 1 is running");
        Runnable runnable2 = () -> System.out.println("Thread 2 is running");
        executorService.submit(runnable1);
        executorService.submit(runnable2);

        /**
         * shutdown(): This method initiates an orderly shutdown of the ExecutorService.
         * No new tasks will be accepted after this call.
         * The previously submitted tasks will continue to execute until completion.
         * It is important to call shutdown() to free up resources
         * when the ExecutorService is no longer needed.
         * Not doing so can result in the program hanging due to active threads.
         */
        executorService.shutdown();
    }
}
