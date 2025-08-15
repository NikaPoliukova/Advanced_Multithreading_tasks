package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutureService {

    public CompletionStage<List<Employee>> hiredEmployees() {
        return CompletableFuture.supplyAsync(() -> {
            return Arrays.asList(
                    new Employee(1, "Alice"),
                    new Employee(2, "Bob"),
                    new Employee(3, "Charlie"),
                    new Employee(4, "Diana")
            );
        });
    }

    public CompletionStage<Double> getSalary(int hiredEmployeeId) {
        return CompletableFuture.supplyAsync(() -> {
            return 2000 + hiredEmployeeId * 500.0;
        });
    }
}
