package org.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {
    @Test
    void testFibonacci45() {
        long result = new ForkJoinPool().invoke(new Fibonacci(45));
        assertEquals(1134903170L, result);
    }
}