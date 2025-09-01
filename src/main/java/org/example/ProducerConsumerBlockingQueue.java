package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerBlockingQueue {
    private final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(5);
    public void produce(int value) throws InterruptedException {
        buffer.put(value);
        System.out.println("Produce: " + value);
    }

    public void consume() throws InterruptedException {
        Integer value = buffer.take();
        System.out.println("Consume: " + value);
    }
}
