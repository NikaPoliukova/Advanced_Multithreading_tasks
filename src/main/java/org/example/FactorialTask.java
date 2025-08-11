package org.example;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

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
            BigInteger result = BigInteger.ONE;
            for (int i = start; i <= end; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
            
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

