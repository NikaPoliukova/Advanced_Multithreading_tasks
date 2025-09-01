package org.example;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

import static org.example.Main.multiplyRange;

public class FactorialTask extends RecursiveTask<BigInteger> {
    private final int start;
    private final int end;
    private static final int THRESHOLD = 10;

    public FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected BigInteger compute() {
        if (end - start <= THRESHOLD) {
            return multiplyRange(start, end);
        } else {
            int mid = (start + end) / 2;
            FactorialTask left = new FactorialTask(start, mid);
            FactorialTask right = new FactorialTask(mid + 1, end);
            left.fork();
            BigInteger rightResult = right.compute();
            BigInteger leftResult = left.join();
            return leftResult.multiply(rightResult);
        }
    }
}

