package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumerSemaphore {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int CAPACITY = 5;

    private final Semaphore empty = new Semaphore(CAPACITY);
    private final Semaphore full = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    public void produce(int value) throws InterruptedException {
        empty.acquire();
        mutex.acquire();

        buffer.offer(value);
        System.out.println("Produce: " + value);

        mutex.release();
        full.release();
    }

    public void consume() throws InterruptedException {
        full.acquire();
        mutex.acquire();
        Integer value = buffer.poll();
        System.out.println("Consume: " + value);

        mutex.release();
        empty.release();
    }
}
