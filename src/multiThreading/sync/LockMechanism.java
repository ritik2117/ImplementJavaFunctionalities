package multiThreading.sync;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock allows for explicit locking and unlocking of critical sections in code,
 * providing better control over thread synchronization.
 * Key Features:
 * Reentrancy: A thread can acquire the same lock multiple times without causing a deadlock.
 * Fairness: An optional fairness parameter can be set to ensure that the longest-waiting thread gets the lock first.
 * Lock Interruptibility: Threads can be interrupted while waiting for the lock.
 * Condition Variables: Supports multiple condition variables for more complex thread coordination.
 */
class SharedResource {
    private final Lock lock = new ReentrantLock();

    public void criticalSection(int n, AtomicInteger count) {
        lock.lock();
        try {
            if (n % 2 == 0) {
                count.incrementAndGet();
                Thread.sleep(1000);
            } else {
                count.decrementAndGet();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class LockMechanism {
    public static void main(String[] args) {
        /**
         * AtomicInteger is a class in the java.util.concurrent.atomic package that provides an integer value which can be updated atomically.
         * This means that all operations on the AtomicInteger are thread-safe and are performed without the need for explicit synchronization.
         * Key Features:
         * Atomic Operations: Provides atomic methods for incrementing, decrementing, adding, and setting the integer value.
         * Thread Safety: Ensures that multiple threads can safely update the integer value without causing data races.
         * Non-blocking: Uses low-level atomic machine instructions to perform operations, which can be more efficient than using locks.
         */
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 1; i++) {
            int n = i;
            new Thread(() -> new SharedResource().criticalSection(n, count))
                    .start();
        }
        System.out.println("Count: " + count);
    }
}
