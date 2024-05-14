package com.example.multit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;

@Service
public class CountingServiceImpl implements CountingService {
    public static volatile int counter = 0;

    private static int threadsNumber;

    @Value("${spring.application.threadsNumber}")
    private void setThreadsNumber(int value) {
        threadsNumber = value;
    }

    private static int limit;

    @Value("${spring.application.limit}")
    private void setLimit(int value) {
        limit = value;
    }

    static class Worker implements Runnable {
        private final CountDownLatch latch;
        private final String name;
        private final int limit;


        public Worker(CountDownLatch latch, String name, int limit) {
            this.latch = latch;
            this.name = name;
            this.limit = limit;
        }
        @Override
        public void run() {
            while (counter <= limit - threadsNumber) {
                doWork();
                incrementCounter();
            }
            latch.countDown();
            System.out.println(name + " finished");
        }
        public void doWork() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " doing work");
        }
    }
    public void threadExecutorWithLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadsNumber);
        long start = Instant.now().toEpochMilli();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new Worker(latch, "Thread-" + i, limit));
            long startOneThread = Instant.now().toEpochMilli();
            System.out.println(("Thread-" + i) + " started at " + startOneThread + " ms");
            thread.start();
        }
        latch.await();
        long end = Instant.now().toEpochMilli();
        System.out.println("Thread pool finished at " + end + " ms");
        System.out.println("Total time used: " + (end - start));
        System.out.println("**** Counter: " + counter + " **** ");
    }

    private static synchronized void incrementCounter() {
        counter++;
    }

}
