package org.example;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        int number = 2000;
        // ForkJoin
        ForkJoinPool pool = new ForkJoinPool();
        long startFjp = System.nanoTime();
        pool.invoke(new FactorialTask(1, number));
        long endFjp = System.nanoTime();
        System.out.println("ForkJoin time: " + (endFjp - startFjp) / 1_000_000 + " ms");

        //Sequential
        SequentialFactorial sequentialFactorial = new SequentialFactorial();
        long startSeq = System.nanoTime();
        sequentialFactorial.factorial(number);
        long endSeq = System.nanoTime();
        System.out.println("Sequential time: " + (endSeq - startSeq) / 1_000_000 + " ms");

    }
}
