package org.example;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) throws IOException {

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

//Task 2
        int[] array = {5, 2, 9, 1, 5, 6, 10, 3, 33, 78, 4, 55, 89, 559, 4848, 22, 47, 13};

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MultithreadingSorting task = new MultithreadingSorting(array, 0, array.length - 1);

        forkJoinPool.invoke(task);
        for (int num : array) {
            System.out.print(num + " ");
        }


//Task 3
        String folderPath;
        if (args.length >= 1) {
            folderPath = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter path to folder: ");
            folderPath = scanner.nextLine();
        }

        Path rootPath = Paths.get(folderPath);

        ForkJoinPool pool3 = new ForkJoinPool();
        var result = pool3.invoke(new FolderScanner(rootPath));

        System.out.println(" Results:");
        System.out.println("Failes: " + result.fileCount);
        System.out.println("Folders: " + result.folderCount);
        System.out.println("Size: " + result.totalSize + " byte");


//Task 4
        CompletableFutureService service = new CompletableFutureService();

        try {
            service.hiredEmployees()
                    .thenCompose(employees ->
                            CompletableFuture.allOf(
                                    employees.stream()
                                            .map(emp -> service.getSalary(emp.id)
                                                    .thenAccept(emp::setSalary)
                                                    .toCompletableFuture())
                                            .toArray(CompletableFuture[]::new)
                            ).thenApply(v -> employees)
                    )
                    .thenAccept(list -> {
                        list.forEach(System.out::println);
                    })
                    .toCompletableFuture()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        //Task 5
//Semaphore
        ProducerConsumerSemaphore pc = new ProducerConsumerSemaphore();

        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.produce(i);
                    Thread.sleep(1000); // Имитация задержки для удобства наблюдения
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.consume();
                    Thread.sleep(1500); // Имитация задержки для удобства наблюдения
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producerThread.start();
        consumerThread.start();


        //BlockingQueue
        ProducerConsumerBlockingQueue pc1 = new ProducerConsumerBlockingQueue();
        Thread producerThread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc1.produce(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerThread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc1.consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread1.start();
        consumerThread1.start();
    }
}


