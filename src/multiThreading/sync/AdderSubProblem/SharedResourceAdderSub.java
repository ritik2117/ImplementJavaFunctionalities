package multiThreading.sync.AdderSubProblem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResourceAdderSub {
    private int value = 0;
    private final Lock lock = new ReentrantLock();

    public void add(int amount) {
        lock.lock();
        try {
            value += amount;
            System.out.println("Added " + amount + " to value. New value: " + value);
        } finally {
            lock.unlock();
        }
    }

    public void subtract(int amount) {
        lock.lock();
        try {
            value -= amount;
            System.out.println("Subtracted " + amount + " from value. New value: " + value);
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        return value;
    }
}
